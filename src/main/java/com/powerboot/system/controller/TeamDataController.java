package com.powerboot.system.controller;

import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.system.consts.AmountConstants;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.response.MainResp;
import com.powerboot.system.response.PayResp;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.BalanceService;
import com.powerboot.system.service.DictService;
import com.powerboot.system.service.OrderService;
import com.powerboot.system.service.PayService;
import com.powerboot.system.service.SummaryTableService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.impl.FinancialOrderServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.powerboot.system.service.impl.UserServiceImpl;
import com.powerboot.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeamDataController extends BaseController {

    @Autowired
    PayService payService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    DictService dictService;
    @Autowired
    private FinancialOrderServiceImpl financialOrderService;
    @Autowired
    SummaryTableService summaryTableService;
    @Autowired
    OrderService orderService;
    @Autowired
    BalanceService balanceService;
    @Autowired
    SysUserMappingService sysUserMappingService;
    @Autowired
    UserServiceImpl userService;

    @Log("团队数据概览")
    @GetMapping("/team/data/main")
    @RequiresPermissions("system:team:dataMain")
    public String getTeamDataMain(Model model) {

        //当前登录boss的账号id
        Long sysUserId = this.getUserId();
        UserDO userDO = userService.get(sysUserId);
        if (userDO.getTeamLeader() == 0) {
            return "/";
        }
        //查找boss账号和app账号id的映射关系
        List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        MainResp resp = new MainResp();
        if (CollectionUtils.isEmpty(saleIds)) {
            model.addAttribute("resp", resp);
            return "system/team/teamMain";
        }
        resp.setUserCountResp(appUserService.getUserCount(saleIds));
        resp.setRelCharge(payService.getCountByTypeStatusAndDate(Arrays.asList(1), 2, saleIds));
        resp.setRelWithdraw(payService.getCountByTypeStatusAndDate(Arrays.asList(99), 2, saleIds));
        resp.setWithdraw(balanceService.getCountByTypeStatusAndDate(Arrays.asList(7), 2, saleIds));
        resp.setVIPPay(payService.getCountByTypeStatusAndDate(Arrays.asList(2, 3, 4, 5), 2, saleIds));
        resp.setCharge(payService.getCountByTypeStatusAndDate(Arrays.asList(1), null, saleIds));
        resp.setFirstRechargeAmount(balanceService.getCountByTypeStatusAndDate(Arrays.asList(3), 2, saleIds));
        resp.setFinOrderResp(financialOrderService.getFinOrderResp(saleIds));
        String sysPayOut = RedisUtils.getString("SYS_PAY_OUT:" + sysUserId);
        resp.setSysPayOut(StringUtils.isNotBlank(sysPayOut) ? new BigDecimal(sysPayOut) : BigDecimal.ZERO);

        PayResp relCharge = resp.getRelCharge();
        PayResp relChargeTaxDeduction = new PayResp();
        relChargeTaxDeduction.setAmount(relCharge.getAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setCount(relCharge.getCount());
        relChargeTaxDeduction.setLocalAmount(relCharge.getLocalAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setLocalCount(relCharge.getLocalCount());
        relChargeTaxDeduction.setYesterdayAmount(relCharge.getYesterdayAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setYesterdayCount(relCharge.getYesterdayCount());
        resp.setRelChargeTaxDeduction(relChargeTaxDeduction);

        PayResp userAmount = new PayResp();
        BigDecimal userAllAmount = appUserService.getAllAmount(saleIds);
        BigDecimal finAmount = financialOrderService.getAmountByStatus(1, saleIds);
        userAmount.setAmount(userAllAmount.add(finAmount));
        userAmount.setLocalAmount(BigDecimal.ZERO);
        userAmount.setYesterdayAmount(BigDecimal.ZERO);
        resp.setUserAmount(userAmount);

        BigDecimal addSum = balanceService.sumAmount(saleIds);
        resp.setAddAmount(addSum == null ? BigDecimal.ZERO : addSum);

        //vip购买金额去除5%手续费
        resp.getVIPPay().setAmount(resp.getVIPPay().getAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getVIPPay().setLocalAmount(resp.getVIPPay().getLocalAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getVIPPay().setYesterdayAmount(resp.getVIPPay().getYesterdayAmount().multiply(AmountConstants.RECHARGE_RATE));
        //提现金额加上3%手续费
        resp.getRelWithdraw().setAmount(resp.getRelWithdraw().getAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));
        resp.getRelWithdraw().setLocalAmount(resp.getRelWithdraw().getLocalAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));
        resp.getRelWithdraw().setYesterdayAmount(resp.getRelWithdraw().getYesterdayAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));

        List<UserDO> userDOList = userService.getByLeader(sysUserId);
        String userList = "";
        for (UserDO user : userDOList) {
            userList = userList + user.getUserId() + ",";
        }
        userList = userList.substring(0, userList.length() - 1);
        model.addAttribute("userList", userList);
        model.addAttribute("sysUserId", sysUserId);
        model.addAttribute("resp", resp);
        return "system/team/teamMain";
    }

    @Log("个人数据概览")
    @GetMapping("/person/data/main")
    @RequiresPermissions("system:person:dataMain")
    public String getPersonDataMain(Model model) {

        //当前登录boss的账号id
        Long sysUserId = this.getUserId();
        //查找boss账号和app账号id的映射关系
        Long saleId = appUserService.getIdByMobile(sysUserId.toString());
        List<Long> saleIds = new ArrayList<>();
        saleIds.add(saleId);
        MainResp resp = new MainResp();
        if (CollectionUtils.isEmpty(saleIds)) {
            model.addAttribute("resp", resp);
            return "system/team/personMain";
        }
        resp.setUserCountResp(appUserService.getUserCount(saleIds));
        resp.setRelCharge(payService.getCountByTypeStatusAndDate(Arrays.asList(1), 2, saleIds));
        resp.setRelWithdraw(payService.getCountByTypeStatusAndDate(Arrays.asList(99), 2, saleIds));
        resp.setWithdraw(balanceService.getCountByTypeStatusAndDate(Arrays.asList(7), 2, saleIds));
        resp.setVIPPay(payService.getCountByTypeStatusAndDate(Arrays.asList(2, 3, 4, 5), 2, saleIds));
        resp.setCharge(payService.getCountByTypeStatusAndDate(Arrays.asList(1), null, saleIds));
        resp.setFirstRechargeAmount(balanceService.getCountByTypeStatusAndDate(Arrays.asList(3), 2, saleIds));
        resp.setFinOrderResp(financialOrderService.getFinOrderResp(saleIds));

        PayResp relCharge = resp.getRelCharge();
        PayResp relChargeTaxDeduction = new PayResp();
        relChargeTaxDeduction.setAmount(relCharge.getAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setCount(relCharge.getCount());
        relChargeTaxDeduction.setLocalAmount(relCharge.getLocalAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setLocalCount(relCharge.getLocalCount());
        relChargeTaxDeduction.setYesterdayAmount(relCharge.getYesterdayAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setYesterdayCount(relCharge.getYesterdayCount());
        resp.setRelChargeTaxDeduction(relChargeTaxDeduction);

        PayResp userAmount = new PayResp();
        BigDecimal userAllAmount = appUserService.getAllAmount(saleIds);
        BigDecimal finAmount = financialOrderService.getAmountByStatus(1, saleIds);
        userAmount.setAmount(userAllAmount.add(finAmount));
        userAmount.setLocalAmount(BigDecimal.ZERO);
        userAmount.setYesterdayAmount(BigDecimal.ZERO);
        resp.setUserAmount(userAmount);
        //用户加钱
        //resp.setAddAmount(balanceService.sumAmount(saleIds));

        //vip购买金额去除5%手续费
        resp.getVIPPay().setAmount(resp.getVIPPay().getAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getVIPPay().setLocalAmount(resp.getVIPPay().getLocalAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getVIPPay().setYesterdayAmount(resp.getVIPPay().getYesterdayAmount().multiply(AmountConstants.RECHARGE_RATE));
        //提现金额加上3%手续费
        resp.getRelWithdraw().setAmount(resp.getRelWithdraw().getAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));
        resp.getRelWithdraw().setLocalAmount(resp.getRelWithdraw().getLocalAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));
        resp.getRelWithdraw().setYesterdayAmount(resp.getRelWithdraw().getYesterdayAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));

        model.addAttribute("resp", resp);
        return "system/team/personMain";
    }

}
