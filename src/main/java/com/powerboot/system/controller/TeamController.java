package com.powerboot.system.controller;

import com.google.common.collect.Lists;
import com.powerboot.common.annotation.Log;
import com.powerboot.common.controller.BaseController;
import com.powerboot.common.utils.PageUtils;
import com.powerboot.common.utils.Query;
import com.powerboot.common.utils.R;
import com.powerboot.system.dao.UserRoleDao;
import com.powerboot.system.domain.RoleDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.domain.UserRoleDO;
import com.powerboot.system.dto.SysUserMappingDTO;
import com.powerboot.system.request.TeamLeaderUpdateRequest;
import com.powerboot.system.service.AppUserService;
import com.powerboot.system.service.RoleService;
import com.powerboot.system.service.SysUserMappingService;
import com.powerboot.system.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/team")
public class TeamController extends BaseController{

    @Autowired
    UserService userService;
    @Autowired
    SysUserMappingService sysUserMappingService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserRoleDao userRoleMapper;
    @Autowired
    private RoleService roleService;

    @GetMapping()
    @RequiresPermissions("system:team:list")
    String team() {
        return "system/team/team";
    }

    @GetMapping("/list")
    @RequiresPermissions("system:team:list")
    @ResponseBody
    PageUtils list(@RequestParam Map<String, Object> params) {
        // ??????????????????
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("teamFlag", 1);
        params.put("sort", "user_id");
        params.put("order", "asc");
        Query query = new Query(params);
        List<UserDO> sysUserList = userService.list(query);
        sysUserList.forEach(o -> {
            o.setTeamLeaderStr(o.getTeamLeader() == 0 ? "???" : "???");
        });
        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(sysUserList, total);
        return pageUtil;
    }

    @GetMapping("/setTeamLeaderEdit/{username}")
    @RequiresPermissions("system:team:list")
    String setTeamLeaderEdit(@PathVariable("username") String username, Model model) {
        model.addAttribute("username", username);
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<UserDO> sysUserList = userService.list(params);
        model.addAttribute("teamLeader", sysUserList.get(0).getTeamLeader());
        model.addAttribute("teamLeaderStyle", sysUserList.get(0).getTeamLeader()==0?"hidden":"");
        model.addAttribute("userId", sysUserList.get(0).getUserId());
        Map<String, Object> params2 = new HashMap<>();
        params2.put("teamFlag", 1);
        params2.put("sort", "user_id");
        params2.put("order", "asc");
        List<UserDO> allUser = userService.list(params2);
        model.addAttribute("allUser", allUser);
        return "system/team/setTeamLeaderEdit";
    }


    @Log
    @ResponseBody
    @RequestMapping("/teamLeaderEditUpdate")
    @RequiresPermissions("system:team:list")
    @Transactional(rollbackFor = Exception.class)
    public R teamLeaderEditUpdate(TeamLeaderUpdateRequest request) {
        //?????????????????????
        UserDO entity1 = new UserDO();
        entity1.setTeamLeader(request.getTeamLeader());
        UserDO param1 = new UserDO();
        param1.setUserId(request.getUserId());
        userService.updateByParam(entity1,param1);
        //??????????????????
        sysUserMappingService.deleteBySysUserId(request.getUserId());
        List<String> mobileList = new ArrayList<>();
        mobileList.add(request.getUserId().toString());
        //???????????????leader??????
        UserDO entity2 = new UserDO();
        entity2.setLeaderSysId(0);
        UserDO param2 = new UserDO();
        param2.setLeaderSysId(request.getUserId().intValue());
        userService.updateByParam(entity2,param2);
        List<RoleDO> roleDOList = roleService.list(request.getUserId());
        //??????????????????
        userRoleMapper.removeByUserId(request.getUserId());

        List<UserRoleDO> addUserRole = Lists.newArrayList();
        roleDOList.forEach(roleDO -> {
            if (!roleDO.getRoleId().equals(62L) && !roleDO.getRoleId().equals(63L) && roleDO.getRoleSign().equalsIgnoreCase("true")) {
                UserRoleDO userRole = new UserRoleDO();
                userRole.setRoleId(roleDO.getRoleId());
                userRole.setUserId(request.getUserId());
                addUserRole.add(userRole);
            }
        });
        //????????????????????????
        if(request.getTeamLeader()==1){
            if(CollectionUtils.isNotEmpty(request.getTeamUserIds())){
                request.getTeamUserIds().removeIf(o->o.equals(request.getUserId()));
                UserDO entity = new UserDO();
                entity.setLeaderSysId(request.getUserId().intValue());
                UserDO param = new UserDO();
                param.setUserIdList(request.getTeamUserIds());
                userService.updateByParam(entity,param);
                request.getTeamUserIds().forEach(o->{
                    mobileList.add(o.toString());
                });
            }
            //??????????????????
            UserRoleDO userRole = new UserRoleDO();
            userRole.setRoleId(63L);
            userRole.setUserId(request.getUserId());
            addUserRole.add(userRole);
        }else{
            //????????????????????????
            UserRoleDO userRole = new UserRoleDO();
            userRole.setRoleId(62L);
            userRole.setUserId(request.getUserId());
            addUserRole.add(userRole);
        }
        addUserRole.forEach(userRoleDO -> userRoleMapper.save(userRoleDO));
        List<Long> appUserIds = appUserService.selectIdByMobile(mobileList);
        appUserIds.forEach(appUserId->{
            SysUserMappingDTO dto = new SysUserMappingDTO();
            dto.setSysUserId(request.getUserId());
            dto.setUserId(appUserId);
            sysUserMappingService.save(dto);
        });
        return R.ok();
    }

}
