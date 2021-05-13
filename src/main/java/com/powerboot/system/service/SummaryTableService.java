package com.powerboot.system.service;

import com.powerboot.system.dao.SummaryTableDao;
import com.powerboot.system.domain.DataBossVo;
import com.powerboot.system.domain.DataSaleVo;
import com.powerboot.system.domain.OrderDO;
import com.powerboot.system.domain.PayDO;
import com.powerboot.system.dto.UserDTO;
import com.powerboot.system.vo.DataCountVO;
import com.powerboot.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SummaryTableService {
    @Autowired
    SummaryTableDao summaryTableDao;
    @Autowired
    PayService payService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    OrderService orderService;

    public int updateById(DataBossVo dataBossVo) {
        return summaryTableDao.updateById(dataBossVo);
    }

    public DataBossVo getById(Integer id) {
        return summaryTableDao.getById(id);
    }

    public List<DataBossVo> list(Map<String, Object> params) {
        return summaryTableDao.list(params);
    }

    public List<DataBossVo> listAndLimit(Integer limit) {
        return summaryTableDao.listAndLimit(limit);
    }

    public int listSonCount(DataCountVO param) {
        return summaryTableDao.listSonCount(param);
    }

    public List<DataSaleVo> listSon(DataCountVO param) {
        List<DataSaleVo> result = summaryTableDao.listSon(param);
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        }
        List<Long> saleIds = result.stream().map(DataSaleVo::getSaleId).collect(Collectors.toList());
        List<UserDTO> saleUserDOList = appUserService.getByIdList(saleIds);

        List<Long> idList = result.stream().map(DataSaleVo::getId).collect(Collectors.toList());
        List<PayDO> payDOList = payService.getByUserIdList(idList);
        List<UserDTO> byParentId = appUserService.getByParentIdList(idList);
        List<OrderDO> orderDOList = orderService.listByData(idList);
        result.forEach(o -> {
            Long userId = o.getId();
            BigDecimal rechargeAmount = payDOList.stream().filter(i -> (userId.equals(i.getUserId()) && i.getStatus().equals(2) && i.getType().equals(1))).map(PayDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal withdrawalAmount = payDOList.stream().filter(i -> (userId.equals(i.getUserId()) && i.getStatus().equals(2) && i.getType().equals(99))).map(PayDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            Integer validCount = byParentId.stream().filter(j -> (userId.equals(j.getParentId()) && j.getFirstRecharge().equals(1))).collect(Collectors.toList()).size();
            Integer totalCount = byParentId.stream().filter(j -> userId.equals(j.getParentId())).collect(Collectors.toList()).size();
            Integer clickCount = orderDOList.stream().filter(i -> (userId.equals(i.getUserId()))).collect(Collectors.toList()).size();
            BigDecimal clickCommission = orderDOList.stream().filter(i -> (userId.equals(i.getUserId()))).map(OrderDO::getProductCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
            o.setRechargeAmount(rechargeAmount);
            o.setWithdrawalAmount(withdrawalAmount);
            o.setVaildCount(validCount);
            o.setTotalCount(totalCount);
            o.setClickCount(clickCount);
            o.setClickCommission(clickCommission);
            o.setSaleNo(saleUserDOList.stream().filter(i -> o.getSaleId().equals(i.getId())).findFirst().orElse(new UserDTO()).getMobile());
            o.setRegDate(DateUtils.formatDateYMDHMS(o.getCreateTime()));
        });
        return result;
    }

    public List<DataSaleVo> listSonDetail(Long parentId) {
        List<DataSaleVo> result = summaryTableDao.listSonDetail(parentId);
        if (CollectionUtils.isEmpty(result)) {
            return new ArrayList<>();
        }
        List<Long> idList = result.stream().map(DataSaleVo::getId).collect(Collectors.toList());
        List<PayDO> payDOList = payService.getByUserIdList(idList);
        List<UserDTO> byParentId = appUserService.getByParentIdList(idList);
        List<OrderDO> orderDOList = orderService.listByData(idList);

        result.forEach(o -> {
            Long userId = o.getId();
            BigDecimal rechargeAmount = payDOList.stream().filter(i -> (userId.equals(i.getUserId()) && i.getStatus().equals(2) && i.getType().equals(1))).map(PayDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal withdrawalAmount = payDOList.stream().filter(i -> (userId.equals(i.getUserId()) && i.getStatus().equals(2) && i.getType().equals(99))).map(PayDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            Integer validCount = byParentId.stream().filter(j -> (userId.equals(j.getParentId()) && j.getFirstRecharge().equals(1))).collect(Collectors.toList()).size();
            Integer totalCount = byParentId.stream().filter(j -> userId.equals(j.getParentId())).collect(Collectors.toList()).size();
            Integer clickCount = orderDOList.stream().filter(i -> (userId.equals(i.getUserId()))).collect(Collectors.toList()).size();
            BigDecimal clickCommission = orderDOList.stream().filter(i -> (userId.equals(i.getUserId()))).map(OrderDO::getProductCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
            o.setRechargeAmount(rechargeAmount);
            o.setWithdrawalAmount(withdrawalAmount);
            o.setVaildCount(validCount);
            o.setTotalCount(totalCount);
            o.setClickCount(clickCount);
            o.setClickCommission(clickCommission);
        });
        return result;
    }
}
