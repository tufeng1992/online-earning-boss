package com.powerboot.system.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.consts.BalanceTypeEnum;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.consts.StatusTypeEnum;
import com.powerboot.system.consts.UserRoleEnum;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.BalanceDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.dto.UserDTO;
import com.powerboot.system.response.AppUserResponse;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.BalanceService;
import com.powerboot.system.service.FinancialOrderService;
import com.powerboot.system.service.PayService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.UserService;
import com.powerboot.utils.RedisUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
 * 用户表
 */

@Controller
@RequestMapping("/system/userEdit")
public class UserEditController extends BaseController {

    @Autowired
    SysUserMappingService sysUserMappingService;
    @Autowired
    private AppUserService userService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserService sysUserService;
    @Autowired
    private PayService payService;
    @Autowired
    private FinancialOrderService financialOrderService;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @GetMapping()
    @RequiresPermissions("system:userEdit:userEdit")
    String User(Model model) {
        List<UserDO> userDOList = sysUserService.list(Maps.newHashMap());
        List<String> mobiles = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(userDOList)) {
            userDOList.forEach(userDO -> mobiles.add(userDO.getUserId().toString()));
        }
        List<AppUserDO> appUserDOList = userService.getByMobiles(mobiles);
        model.addAttribute("appUserDOList", appUserDOList);
        return "system/userEdit/userEdit";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:userEdit:userEdit")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Long sysUserId = this.getUserId();
        UserDO sysUser = sysUserService.get(sysUserId);
        if (sysUser.getTeamFlag() == 1) {
            List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
            List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId)
                .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(saleIds)) {
                return new PageUtils(Collections.emptyList(), 0);
            }
            params.put("saleIdList", saleIds);
        }
        Query query = new Query(params);
        List<AppUserDO> userList = userService.list(query);
        int total = userService.count(query);
        List<AppUserResponse> resultList = new ArrayList<>();
        userList.forEach(user -> {
            AppUserResponse appUserResponse = new AppUserResponse();
            BeanUtils.copyProperties(user, appUserResponse);
            appUserResponse.setRoleName(UserRoleEnum.getMsgByCode(appUserResponse.getRole()));
            appUserResponse.setBindStatusStr(appUserResponse.getBindStatus().equals(0) ? "未绑定" : "已绑定");
            appUserResponse.setBlackFlagStr(appUserResponse.getBlackFlag().equals(0) ? "否" : "是");
            appUserResponse.setFirstRechargeStr(appUserResponse.getFirstRecharge().equals(0) ? "未完成" : "已完成");
            AppUserDO appUserDO = userService.getSaleInfo(user.getId());
            if (null != appUserDO) {
                appUserResponse.setSaleMobile(appUserDO.getMobile());
            }
            resultList.add(appUserResponse);
        });
        PageUtils pageUtils = new PageUtils(resultList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("system:userEdit:add")
    String add() {
        return "system/userEdit/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:userEdit:userEdit")
    String edit(@PathVariable("id") Long id, Model model) {
        AppUserDO user = userService.get(id);
        model.addAttribute("user", user);
        AppUserDO sale = userService.get(user.getSaleId());
        model.addAttribute("saleMobile", sale.getMobile());
        return "system/userEdit/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("system:userEdit:add")
    public R save(AppUserDO user) {
        if (userService.save(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @Log
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:userEdit:userEdit")
    public R update(AppUserDO user) {
        AppUserDO appUserDO = userService.get(user.getId());
        BalanceDO balance = new BalanceDO();
        balance.setUserId(user.getId());
        balance.setStatus(StatusTypeEnum.SUCCESS.getCode());
        //减少余额
        if (appUserDO.getBalance().compareTo(user.getBalance()) >= 0) {
            balance.setType(BalanceTypeEnum.L.getCode());
            balance.setAmount(appUserDO.getBalance().subtract(user.getBalance()).multiply(new BigDecimal(-1)));
        } else {
            //增加余额
            balance.setType(BalanceTypeEnum.K.getCode());
            balance.setAmount(user.getBalance().subtract(appUserDO.getBalance()));
        }
        //更新开关
        if (!user.getWithdrawCheck().equals(appUserDO.getWithdrawCheck())
            || !user.getLxSwitch().equals(appUserDO.getLxSwitch())
            || !user.getSdSwitch().equals(appUserDO.getSdSwitch())
            || !user.getRechargeCheck().equals(appUserDO.getRechargeCheck())
            || !user.getLoginFlag().equals(appUserDO.getLoginFlag())
            || !user.getShareFlag().equals(appUserDO.getShareFlag())
            || !user.getBlackFlag().equals(appUserDO.getBlackFlag())
        ) {
            userService.updateSwitch(user);
            String loginKey = String.format(DictConsts.USER_LOGIN_FLAG, appUserDO.getMobile());
            if (user.getLoginFlag() == 0) {
                RedisUtils.setValue(loginKey, "1", DictConsts.DICT_CACHE_LIVE_TIME);
            } else {
                RedisUtils.remove(loginKey);
            }
            String blackIdKey = String.format(DictConsts.BLACK_USER_ID, user.getId());
            if (user.getBlackFlag() == 1) {
                RedisUtils.setValue(blackIdKey, "1", DictConsts.DICT_CACHE_LIVE_TIME);
            } else {
                RedisUtils.remove(blackIdKey);
            }
        }

        balanceService.updateMoneyVip(balance, user.getMemberLevel());

        if (StringUtils.isNotBlank(user.getSaleMobile())) {
            AppUserDO oldSaleUser = userService.get(appUserDO.getSaleId());
            if (!user.getSaleMobile().equals(oldSaleUser.getMobile())) {
                AppUserDO newSaleUser = userService.getByMobileAndRole(user.getSaleMobile(), 1);
                if (newSaleUser == null) {
                    return R.error(user.getSaleMobile() + "客服不存在，请确认重新提交");
                }
                executorService.execute(() -> {
                    Long parentId = user.getId();
                    //查询子账号
                    List<Long> allUserIdList = new ArrayList<>();
                    allUserIdList.add(parentId);
                    List<UserDTO> allSonUserList = new ArrayList<>();
                    this.getAllSonUserId(Collections.singletonList(parentId), allSonUserList);
                    boolean isExistSon = CollectionUtils.isNotEmpty(allSonUserList);
                    if (isExistSon) {
                        allUserIdList.addAll(allSonUserList.stream().map(UserDTO::getId).collect(Collectors.toList()));
                    }
                    Long newSaleId = newSaleUser.getId();
                    String newTeamLlag = newSaleUser.getTeamFlag();
                    userService.updateSaleIdByUserId(newSaleId, newTeamLlag, allUserIdList);
                    payService.updateSaleIdByUserId(newSaleId, allUserIdList);
                    balanceService.updateSaleIdByUserId(newSaleId, allUserIdList);
                    financialOrderService.updateSaleIdByUserId(newSaleId, allUserIdList);
                });
            }
        }
        return R.ok();
    }

    private List<UserDTO> getAllSonUserId(List<Long> parentId, List<UserDTO> allSonUserList) {
        List<UserDTO> list = userService.getUserIdByParentId(parentId);
        if (CollectionUtils.isEmpty(list)) {
            return allSonUserList;
        }
        allSonUserList.addAll(list);
        return this.getAllSonUserId(list.stream().map(UserDTO::getId).collect(Collectors.toList()), allSonUserList);
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("system:userEdit:remove")
    public R remove(Long id) {
        if (userService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("system:userEdit:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        userService.batchRemove(ids);
        return R.ok();
    }

}
