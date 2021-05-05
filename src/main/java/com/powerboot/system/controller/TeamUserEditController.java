package com.powerboot.system.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.consts.UserRoleEnum;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.response.AppUserResponse;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.UserService;
import com.powerboot.utils.RedisUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/teamUserEdit")
public class TeamUserEditController extends BaseController {

    @Autowired
    SysUserMappingService sysUserMappingService;

    @Autowired
    private AppUserService userService;

    @Autowired
    private UserService sysUserService;

    @GetMapping()
    @RequiresPermissions("system:teamUserEdit:teamUserEdit")
    String User(Model model) {

        return "system/team/teamUserEditList";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:teamUserEdit:teamUserEdit")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Long sysUserId = this.getUserId();
        List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(saleIds)) {
            return new PageUtils(null, 0);
        }
        params.put("saleIdList", saleIds);
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
            resultList.add(appUserResponse);
        });
        PageUtils pageUtils = new PageUtils(resultList, total);
        return pageUtils;
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:teamUserEdit:teamUserEdit")
    String edit(@PathVariable("id") Long id, Model model) {
        AppUserDO user = userService.get(id);
        model.addAttribute("user", user);
        return "system/team/teamUserEdit";
    }

    @Log
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("system:teamUserEdit:teamUserEdit")
    public R update(AppUserDO user) {
        AppUserDO appUserDO = userService.get(user.getId());
        Long sysUserId = this.getUserId();
        List<SysUserMappingDTO> userMappingDTOS = sysUserMappingService.getBySysUserId(sysUserId);
        List<Long> saleIds = userMappingDTOS.stream().map(SysUserMappingDTO::getUserId).collect(Collectors.toList());
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
        return R.ok();
    }

}
