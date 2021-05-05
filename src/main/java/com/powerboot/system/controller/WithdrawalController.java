package com.powerboot.system.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.powerboot.client.ApplyRequest;
import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.domain.WithdrawalDo;
import com.powerboot.common.utils.DateUtils;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.consts.PayApplyStatusEnum;
import com.powerboot.system.domain.PayDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.dto.UserDTO;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.PayService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/withdrawal")
public class WithdrawalController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WithdrawalController.class);
    @Autowired
    private PayService payService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserService userService;
    @Autowired
    SysUserMappingService sysUserMappingService;

    @GetMapping()
    @RequiresPermissions("system:withdrawal:withdrawalList")
    String withdrawalList() {
        return "system/withdrawal/withdrawalList";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:withdrawal:withdrawalList")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        String saleNo = params.get("saleNo") == null ? null : params.get("saleNo").toString();
        if (!StringUtils.isBlank(saleNo)) {
            Long saleId = appUserService.getIdByMobile(saleNo);
            params.put("saleId", saleId);
        }
        Long sysUserId = this.getUserId();
        UserDO sysUser = userService.get(sysUserId);
        if (sysUser.getTeamFlag() == 1) {
            List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
            List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(saleIds)) {
                return new PageUtils(Collections.emptyList(), 0);
            }
            params.put("saleIdList", saleIds);
        }
        params.put("type",99);
        Query query = new Query(params);
        List<PayDO> payList = payService.list(query);
        List<Long> saleIds = payList.stream().filter(o->o.getSaleId() != null).map(PayDO::getSaleId).distinct().collect(Collectors.toList());
        List<Long> userIds = payList.stream().filter(o->o.getUserId() != null).map(PayDO::getUserId).distinct().collect(Collectors.toList());
        List<UserDTO> saleUserDOList = appUserService.getByIdList(saleIds);

        List<UserDTO> userDOList = appUserService.getByIdList(userIds);

        List<WithdrawalDo> withdrawalList = new ArrayList<>();
        payList.forEach(o->{
            WithdrawalDo withdrawalDo = new WithdrawalDo();
            withdrawalDo.setId(o.getId());
            withdrawalDo.setOrderNo(o.getOrderNo());
            withdrawalDo.setUserId(o.getUserId());
            if (CollectionUtils.isNotEmpty(saleUserDOList) && o.getSaleId() != null) {
                withdrawalDo.setSaleNo(
                        saleUserDOList.stream().filter(i -> o.getSaleId().equals(i.getId())).findFirst().orElse(new UserDTO())
                                .getMobile());
            } else {
                withdrawalDo.setSaleNo(null);
            }

            if (CollectionUtils.isNotEmpty(userDOList)) {

                withdrawalDo.setParentId(userDOList.stream().filter(i -> o.getUserId().equals(i.getId())).findFirst().orElse(new UserDTO())
                        .getParentId());
                withdrawalDo.setMobile(userDOList.stream().filter(i -> o.getUserId().equals(i.getId())).findFirst().orElse(new UserDTO())
                        .getMobile());
                withdrawalDo.setRegDate(DateUtils.format(userDOList.stream().filter(i -> o.getUserId().equals(i.getId())).findFirst().orElse(new UserDTO())
                        .getCreateTime(),DateUtils.DATE_TIME_PATTERN));
            } else {
                withdrawalDo.setParentId(null);
                withdrawalDo.setMobile(null);
                withdrawalDo.setRegDate(null);
            }
            PayDO newPay = payService.getNewOrderByUserId(o.getUserId());
            //当前用户充值总金额
            BigDecimal localUserAmount = userService.getUserRechargeAmount(o.getUserId());
            withdrawalDo.setRechargeAmount(localUserAmount);
            withdrawalDo.setLastRechargeDate(newPay !=null ? DateUtils.format(newPay.getCreateTime(),DateUtils.DATE_TIME_PATTERN):"");
            withdrawalDo.setWithdrawalAmount(o.getAmount());
            withdrawalDo.setWithdrawalApplyTime(DateUtils.format(o.getCreateTime(),DateUtils.DATE_TIME_PATTERN));
            withdrawalDo.setWithdrawalAuditStatus(o.getApplyStatus());
            withdrawalDo.setWithdrawalAuditStatusDesc(PayApplyStatusEnum.getDescByCode(o.getApplyStatus()));
            withdrawalDo.setFailReason(o.getRemark());
            withdrawalDo.setWithdrawalAuditTime(DateUtils.format(o.getUpdateTime(),DateUtils.DATE_TIME_PATTERN));
            withdrawalDo.setWithdrawalTotalAmount(selectUserWithdrawalTotalAmont(o.getUserId(), o));
            withdrawalList.add(withdrawalDo);
        });

        int total = payService.count(query);
        PageUtils pageUtils = new PageUtils(withdrawalList, total);
        return pageUtils;
    }

    /**
     * 查询用户提现总金额
     * @param userId
     * @param o
     * @return
     */
    private BigDecimal selectUserWithdrawalTotalAmont(Long userId, PayDO o) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("offset", 1);
        params.put("limit", 9999999);
        params.put("type", 99);
        params.put("applyStatus", 2);
        params.put("userId", userId);
        Query query = new Query(params);
        List<PayDO> withdrawalTotalList = payService.list(query);
        BigDecimal total = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(withdrawalTotalList)) {
            return total;
        }
        for (PayDO payDO : withdrawalTotalList) {
            if (o.getId().equals(payDO.getId())) {
                continue;
            }
            total = total.add(payDO.getAmount());
        }
        return total;
    }


    @ResponseBody
    @RequestMapping("/auditSuccess")
    @RequiresPermissions("system:withdrawal:auditSuccess")
    @Log
    public R auditSuccess(String orderNo) {
        ApplyRequest request = new ApplyRequest();
        request.setOrderNo(orderNo);
        boolean result = payService.apply(request,true);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/auditFail/{orderNo}")
    @RequiresPermissions("system:withdrawal:auditFail")
    String auditFailPage(@PathVariable("orderNo") String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        return "system/withdrawal/auditFail";
    }


    @ResponseBody
    @RequestMapping("/auditFailDo")
    @RequiresPermissions("system:withdrawal:auditFail")
    public R auditFailDo(WithdrawalDo withdrawalDo) {
        ApplyRequest request = new ApplyRequest();
        request.setOrderNo(withdrawalDo.getOrderNo());
        request.setRemark(withdrawalDo.getFailReason());
        boolean result = payService.apply(request,false);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

}