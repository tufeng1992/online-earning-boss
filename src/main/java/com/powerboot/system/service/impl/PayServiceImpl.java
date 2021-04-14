package com.powerboot.system.service.impl;

import com.powerboot.client.AppClient;
import com.powerboot.client.ApplyRequest;
import com.powerboot.client.BaseResponse;
import com.powerboot.system.consts.BalanceTypeEnum;
import com.powerboot.system.consts.PayEnum;
import com.powerboot.system.consts.PayTypeEnum;
import com.powerboot.system.consts.StatusTypeEnum;
import com.powerboot.system.dao.PayDao;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.BalanceDO;
import com.powerboot.system.domain.PayDO;
import com.powerboot.system.response.PayResp;
import com.powerboot.system.response.RechangeResponse;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.BalanceService;
import com.powerboot.system.service.PayService;
import com.powerboot.system.vo.PayVO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<PayDO> getByUserIdList(List<Long> idList) {
        return payDao.getByUserIdList(idList);
    }

    @Override
    public Boolean successPay(String orderNo) {
        PayDO payDO = payDao.getByOrderNo(orderNo);
        if (payDO == null || PayTypeEnum.WITHDRAW.getCode().equals(payDO.getType())
            || PayEnum.PAID.getCode().equals(payDO.getStatus())) {
            return false;
        }
        PayDO update = new PayDO();
        update.setId(payDO.getId());
        update.setStatus(PayEnum.PAID.getCode());
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
                BalanceDO parent = new BalanceDO();
                parent.setAmount(new BigDecimal("50"));
                parent.setType(BalanceTypeEnum.C.getCode());
                parent.setUserId(userDO.getParentId());
                parent.setWithdrawAmount(BigDecimal.ZERO);
                parent.setServiceFee(BigDecimal.ZERO);
                parent.setStatus(StatusTypeEnum.SUCCESS.getCode());
                parent.setCreateTime(new Date());
                parent.setUpdateTime(new Date());
                parent.setOrderNo(payDO.getOrderNo());
                balanceService.addBalanceDetail(parent);
            }
            appUserService.updateFirstRechargeById(userDO.getId());
        } else if (PayTypeEnum.PAY_VIP2.getCode().equals(payDO.getType())
            || PayTypeEnum.PAY_VIP3.getCode().equals(payDO.getType())
            || PayTypeEnum.PAY_VIP4.getCode().equals(payDO.getType())
            || PayTypeEnum.PAY_VIP5.getCode().equals(payDO.getType())) {
            appUserService.updateUserVIP(payDO.getUserId(), payDO.getType());
        } else {
            return false;
        }
        return true;
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
    public RechangeResponse getRechangeResponse(Integer userCount) {
        RechangeResponse rechangeResponse = new RechangeResponse();
        Integer rechangCount = payDao.getRechangeCount();
        Integer againRechangeCount = payDao.getAgainRechangeCount();
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
