package com.powerboot.system.controller;

import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.R;
import com.powerboot.common.utils.ShiroUtils;
import com.powerboot.system.consts.AmountConstants;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.domain.DataBossVo;
import com.powerboot.system.domain.DictDO;
import com.powerboot.system.domain.PayOutDO;
import com.powerboot.system.response.MainResp;
import com.powerboot.system.response.PayResp;
import com.powerboot.system.service.*;
import com.powerboot.system.service.impl.DictServiceImpl;
import com.powerboot.system.service.impl.FinancialOrderServiceImpl;
import com.powerboot.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

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
    PayOutService payOutService;
    @Autowired
    private SysUserMappingService sysUserMappingService;

    @Log("主页")
    @GetMapping("/mainV1")
    public String getMainV1(Model model) {
        return "mainV1";
    }

    @Log("数据概览")
    @GetMapping("/main")
    @RequiresPermissions("system:main:data")
    public String getMain(Model model) {
        MainResp resp = new MainResp();
        //查询列表数据
        Long sysUserId = this.getUserId();
        List<Long> saleIdList = null;
        if (ShiroUtils.getUserId() != 1L) {
            saleIdList = sysUserMappingService.getUserMappingSaleIds(sysUserId);
            if (CollectionUtils.isEmpty(saleIdList)) {
                model.addAttribute("resp", resp);
                return "main";
            }
        }
        resp.setUserCountResp(appUserService.getUserCount(saleIdList));
        appUserService.getUserActivateCount(resp.getUserCountResp(), saleIdList);
        appUserService.getUserContactCount(resp.getUserCountResp(), saleIdList);
        appUserService.getUserTaskCount(resp.getUserCountResp(), saleIdList);
        appUserService.getUserRechargeCount(resp.getUserCountResp(), saleIdList);

        resp.setUserReferral(appUserService.getUserReferral(saleIdList));
        resp.setSaleReferral(appUserService.getSaleReferral(saleIdList));
        resp.setVIPPay(payService.getCountByTypeStatusAndDate(Arrays.asList(2, 3, 4, 5), 2, saleIdList));
        resp.setCharge(payService.getCountByTypeStatusAndDate(Arrays.asList(1), null, saleIdList));
        resp.setRelCharge(payService.getCountByTypeStatusAndDate(Arrays.asList(1), 2, saleIdList));
        resp.setWithdraw(balanceService.getCountByTypeStatusAndDate(Arrays.asList(7), 2, saleIdList));
        resp.setRelWithdraw(payService.getCountByTypeStatusAndDate(Arrays.asList(99), 2, saleIdList));
        resp.setFirstRechargeAmount(balanceService.getCountByTypeStatusAndDate(Arrays.asList(3), 2, saleIdList));
        resp.setFinOrderResp(financialOrderService.getFinOrderResp(saleIdList));
        resp.setSysPayOut(new BigDecimal(dictService.getByKey("SYS_PAY_OUT").getValue()));
        resp.setRegisterCountResp(balanceService.selectRegisterResp(saleIdList));

        PayResp relCharge = resp.getRelCharge();

        PayResp relChargeTaxDeduction = new PayResp();
        relChargeTaxDeduction.setAmount(relCharge.getAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setCount(relCharge.getCount());
        relChargeTaxDeduction.setLocalAmount(relCharge.getLocalAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setLocalCount(relCharge.getLocalCount());
        relChargeTaxDeduction.setYesterdayAmount(relCharge.getYesterdayAmount().multiply(AmountConstants.RECHARGE_RATE));
        relChargeTaxDeduction.setYesterdayCount(relCharge.getYesterdayCount());

        PayResp userAmount = new PayResp();
        BigDecimal userAllAmount = appUserService.getAllAmount(saleIdList);
        BigDecimal finAmount = financialOrderService.getAmountByStatus(1, saleIdList);
        userAmount.setAmount(userAllAmount.add(finAmount));
        userAmount.setLocalAmount(BigDecimal.ZERO);
        userAmount.setYesterdayAmount(BigDecimal.ZERO);

        List<DataBossVo> dataBossVos = summaryTableService.listAndLimit(2, saleIdList);
        BigDecimal yesterdayNetProfit = BigDecimal.ZERO;
        Integer yesterdayUserCount = 0;
        if (CollectionUtils.isNotEmpty(dataBossVos)) {
            if (dataBossVos.size() > 0) {
                userAmount.setLocalAmount(userAmount.getAmount().subtract(dataBossVos.get(0).getFinancialProfitCountAmount()).subtract(dataBossVos.get(0).getVipBalanceCount()));
            }
            if (dataBossVos.size() > 1) {
                userAmount.setYesterdayAmount(dataBossVos.get(0).getVipBalanceCount().add(dataBossVos.get(0).getFinancialProfitCountAmount()).subtract(dataBossVos.get(1).getVipBalanceCount()).subtract(dataBossVos.get(1).getFinancialProfitCountAmount()));
                yesterdayNetProfit = dataBossVos.get(0).getRechargeAmount().subtract(dataBossVos.get(0).getWithdrawAmount()).add(dataBossVos.get(0).getVipPayAmount());
                yesterdayUserCount = dataBossVos.get(0).getVipCount();
            }
        }

        resp.setUserAmount(userAmount);
        resp.setRelChargeTaxDeduction(relChargeTaxDeduction);

        resp.setOrderResp(orderService.getOrderResp(saleIdList));
        resp.setAddAmount(balanceService.sumAmount(saleIdList));
        resp.setTaskResponse(orderService.getTaskResponse(resp.getUserCountResp().getUserCount(), yesterdayUserCount, saleIdList));
        resp.setUserLoginRes(appUserService.getUserLoginRes(resp.getUserCountResp().getUserCount(), saleIdList));
        resp.setNoSaleUser(appUserService.getNoSaleUser(resp.getUserCountResp().getUserCount(), saleIdList));
        resp.setRechangeResponse(payService.getRechangeResponse(resp.getUserCountResp().getUserCount(), saleIdList));


        resp.getVIPPay().setAmount(resp.getVIPPay().getAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getVIPPay().setLocalAmount(resp.getVIPPay().getLocalAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getVIPPay().setYesterdayAmount(resp.getVIPPay().getYesterdayAmount().multiply(AmountConstants.RECHARGE_RATE));
        resp.getRelWithdraw().setAmount(resp.getRelWithdraw().getAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));
        resp.getRelWithdraw().setLocalAmount(resp.getRelWithdraw().getLocalAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));
        resp.getRelWithdraw().setYesterdayAmount(resp.getRelWithdraw().getYesterdayAmount().multiply(AmountConstants.WITHDRAW_REAL_RATE));


        PayResp netProfit = new PayResp();
        netProfit.setAmount(resp.getRelChargeTaxDeduction().getAmount().subtract(resp.getRelWithdraw().getAmount()).add(resp.getVIPPay().getAmount()));
        netProfit.setLocalAmount(resp.getRelChargeTaxDeduction().getLocalAmount().subtract(resp.getRelWithdraw().getLocalAmount()).add(resp.getVIPPay().getLocalAmount()));
        netProfit.setYesterdayAmount(yesterdayNetProfit);
        resp.setNetProfit(netProfit);
        model.addAttribute("resp", resp);
        return "main";
    }

    @Log("添加系统提出金额")
    @GetMapping("/main/amount")
    @RequiresPermissions("system:main:addamount")
    public String add() {
        return "system/sys/add";
    }

    @Log("团队长添加系统提出金额")
    @GetMapping("/main/addAmountTeamLeader")
    @RequiresPermissions("system:main:addAmountTeamLeader")
    public String addAmountTeamLeader() {
        return "system/sys/addAmountTeamLeader";
    }

    @Log("添加系统提出金额")
    @PostMapping("/main/amount")
    @ResponseBody
    public R addAmount(BigDecimal amount) {
        if (amount == null) {
            return R.error("金额必须输入");
        }
        DictDO payOut = dictService.getByKey("SYS_PAY_OUT");
        BigDecimal localAmount = new BigDecimal(payOut.getValue());
        payOut.setValue(localAmount.add(amount).toString());
        return dictService.update(payOut) > 0 ? R.ok() : R.error();
    }

    @Log("团队长添加系统提出金额")
    @PostMapping("/main/amountTeamLeader")
    @ResponseBody
    public R amountTeamLeader(BigDecimal amount) {
        if (amount == null) {
            return R.error("金额必须输入");
        }
        Long sysUserId = this.getUserId();
        BigDecimal totalAmount = BigDecimal.ZERO;
        String key = "SYS_PAY_OUT:" + sysUserId;
        String oldAmount = RedisUtils.getString(key);
        if (StringUtils.isNotBlank(oldAmount)) {
            totalAmount = totalAmount.add(new BigDecimal(oldAmount));
        }
        totalAmount = totalAmount.add(amount);
        RedisUtils.setValue(key, totalAmount.toString(), DictConsts.DICT_CACHE_LIVE_TIME);
        PayOutDO payOut = new PayOutDO();
        payOut.setUserId(sysUserId);
        payOut.setAmount(amount);
        payOutService.save(payOut);
        logger.info("修改团队长提出金额缓存，key=" + key + ",value=" + RedisUtils.getString(key));
        return R.ok();
    }

}
