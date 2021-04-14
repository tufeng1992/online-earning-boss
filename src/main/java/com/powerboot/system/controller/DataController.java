package com.powerboot.system.controller;

import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.JsonUtils;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.R;
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
     * 数据总表
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
        List<DataBossVo> list = summaryTableService.list();
        list.forEach(o -> {
            o.setNetProfit(o.getRechargeAmount().add(o.getVipPayAmount()).subtract(o.getWithdrawAmount()));
            o.setShowRed(o.getNetProfit().compareTo(BigDecimal.ZERO) > 0 ? 1 : 0);
        });
        PageUtils pageUtils = new PageUtils(list, list.size());
        return pageUtils;
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
        logger.info("数据总表修改，id={},old={},new={}",
                dataBossVo.getId(), JsonUtils.toJSONString(oldDataBossVo), JsonUtils.toJSONString(dataBossVo));
        return R.ok();
    }


    @GetMapping("/dataSale")
    @RequiresPermissions("system:data:dataSale")
    String dataSale(Model model) {
        return "system/data/dataSale";
    }

    /**
     * 数据分表
     * offset 开始索引
     * limit 步长
     * operateNo 运营号码
     * startTime 搜索起始时间
     * endTime 搜索结束时间
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

        //当前登录boss的账号id
        Long sysUserId = this.getUserId();
        //查找boss账号和app账号id的映射关系
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
        //根据运营手机号查询运营appid
        Long saleId = appUserService.getIdByMobile(operateNo);
        param.setSaleId(saleId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(saleIds)) {
            param.setSaleIds(saleIds);
        }
        //总条数
        int total = summaryTableService.listSonCount(param);
        //数据集合
        List<DataSaleVo> list = summaryTableService.listSon(param);
        return new PageUtils(list, total);
    }

    /**
     * 分表详情
     */
    @GetMapping("/dataSale/read/{id}")
    public String read(@PathVariable("id") Long id, Model model) {
        //总计信息
        UserTotalVo userTotalVo = new UserTotalVo();

        //详情信息
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
            logger.info("========导出======第" + i + "个循环================");
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
        excelModel.setFileName("数据分表-" + DateUtils.formatDateYMDChinese(now));
        excelModel.setRowList(dataSaleExcelVoList);
        excelModel.setExportFields(SaleDataExcelModel.fields);
        excelModel.setFieldHeaderMap(SaleDataExcelModel.headerNameMap);
        ExcelExports.webExportExcel(excelModel, resp);


    }


}
