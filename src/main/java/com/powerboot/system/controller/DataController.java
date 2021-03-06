package com.powerboot.system.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.JsonUtils;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.R;
import com.powerboot.common.utils.ShiroUtils;
import com.powerboot.system.consts.AmountConstants;
import com.powerboot.system.consts.SaleDataExcelModel;
import com.powerboot.system.domain.*;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.SummaryTableService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.vo.DataCountVO;
import com.powerboot.utils.DateUtils;
import com.powerboot.utils.ExcelExports;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/system/data")
public class DataController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    SummaryTableService summaryTableService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    SysUserMappingService sysUserMappingService;

    /**
     * ????????????
     */
    @GetMapping("/dataBoss")
    @RequiresPermissions("system:data:dataBoss")
    String dataBoss() {
        /*List<DataBossVo> list = summaryTableService.list();
        list.forEach(o -> {
            o.setNetProfit(o.getRechargeAmount().add(o.getVipPayAmount()).subtract(o.getWithdrawAmount()));
            o.setShowRed(o.getNetProfit().compareTo(BigDecimal.ZERO) > 0 ? 1 : 0);
        });
        model.addAttribute("list", list);*/
        return "system/data/dataBoss";
    }

    @ResponseBody
    @GetMapping("/dataBossList")
    @RequiresPermissions("system:data:dataBoss")
    PageUtils dataBossList(@RequestParam Map<String, Object> params) {
        boolean isAdmin = ShiroUtils.getUserId() == 1L;
        if (!isAdmin) {
            AppUserDO appUserDO = appUserService.getByMobile(ShiroUtils.getUserId().toString());
            params.put("saleId", appUserDO.getId());
        }
        List<DataBossVo> list = summaryTableService.list(params);
        List<DataBossVo> res = Lists.newArrayList();
        Map<String, DataBossVo> adminMergeMap = Maps.newHashMap();
        list.forEach(o -> {
            o.setNetProfit(o.getRechargeAmount().add(o.getVipPayAmount()).subtract(o.getWithdrawAmount()));
            o.setShowRed(o.getNetProfit().compareTo(BigDecimal.ZERO) > 0 ? 1 : 0);
            if (!isAdmin) {
                res.add(o);
            } else {
                String dateStr = DateUtils.format(o.getGeneratedDate(), DateUtils.SIMPLE_DATEFORMAT_YMD);
                DataBossVo dataBossVo = adminMergeMap.computeIfAbsent(dateStr, k -> new DataBossVo());
                dataBossVo.setGeneratedDate(o.getGeneratedDate());
                dataBossVo.setDate(o.getDate());
                mergeDataBossVo(o, dataBossVo);
            }
        });
        if (isAdmin) {
            adminMergeMap.forEach((k, v) -> {
                res.add(v);
            });
        }
        PageUtils pageUtils = new PageUtils(res.stream().
                sorted(Comparator.comparing(DataBossVo::getGeneratedDate).reversed()).collect(Collectors.toList()), res.size());
        return pageUtils;
    }

    /**
     * dataBossVo????????????
     * @param source
     * @param target
     */
    private void mergeDataBossVo(DataBossVo source, DataBossVo target) {
        target.setVipCount(mergeInteger(target.getVipCount(), source.getVipCount()));
        target.setVip1Count(mergeInteger(target.getVip1Count(), source.getVip1Count()));
        target.setVip2Count(mergeInteger(target.getVip2Count(), source.getVip2Count()));
        target.setVip3Count(mergeInteger(target.getVip3Count(), source.getVip3Count()));
        target.setVip4Count(mergeInteger(target.getVip4Count(), source.getVip4Count()));
        target.setVip5Count(mergeInteger(target.getVip5Count(), source.getVip5Count()));
        target.setVipValidCount(mergeInteger(target.getVipValidCount(), source.getVipValidCount()));
        target.setRechargeAmount(mergeBigDecimal(target.getRechargeAmount(), source.getRechargeAmount()));
        target.setVipBalanceCount(mergeBigDecimal(target.getVipBalanceCount(), source.getVipBalanceCount()));
        target.setCommissionsAmount(mergeBigDecimal(target.getCommissionsAmount(), source.getCommissionsAmount()));
        target.setFinancialProfitAmount(mergeBigDecimal(target.getFinancialProfitAmount(), source.getFinancialProfitAmount()));
        target.setLocalUserCount(mergeInteger(target.getLocalUserCount(), source.getLocalUserCount()));
        target.setRechargeCount(mergeInteger(target.getRechargeCount(), source.getRechargeCount()));
        target.setWithdrawCount(mergeInteger(target.getWithdrawCount(), source.getWithdrawCount()));
        target.setWithdrawAmount(mergeBigDecimal(target.getWithdrawAmount(), source.getWithdrawAmount()));
        target.setVipPayCount(mergeInteger(target.getVipPayCount(), source.getVipPayCount()));
        target.setVipPayAmount(mergeBigDecimal(target.getVipPayAmount(), source.getVipPayAmount()));
        target.setFirstRechargeAmount(mergeBigDecimal(target.getFirstRechargeAmount(), source.getFirstRechargeAmount()));
        target.setFinancialProfitInAmount(mergeBigDecimal(target.getFinancialProfitInAmount(), source.getFinancialProfitInAmount()));
        target.setFinancialProfitOutAmount(mergeBigDecimal(target.getFinancialProfitOutAmount(), source.getFinancialProfitOutAmount()));
        target.setFinancialProfitCountAmount(mergeBigDecimal(target.getFinancialProfitCountAmount(), source.getFinancialProfitCountAmount()));
        target.setNetProfit(mergeBigDecimal(target.getNetProfit(), source.getNetProfit()));
        target.setShowRed(mergeInteger(target.getShowRed(), source.getShowRed()));
        target.setUserReferral(mergeInteger(target.getUserReferral(), source.getUserReferral()));
        target.setSaleReferral(mergeInteger(target.getSaleReferral(), source.getSaleReferral()));
    }

    private Integer mergeInteger(Integer target, Integer source) {
        if (source == null) {
            return target;
        }
        if (target == null) {
            return source;
        }
        return target + source;
    }

    private BigDecimal mergeBigDecimal(BigDecimal target, BigDecimal source) {
        if (source == null) {
            return target;
        }
        if (target == null) {
            return source;
        }
        return target.add(source);
    }

    @GetMapping("/dataEdit/{id}")
    @RequiresPermissions("system:data:dataBoss")
    String dataEdit(@PathVariable("id") Integer id, Model model) {
        DataBossVo dataBossVo = summaryTableService.getById(id);
        model.addAttribute("dataBossVo", dataBossVo);
        return "system/data/dataBossEdit";
    }

    @ResponseBody
    @RequestMapping("/dataEdit/update")
    @RequiresPermissions("system:data:dataBoss")
    public R dataEditUpdate(DataBossVo dataBossVo) {
        DataBossVo oldDataBossVo = summaryTableService.getById(dataBossVo.getId());
        summaryTableService.updateById(dataBossVo);
        logger.info("?????????????????????id={},old={},new={}",
                dataBossVo.getId(), JsonUtils.toJSONString(oldDataBossVo), JsonUtils.toJSONString(dataBossVo));
        return R.ok();
    }


    @GetMapping("/dataSale")
    @RequiresPermissions("system:data:dataSale")
    String dataSale(Model model) {
        return "system/data/dataSale";
    }

    /**
     * ????????????
     * offset ????????????
     * limit ??????
     * operateNo ????????????
     * startTime ??????????????????
     * endTime ??????????????????
     */
    @ResponseBody
    @GetMapping("/dataSale/list")
    public PageUtils list(@RequestParam(value = "limit") Integer limit,
                          @RequestParam(value = "offset") Integer offset,
                          @RequestParam(value = "operateNo") String operateNo,
                          @RequestParam(value = "userId") Long userId,
                          @RequestParam(value = "userMobile") String userMobile,
                          @RequestParam(value = "userLevel") Integer userLevel,
                          @RequestParam(value = "startTime") String startTimeStr,
                          @RequestParam(value = "endTime") String endTimeStr) {

        //????????????boss?????????id
        Long sysUserId = this.getUserId();
        //??????boss?????????app??????id???????????????
        List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);

        DataCountVO param = new DataCountVO();
        param.setUserId(userId);
        param.setUserMobile(userMobile);
        param.setLimit(limit);
        param.setOffset(offset);
        param.setUserLevel(userLevel);
        if (StringUtils.isNotBlank(startTimeStr)) {
            Date startTime = DateUtils.parseDateYMD(startTimeStr);
            param.setStartTime(startTime);
        }
        if (StringUtils.isNotBlank(endTimeStr)) {
            Date endTime = DateUtils.addDays(DateUtils.parseDateYMD(endTimeStr), 1);
            param.setEndTime(endTime);
        }
        //?????????????????????????????????appid
        Long saleId = appUserService.getIdByMobile(operateNo);
        param.setSaleId(saleId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(saleIds)) {
            param.setSaleIds(saleIds);
        }
        //?????????
        int total = summaryTableService.listSonCount(param);
        //????????????
        List<DataSaleVo> list = summaryTableService.listSon(param);
        return new PageUtils(list, total);
    }

    /**
     * ????????????
     */
    @GetMapping("/dataSale/read/{id}")
    public String read(@PathVariable("id") Long id, Model model) {
        //????????????
        UserTotalVo userTotalVo = new UserTotalVo();

        //????????????
        List<DataSaleVo> list = summaryTableService.listSonDetail(id);
        userTotalVo.setInvitedCount(list.size());
        userTotalVo.setVip1RegCount(list.stream().filter(o -> "1".equals(o.getLevel())).collect(Collectors.toList()).size());
        userTotalVo.setVip2RegCount(list.stream().filter(o -> "2".equals(o.getLevel())).collect(Collectors.toList()).size());
        userTotalVo.setVip3RegCount(list.stream().filter(o -> "3".equals(o.getLevel())).collect(Collectors.toList()).size());
        userTotalVo.setVip1RechargeCount(list.stream().filter(o -> ("1".equals(o.getLevel()) && o.getRechargeAmount().compareTo(BigDecimal.ZERO) > 0)).collect(Collectors.toList()).size());
        userTotalVo.setVip2RechargeCount(list.stream().filter(o -> ("2".equals(o.getLevel()) && o.getRechargeAmount().compareTo(BigDecimal.ZERO) > 0)).collect(Collectors.toList()).size());
        userTotalVo.setVip3RechargeCount(list.stream().filter(o -> ("3".equals(o.getLevel()) && o.getRechargeAmount().compareTo(BigDecimal.ZERO) > 0)).collect(Collectors.toList()).size());
        model.addAttribute("userTotal", userTotalVo);
        model.addAttribute("userDetailList", list);
        return "system/data/dataBossRead";
    }

    @ResponseBody
    @GetMapping("/dataSale/export")
    @RequiresPermissions("system:data:dataSaleExport")
    public void dataSaleExport(
            @RequestParam(value = "operateNo") String operateNo,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "userMobile") String userMobile,
            @RequestParam(value = "startTime") String startTimeStr,
            @RequestParam(value = "endTime") String endTimeStr,
            HttpServletResponse resp) {

        Long sysUserId = this.getUserId();
        List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);

        DataCountVO param = new DataCountVO();
        param.setUserId(userId);
        param.setUserMobile(userMobile);
        if (StringUtils.isNotBlank(startTimeStr)) {
            Date startTime = DateUtils.parseDateYMD(startTimeStr);
            param.setStartTime(startTime);
        }
        if (StringUtils.isNotBlank(endTimeStr)) {
            Date endTime = DateUtils.addDays(DateUtils.parseDateYMD(endTimeStr), 1);
            param.setEndTime(endTime);
        }
        Long saleId = appUserService.getIdByMobile(operateNo);
        param.setSaleId(saleId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(saleIds)) {
            param.setSaleIds(saleIds);
        }
        int total = summaryTableService.listSonCount(param);
        if (total == 0) {
            return;
        }
        List<DataSaleVo> list = new ArrayList<>(total);
        int limit = 200;
        int page = total / limit;
        int last = total % limit;
        page = (last == 0 ? page : page + 1);
        param.setLimit(limit);
        for (int i = 0; i < page; i++) {
            int start = i * limit;
            param.setOffset(start);
            List<DataSaleVo> resultList = summaryTableService.listSon(param);
            if (CollectionUtils.isNotEmpty(resultList)) {
                list.addAll(resultList);
            }
            logger.info("========??????======???" + i + "?????????================");
        }
        DecimalFormat df0 = new DecimalFormat("0");
        DecimalFormat df2 = new DecimalFormat("0.00");

        Set<Long> idSet = new HashSet<>(list.size());
        List<DataSaleExcelVo> dataSaleExcelVoList = new ArrayList<>(list.size());
        for (DataSaleVo o : list) {
            if (idSet.contains(o.getId())) {
                continue;
            }
            idSet.add(o.getId());
            DataSaleExcelVo dataSaleExcelVo = new DataSaleExcelVo();
            dataSaleExcelVo.setId(df0.format(o.getId()));
            dataSaleExcelVo.setSaleNo(o.getSaleNo());
            dataSaleExcelVo.setUserLevel(o.getUserLevel().toString());
            dataSaleExcelVo.setMobile(o.getMobile() + "\t");
            dataSaleExcelVo.setName(o.getName() + "\t");
            dataSaleExcelVo.setLevel(o.getLevel());
            dataSaleExcelVo.setTotalAssets(df2.format(o.getTotalAssets()));
            dataSaleExcelVo.setRechargeAmount(df2.format(o.getRechargeAmount()));
            dataSaleExcelVo.setWithdrawalAmount(df2.format(o.getWithdrawalAmount()));
            dataSaleExcelVo.setVaildCount(df0.format(o.getVaildCount()));
            dataSaleExcelVo.setTotalCount(df0.format(o.getTotalCount()));
            dataSaleExcelVo.setClickCount(df0.format(o.getClickCount()));
            dataSaleExcelVo.setClickCommission(df0.format(o.getClickCommission()));
            dataSaleExcelVo.setSuperiorId(df0.format(o.getSuperiorId()));
            dataSaleExcelVo.setRegDate(o.getRegDate() + "\t");
            dataSaleExcelVoList.add(dataSaleExcelVo);
        }
        ExcelExports.SimpleExcelModel<DataSaleExcelVo> excelModel = new ExcelExports.SimpleExcelModel<>();
        Date now = new Date();
        excelModel.setFileName("????????????-" + DateUtils.formatDateYMDChinese(now));
        excelModel.setRowList(dataSaleExcelVoList);
        excelModel.setExportFields(SaleDataExcelModel.fields);
        excelModel.setFieldHeaderMap(SaleDataExcelModel.headerNameMap);
        ExcelExports.webExportExcel(excelModel, resp);


    }


}
