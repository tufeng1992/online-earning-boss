package com.powerboot.system.controller;

import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.consts.PayEnum;
import com.powerboot.system.consts.PayExportExcelModel;
import com.powerboot.system.consts.PayTypeEnum;
import com.powerboot.system.dao.PayDao;
import com.powerboot.system.domain.PayDO;
import com.powerboot.system.domain.PayImportDo;
import com.powerboot.system.dto.PayExportDto;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.dto.UserDTO;
import com.powerboot.system.request.ChangeSdPriceRequest;
import com.powerboot.system.request.PaySwitchUpdateRequest;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.DictService;
import com.powerboot.system.service.PayService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.utils.DateUtils;
import com.powerboot.utils.ExcelExports;
import com.powerboot.utils.RedisUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付结果
 */

@Controller
@RequestMapping("/system/pay")
public class PayController extends BaseController {

    @Autowired
    AppUserService appUserService;
    @Autowired
    SysUserMappingService sysUserMappingService;
    @Autowired
    DictService dictService;
    @Autowired
    private PayService payService;
    @Autowired
    private PayDao payDao;

    @ResponseBody
    @GetMapping("/payExport")
    @RequiresPermissions("system:pay:payExport")
    public void payExport(@RequestParam(value = "startTime") String startTimeStr,
        @RequestParam(value = "endTime") String endTimeStr,
        HttpServletResponse resp) {
        Date startTime = null;
        if (StringUtils.isNotBlank(startTimeStr)) {
            startTime = DateUtils.parseDateYMDHMS(startTimeStr);
        }
        Date endTime = null;
        if (StringUtils.isNotBlank(endTimeStr)) {
            endTime = DateUtils.parseDateYMDHMS(endTimeStr);
        }
        DecimalFormat df2 = new DecimalFormat("0.00");
        List<PayExportDto> exportDtoList = new ArrayList<>();
        long payId = 0;
        int limit = 500;
        while (limit == 500) {
            List<PayImportDo> list = payDao.selectImportPay(payId, startTime, endTime, limit);
            if (CollectionUtils.isNotEmpty(list)) {
                limit = list.size();
                payId = list.get(list.size() - 1).getId();
                list.forEach(o -> {
                    PayExportDto dto = new PayExportDto();
                    dto.setAmount(df2.format(o.getAmount()));
                    dto.setCreateTime(DateUtils.formatDateYMDHMS(o.getCreateTime()) + "\t");
                    dto.setMobile(o.getMobile() + "\t");
                    dto.setThirdNo(o.getThirdNo());
                    dto.setType(o.getType() == 99 ? "提现" : "充值");
                    exportDtoList.add(dto);
                });
            } else {
                limit = 0;
            }
        }
        ExcelExports.SimpleExcelModel<PayExportDto> excelModel = new ExcelExports.SimpleExcelModel<>();
        Date now = new Date();
        excelModel.setFileName("支付流水详情-" + DateUtils.formatDateYMDChinese(now));
        excelModel.setRowList(exportDtoList);
        excelModel.setExportFields(PayExportExcelModel.fields);
        excelModel.setFieldHeaderMap(PayExportExcelModel.headerNameMap);
        ExcelExports.webExportExcel(excelModel, resp);
    }

    @GetMapping("/paySwitch")
    @RequiresPermissions("system:pay:paySwitch")
    public String paySwitch(Model model) {
        Integer sysWithdrawalCheck = RedisUtils.getValue(DictConsts.SYS_WITHDRAWAL_CHECK, Integer.class);
        Integer paySwitch = RedisUtils.getValue(DictConsts.PAY_SWITCH, Integer.class);
        Integer payChannelBranch = RedisUtils.getValue(DictConsts.PAY_CHANNEL_BRANCH, Integer.class);
        Integer payoutChannelBranch = RedisUtils.getValue(DictConsts.PAYOUT_CHANNEL_BRANCH, Integer.class);
        Integer goPaySwitch = RedisUtils.getValue(DictConsts.GO_PAY_SWITCH, Integer.class);
        Integer applySwitch = RedisUtils.getValue(DictConsts.APPLY_SWITCH, Integer.class);
        Integer inviteFlagHalf = RedisUtils.getValue(DictConsts.INVITE_FLAG_HALF, Integer.class);
        model.addAttribute("inviteFlagHalf", inviteFlagHalf);
        model.addAttribute("applySwitch", applySwitch);
        model.addAttribute("sysWithdrawalCheck", sysWithdrawalCheck);
        model.addAttribute("paySwitch", paySwitch);
        model.addAttribute("payChannelBranch", payChannelBranch);
        model.addAttribute("payoutChannelBranch", payoutChannelBranch);
        model.addAttribute("goPaySwitch", goPaySwitch);

        String priceSection = RedisUtils.getValue(DictConsts.PRICE_SECTION, String.class);
        String[] priceSectionArray = priceSection.split("[|]");
        for (int i = 0; i < priceSectionArray.length; i++) {
            String[] priceArray = priceSectionArray[i].split(",");
            model.addAttribute("priceMin" + (i + 1), priceArray[0]);
            model.addAttribute("priceMax" + (i + 1), priceArray[1]);
        }
        return "system/pay/paySwitch";
    }

