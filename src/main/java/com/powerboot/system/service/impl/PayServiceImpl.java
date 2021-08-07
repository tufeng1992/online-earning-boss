package com.powerboot.system.service.impl;

import com.google.common.collect.Maps;
import com.powerboot.client.AppClient;
import com.powerboot.client.ApplyRequest;
import com.powerboot.client.BaseResponse;
import com.powerboot.common.utils.Query;
import com.powerboot.system.consts.*;
import com.powerboot.system.dao.PayDao;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.BalanceDO;
import com.powerboot.system.domain.PayDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.response.PayResp;
import com.powerboot.system.response.RechangeResponse;
import com.powerboot.system.response.WithdrawAuditResp;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.BalanceService;
import com.powerboot.system.service.PayService;
import com.powerboot.system.vo.PayVO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.powerboot.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayServiceImpl implements PayService {

    private static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
    @Autowired
    private PayDao payDao;
    @Autowired
    private AppClient appClient;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private AppUserService appUserService;

    @Override
    public PayDO get(Long id) {
        return payDao.get(id);
    }

    @Override
    public List<PayDO> list(Map<String, Object> map) {
        return payDao.list(map);
    }


    /**
     * 查询用户提现总金额
     * @param userId
     * @param o
     * @return
     */
    @Override
    public BigDecimal selectUserWithdrawalTotalAmont(Long userId, PayDO o) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("offset", 0);
        params.put("limit", 9999999);
        params.put("type", 99);
        params.put("status", 2);
        params.put("applyStatus", 2);
        params.put("userId", userId);
        Query query = new Query(params);
        List<PayDO> withdrawalTotalList = list(query);
        BigDecimal total = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(withdrawalTotalList)) {
            return total;
        }
        for (PayDO payDO : withdrawalTotalList) {
            if (null != o && o.getId().equals(payDO.getId())) {
                continue;
            }
            total = total.add(payDO.getAmount());
        }
        return total;
    }

    @Override
    public BigDecimal selectUserRechargeTotal(Long userId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("offset", 0);
        params.put("limit", 9999999);
        params.put("type", 1);
        params.put("status", 2);
        params.put("applyStatus", 2);
        params.put("userId", userId);
        Query query = new Query(params);
        List<PayDO> rechargeTotalList = list(query);
        BigDecimal total = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(rechargeTotalList)) {
            return total;
        }
        for (PayDO payDO : rechargeTotalList) {
            total = total.add(payDO.getAmount());
        }
        return total;
    }

    @Override
    public int count(Map<String, Object> map) {
        return payDao.count(map);
    }

    @Override
    public int save(PayDO pay) {
        return payDao.save(pay);
    }

    @Override
    public int update(PayDO pay) {
        return payDao.update(pay);
    }

    @Override
    public int updateSaleIdByUserId(Long saleId, List<Long> userIdList) {
        return payDao.updateSaleIdByUserId(saleId, userIdList);
    }

    @Override
    public int remove(Long id) {
        return payDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return payDao.batchRemove(ids);
    }

    @Override
    public PayResp getCountByTypeStatusAndDate(List<Integer> typeList, Integer status, List<Long> saleIdList) {
        PayResp resp = new PayResp();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);

        PayVO count = payDao.getCountByTypeStatusAndDate(typeList, status, null, null, saleIdList);
        PayVO now = payDao.getCountByTypeStatusAndDate(typeList, status, nowDate, nowDate.plusDays(1), saleIdList);
        PayVO yesterday = payDao.getCountByTypeStatusAndDate(typeList, status, yesterdayDate, nowDate, saleIdList);

        resp.setCount(count.getCount());
        resp.setAmount(count.getAmount() == null ? BigDecimal.ZERO : count.getAmount());
        resp.setLocalCount(now.getCount());
        resp.setLocalAmount(now.getAmount() == null ? BigDecimal.ZERO : now.getAmount());
        resp.setYesterdayCount(yesterday.getCount());
        resp.setYesterdayAmount(yesterday.getAmount() == null ? BigDecimal.ZERO : yesterday.getAmount());
        return resp;
    }

    @Override
    public WithdrawAuditResp getCountByTypeStatusAndAudit(List<Integer> typeList, List<Long> saleIdList) {
        WithdrawAuditResp resp = new WithdrawAuditResp();
        LocalDate nowDate = LocalDate.now();
        PayVO nowPass = payDao.getCountByTypeStatusAndAudit(typeList, null, nowDate, nowDate.plusDays(1), saleIdList, Collections.singletonList(2));
        resp.setPassCount(nowPass.getCount());
        resp.setPassAmount(nowPass.getAmount() == null ? BigDecimal.ZERO : nowPass.getAmount());
        PayVO nowWaitPass = payDao.getCountByTypeStatusAndAudit(typeList, null, nowDate, nowDate.plusDays(1), saleIdList, Collections.singletonList(1));
        resp.setWaitCount(nowWaitPass.getCount());
        resp.setWaitAmount(nowWaitPass.getAmount() == null ? BigDecimal.ZERO : nowWaitPass.getAmount());
        return resp;
    }

    @Override
    public List<PayDO> getByUserIdList(List<Long> idList) {
        return payDao.getByUserIdList(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean successPay(String orderNo) {
        PayDO payDO = payDao.getByOrderNo(orderNo);
        if (payDO == null || PayEnum.PAID.getCode().equals(payDO.getStatus())) {
            return false;
        }
        PayDO update = new PayDO();
        update.setId(payDO.getId());
        update.setStatus(PayEnum.PAID.getCode());
        update.setApplyStatus(2);
        int success = update(update);
        if (success <= 0) {
            return false;
        }
        BigDecimal relPayAmount = payDO.getAmount() == null ? new BigDecimal("0.00") : payDO.getAmount();
        if (PayTypeEnum.CHARGE.getCode().equals(payDO.getType())) {
            BalanceDO balanceDO = new BalanceDO();
            balanceDO.setAmount(relPayAmount);
            balanceDO.setType(BalanceTypeEnum.F.getCode());
            balanceDO.setUserId(payDO.getUserId());
            balanceDO.setWithdrawAmount(BigDecimal.ZERO);
            balanceDO.setServiceFee(BigDecimal.ZERO);
            balanceDO.setStatus(StatusTypeEnum.SUCCESS.getCode());
            balanceDO.setCreateTime(new Date());
            balanceDO.setUpdateTime(new Date());
            balanceDO.setOrderNo(payDO.getOrderNo());
            balanceService.addBalanceDetail(balanceDO);
            AppUserDO userDO = appUserService.get(payDO.getUserId());
            if (userDO != null && userDO.getFirstRecharge() == 0 && relPayAmount.compareTo(new BigDecimal("300")) >= 0
                && userDO.getParentId() != null) {
                addParentBalance(1, userDO, new Date(), payDO.getOrderNo());
            }
            appUserService.updateFirstRechargeById(userDO.getId());
        } else if (PayTypeEnum.PAY_VIP2.getCode().equals(payDO.getType())
            || PayTypeEnum.PAY_VIP3.getCode().equals(payDO.getType())
            || PayTypeEnum.PAY_VIP4.getCode().equals(payDO.getType())
            || PayTypeEnum.PAY_VIP5.getCode().equals(payDO.getType())) {
            appUserService.updateUserVIP(payDO.getUserId(), payDO.getType());
        } else if (PayTypeEnum.WITHDRAW.getCode().equals(payDO.getType())){
            balanceService.updateStatusByOrderNo(payDO.getOrderNo(), StatusTypeEnum.SUCCESS.getCode());
            logger.info(
                    "**** 提现成功通知 ****" + "用户id:" + payDO.getUserId() + "订单号:" + payDO.getOrderNo() + ",支付金额: " + payDO.getAmount());
        } else {
            return false;
        }
        return true;
    }

    /**
     * 添加上级返现
     * @param parentLevel
     * @param userDO
     * @param now
     * @param orderNo
     */
    public void addParentBalance(Integer parentLevel, AppUserDO userDO, Date now, String orderNo) {
        logger.info("添加上级返现:parentLevel:{}, parentId:{}, orderNo:{}", parentLevel, userDO.getParentId(), orderNo);
        AppUserDO parentUser = appUserService.get(userDO.getParentId());
        if (null == parentUser || parentLevel == 4) {
            return;
        }
        BalanceDO parent = new BalanceDO();
        //获取首冲返现级别金额
        BigDecimal parentAmount = getParentAmount(parentLevel);
        parent.setAmount(parentAmount);
        parent.setType(BalanceTypeEnum.C.getCode());
        parent.setUserId(parentUser.getId());
        parent.setWithdrawAmount(BigDecimal.ZERO);
        parent.setServiceFee(BigDecimal.ZERO);
        parent.setStatus(StatusTypeEnum.SUCCESS.getCode());
        parent.setCreateTime(now);
        parent.setUpdateTime(now);
        parent.setOrderNo(orderNo);
        balanceService.addBalanceDetail(parent);
        parentLevel++;
        addParentBalance(parentLevel, parentUser, now, orderNo);
    }

    /**
     * 获取首冲返现级别金额
     * @param parentLevel
     * @return
     */
    private BigDecimal getParentAmount(Integer parentLevel) {
        BigDecimal parentAmount = null;
        if (1 == parentLevel) {
            parentAmount = new BigDecimal(RedisUtils.getString(DictConsts.FIRST_RECHARGE_PARENT_AMOUNT));
        } else if (2 == parentLevel) {
            parentAmount = new BigDecimal(RedisUtils.getString(DictConsts.FIRST_RECHARGE_PARENT_AMOUNT_LEVEL2));
        } else if (3 == parentLevel) {
            parentAmount = new BigDecimal(RedisUtils.getString(DictConsts.FIRST_RECHARGE_PARENT_AMOUNT_LEVEL3));
        }
        return parentAmount;
    }

    @Override
    public boolean refreshPay(String orderNo) {
        BaseResponse<com.powerboot.client.PayDO> response = appClient.getPay(orderNo);
        if (response == null || response.isFail()) {
            return false;
        }
        return true;
    }

    @Override
    public RechangeResponse getRechangeResponse(Integer userCount, List<Long> saleIdList) {
        RechangeResponse rechangeResponse = new RechangeResponse();
        Map<String, Object> params = Maps.newHashMap();
        params.put("saleIdList", saleIdList);
        Integer rechangCount = payDao.getRechangeCount(params);
        Integer againRechangeCount = payDao.getAgainRechangeCount(params);
        rechangeResponse.setRechangeCount(rechangCount.toString());
        if (userCount != 0) {
            rechangeResponse.setRechangeRate(BigDecimal.valueOf(rechangCount).multiply(new BigDecimal("100")).
                divide(BigDecimal.valueOf(userCount), 2, BigDecimal.ROUND_DOWN).toString());
        } else {
            rechangeResponse.setRechangeRate("0.00");
        }
        rechangeResponse.setAgainRechangeCount(againRechangeCount.toString());
        if (rechangCount != 0) {
            rechangeResponse
                .setAgainRechangeRate(BigDecimal.valueOf(againRechangeCount).multiply(new BigDecimal("100")).
                    divide(BigDecimal.valueOf(rechangCount), 2, BigDecimal.ROUND_DOWN).toString());
        } else {
            rechangeResponse.setAgainRechangeRate("0.00");
        }
        return rechangeResponse;
    }

    @Override
    public boolean apply(ApplyRequest applyRequest, Boolean apply) {
        BaseResponse response;
        if (apply) {
            response = appClient.applyPass(applyRequest);
        } else {
            response = appClient.applyReject(applyRequest);
        }
        if (response == null || response.isFail()) {
            return false;
        }
        return true;
    }


    public PayDO getNewOrderByUserId(Long userId) {
        return payDao.getNewOrderByUserId(userId);
    }
}