    @Log("价格区间切换")
    @PostMapping("/changeSdPrice")
    @RequiresPermissions("system:pay:paySwitch")
    @ResponseBody
    public R changeSdPrice(ChangeSdPriceRequest request) {
        if (Objects.isNull(request.getIndex()) || StringUtils.isBlank(request.getPriceMin()) ||
            StringUtils.isBlank(request.getPriceMax())) {
            return R.error("请输入价格");
        }
        String value = "";
        String priceSection = RedisUtils.getValue(DictConsts.PRICE_SECTION, String.class);
        String[] priceSectionArray = priceSection.split("[|]");
        for (int i = 1; i <= priceSectionArray.length; i++) {
            if (i == request.getIndex()) {
                value = value + request.getPriceMin() + "," + request.getPriceMax() + "|";
            } else {
                value = value + priceSectionArray[i - 1] + "|";
            }
        }
        value = value.substring(0, value.length() - 1);
        dictService.updateByKey(DictConsts.PRICE_SECTION, value);
        return R.ok("切换成功");
    }

    @Log("支付开关切换")
    @PostMapping("/paySwitchUpdate")
    @RequiresPermissions("system:pay:paySwitch")
    @ResponseBody
    public R paySwitchUpdate(PaySwitchUpdateRequest request) {
        if (StringUtils.isBlank(request.getCacheKey()) || StringUtils.isBlank(request.getCacheValue())) {
            return R.error();
        }
        int count = dictService.updateByKey(request.getCacheKey(), request.getCacheValue());
        if (count > 0) {
            return R.ok();
        }
        return R.error();
    }


    /**
     * 修改支付文案
     */
    @Log("修改支付提示文案")
    @ResponseBody
    @RequestMapping("/updatePayTip")
    @RequiresPermissions("system:pay:payTipEdit")
    public R updatePayTip(PaySwitchUpdateRequest request) {
        if (StringUtils.isBlank(request.getCacheKey()) || StringUtils.isBlank(request.getCacheValue())) {
            return R.error();
        }
        int count = dictService.updateByKey(request.getCacheKey(), request.getCacheValue());
        if (count > 0) {
            return R.ok();
        }
        return R.error();
    }


    @GetMapping()
    @RequiresPermissions("system:pay:pay")
    String Pay() {
        return "system/pay/pay";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:pay:pay")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Long sysUserId = this.getUserId();
        List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
        List<Long> userIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        params.put("userIds", userIds);
        String saleNo = params.get("saleNo") == null ? null : params.get("saleNo").toString();
        if (!StringUtils.isBlank(saleNo)) {
            Long saleId = appUserService.getIdByMobile(saleNo);
            params.put("saleId", saleId);
        }
        //查询列表数据
        Query query = new Query(params);
        List<PayDO> payList = payService.list(query);
        List<Long> saleIds = payList.stream().filter(o -> o.getSaleId() != null).map(PayDO::getSaleId).distinct()
            .collect(Collectors.toList());
        List<UserDTO> saleUserDOList = appUserService.getByIdList(saleIds);
        payList.forEach(o -> {
            o.setTypeText(PayTypeEnum.getNameByCode(o.getType()));
            o.setStatusText(PayEnum.getNameByCode(o.getStatus()));
            if (CollectionUtils.isNotEmpty(saleUserDOList) && o.getSaleId() != null) {
                o.setSaleNo(
                    saleUserDOList.stream().filter(i -> o.getSaleId().equals(i.getId())).findFirst()
                        .orElse(new UserDTO())
                        .getMobile());
            } else {
                o.setSaleNo(null);
            }

        });
        int total = payService.count(query);
        PageUtils pageUtils = new PageUtils(payList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("system:pay:add")
    String add() {
        return "system/pay/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:pay:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        PayDO pay = payService.get(id);
        model.addAttribute("pay", pay);
        return "system/pay/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:pay:add")
    public R save(PayDO pay) {
        if (payService.save(pay) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:pay:edit")
    public R update(PayDO pay) {
        payService.update(pay);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/refreshPay")
    @RequiresPermissions("system:pay:refreshPay")
    @Log
    public R refreshPay(String orderNo) {
        boolean result = payService.refreshPay(orderNo);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

    @ResponseBody
    @RequestMapping("/successPay")
    @RequiresPermissions("system:pay:successPay")
    @Log
    public R successPay(String orderNo) {
        Boolean result = payService.successPay(orderNo);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:pay:remove")
    public R remove(Long id) {
        if (payService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:pay:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        payService.batchRemove(ids);
        return R.ok();
    }

}
