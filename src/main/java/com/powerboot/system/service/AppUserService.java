package com.powerboot.system.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.powerboot.common.config.Constant;
import com.powerboot.common.utils.StringUtils;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.dao.AppUserDao;
import com.powerboot.system.dao.OrderDao;
import com.powerboot.system.dao.PayDao;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.domain.DictDO;
import com.powerboot.system.domain.RoleDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.dto.UserDTO;
import com.powerboot.system.response.TaskResponse;
import com.powerboot.system.response.UserCountResp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.powerboot.utils.DateUtils;
import com.powerboot.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppUserService {

    @Autowired
    private AppUserDao userDao;

    @Autowired
    private DictService dictService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PayDao payDao;

    public TaskResponse getUserLoginRes(Integer userCount, List<Long> saleIdList) {
        Integer allNoLogin = userDao.selectJustRegNoLoginCount();
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setCount(allNoLogin);
        if (userCount == null || userCount == 0) {
            taskResponse.setCountRate("0.00");
        } else {
            taskResponse.setCountRate(
                new BigDecimal(allNoLogin).multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(userCount), 2, BigDecimal.ROUND_DOWN).toString());
        }
        return taskResponse;
    }

    public TaskResponse getNoSaleUser(Integer userCount, List<Long> saleIdList) {
        Map<String, Object> map = new HashMap<>();
        TaskResponse taskResponse = new TaskResponse();
        map.put("saleId",1);
        Integer noSaleCount = userDao.count(map);
        taskResponse.setCount(noSaleCount);
        if (userCount == null || userCount == 0) {
            taskResponse.setCountRate("0.00");
        } else {
            taskResponse.setCountRate(
                new BigDecimal(noSaleCount).multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(userCount), 2, BigDecimal.ROUND_DOWN).toString());
        }
        return taskResponse;
    }

    public UserCountResp getUserCount(List<Long> saleIdList) {
        UserCountResp resp = new UserCountResp();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
        resp.setUserCount(userDao.getUserCount(2, null, null, saleIdList, null, null));
        resp.setLocalCount(userDao.getUserCount(2, nowDate, nowDate.plusDays(1), saleIdList, null, null));
        resp.setYesterdayCount(userDao.getUserCount(2, yesterdayDate, nowDate, saleIdList, null, null));
        return resp;
    }

    /**
     * 获取用户激活数信息
     * @param resp
     */
    public void getUserActivateCount(UserCountResp resp, List<Long> saleIdList) {
        Map<String, Object> params = Maps.newHashMap();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
        params.put("role", 2);
        params.put("startDate", nowDate);
        params.put("endDate", nowDate.plusDays(1));
        params.put("saleIdList", saleIdList);
        //今日新增用户总数
        int total = userDao.getCount(params);
        params.put("firstTask", 0);
        //今日新增未激活用户总数
        int noActivateTotal = userDao.getCount(params);
        resp.setNoActivateTotal(noActivateTotal + "");
        if (0 == total || 0 == noActivateTotal) {
            resp.setActivateCountRate("0%");
        } else {
            resp.setActivateCountRate(new BigDecimal(noActivateTotal).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }

        Map<String, Object> params2 = Maps.newHashMap();
        params2.put("role", 2);
        params2.put("startDate", yesterdayDate);
        params2.put("endDate", nowDate);
        params2.put("saleIdList", saleIdList);
        //昨日新增用户总数
        int yesterdayTotal = userDao.getCount(params2);
        params2.put("firstTask", 0);
        //今日新增未激活用户总数
        int yesterdayNoActivateTotal = userDao.getCount(params2);
        resp.setYesterdayNoActivateTotal(yesterdayNoActivateTotal + "");
        if (0 == yesterdayNoActivateTotal || 0 == yesterdayTotal) {
            resp.setYesterdayActivateCountRate("0%");
        } else {
            resp.setYesterdayActivateCountRate(new BigDecimal(yesterdayNoActivateTotal).divide(new BigDecimal(yesterdayTotal), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }
    }

    /**
     * 获取用户归属客服数信息
     * @param resp
     */
    public void getUserContactCount(UserCountResp resp, List<Long> saleIdList) {
        Map<String, Object> params = Maps.newHashMap();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
        params.put("role", 2);
        params.put("startDate", nowDate);
        params.put("endDate", nowDate.plusDays(1));
        params.put("saleIdList", saleIdList);
        //今日新增用户总数
        int total = userDao.getCount(params);
        params.put("contactSaleId", 1);
        //今日新增无归属用户总数
        int noContactTotal = userDao.getCount(params);
        resp.setNoContactTotal(noContactTotal + "");
        if (0 == total || 0 == noContactTotal) {
            resp.setNoContactCountRate("0%");
        } else {
            resp.setNoContactCountRate(new BigDecimal(noContactTotal).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }

        Map<String, Object> params2 = Maps.newHashMap();
        params2.put("role", 2);
        params2.put("startDate", yesterdayDate);
        params2.put("endDate", nowDate);
        params2.put("saleIdList", saleIdList);
        //昨日新增用户总数
        int yesterdayTotal = userDao.getCount(params2);
        params2.put("contactSaleId", 1);
        //今日新增无归属用户总数
        int yesterdayNoContactTotal = userDao.getCount(params2);
        resp.setYesterdayNoContactTotal(yesterdayNoContactTotal + "");
        if (0 == yesterdayNoContactTotal || 0 == yesterdayTotal) {
            resp.setYesterdayNoContactCountRate("0%");
        } else {
            resp.setYesterdayNoContactCountRate(new BigDecimal(yesterdayNoContactTotal).divide(new BigDecimal(yesterdayTotal), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }
    }

    /**
     * 获取用户刷单数信息
     * @param resp
     */
    public void getUserTaskCount(UserCountResp resp, List<Long> saleIdList) {
        Map<String, Object> params = Maps.newHashMap();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
        params.put("role", 2);
        params.put("startDate", nowDate);
        params.put("endDate", nowDate.plusDays(1));
        params.put("saleIdList", saleIdList);
        //今日新增用户总数
        int total = userDao.getCount(params);
        params.put("contactSaleId", 1);
        //今日新增刷单用户总数
        Date now = new Date();
        Date todayStart = DateUtils.setDateHMS(now,0,0,0);
        Date todayEnd = DateUtils.setDateHMS(now,23,59,59);
        int taskTotal = orderDao.getCountGroupByUser(todayStart, todayEnd);
        resp.setTaskUserCount(taskTotal + "");
        if (0 == total || 0 == taskTotal) {
            resp.setTaskUserCountRate("0%");
        } else {
            resp.setTaskUserCountRate(new BigDecimal(taskTotal).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }

        Map<String, Object> params2 = Maps.newHashMap();
        params2.put("role", 2);
        params2.put("startDate", yesterdayDate);
        params2.put("endDate", nowDate);
        params2.put("saleIdList", saleIdList);
        //昨日新增用户总数
        int yesterdayTotal = userDao.getCount(params2);
        params2.put("contactSaleId", 1);
        //今日新增刷单用户总数
        Date yes = DateUtils.addDays(now,-1);
        Date yesStart = DateUtils.setDateHMS(yes,0,0,0);
        Date yesEnd = DateUtils.setDateHMS(yes,23,59,59);
        int yesterdayTaskTotal = orderDao.getCountGroupByUser(yesStart, yesEnd);
        resp.setYesterdayTaskUserCount(yesterdayTaskTotal + "");
        if (0 == yesterdayTaskTotal || 0 == yesterdayTotal) {
            resp.setYesterdayTaskUserCountRate("0%");
        } else {
            resp.setYesterdayTaskUserCountRate(new BigDecimal(yesterdayTaskTotal).divide(new BigDecimal(yesterdayTotal), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }
    }

    /**
     * 获取用户充值数信息
     * @param resp
     */
    public void getUserRechargeCount(UserCountResp resp, List<Long> saleIdList) {
        Map<String, Object> params = Maps.newHashMap();
        LocalDate nowDate = LocalDate.now();
        params.put("role", 2);
        params.put("startDate", nowDate);
        params.put("endDate", nowDate.plusDays(1));
        params.put("saleIdList", saleIdList);
        //今日新增用户总数
        int total = userDao.getCount(params);
        params.put("firstRecharge", 1);
        //今日新增充值用户总数
        int rechargeTotal = userDao.getCount(params);
        resp.setRechargeCount(rechargeTotal + "");
        if (0 == total || 0 == rechargeTotal) {
            resp.setRechargeRate("0%");
        } else {
            resp.setRechargeRate(new BigDecimal(rechargeTotal).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }

        Map<String, Object> params2 = Maps.newHashMap();

        params2.put("endDate", nowDate);
        //查询当日之前历史用户有过充值行为的人数
        int yesterdayTotal = payDao.getRechangeCount(params2);
        params2.put("startDate", nowDate);
        //查询当日以充值过的老用户发生再次充值人数
        int yesterdayNoContactTotal = payDao.getRegularUserCount(params2);
        resp.setTotalRechargeCount(yesterdayNoContactTotal + "");
        if (0 == yesterdayNoContactTotal || 0 == yesterdayTotal) {
            resp.setTotalRechargeRate("0%");
        } else {
            resp.setTotalRechargeRate(new BigDecimal(yesterdayNoContactTotal).divide(new BigDecimal(yesterdayTotal), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString() + "%");
        }
    }

    public UserCountResp getUserReferral(List<Long> saleIdList) {
        UserCountResp resp = new UserCountResp();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
        resp.setUserCount(userDao.getUserCount(2, null, null, saleIdList, 1, null));
        resp.setLocalCount(userDao.getUserCount(2, nowDate, nowDate.plusDays(1), saleIdList, 1, null));
        resp.setYesterdayCount(userDao.getUserCount(2, yesterdayDate, nowDate, saleIdList, 1, null));
        return resp;
    }

    public UserCountResp getSaleReferral(List<Long> saleIdList) {
        UserCountResp resp = new UserCountResp();
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = LocalDate.now().plusDays(-1);
        resp.setUserCount(userDao.getUserCount(2, null, null, saleIdList, null, 1));
        resp.setLocalCount(userDao.getUserCount(2, nowDate, nowDate.plusDays(1), saleIdList, null, 1));
        resp.setYesterdayCount(userDao.getUserCount(2, yesterdayDate, nowDate, saleIdList, null, 1));
        return resp;
    }

    public Long getIdByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        return userDao.getIdByMobile(mobile);
    }

    public List<Long> getIdByParentId(Long parentId) {
        if (null == parentId) {
            return null;
        }
        return userDao.getIdByParentId(parentId);
    }

    public List<UserDTO> getByParentIdList(List<Long> idList) {
        return userDao.getByParentIdList(idList);
    }

    public List<UserDTO> getByIdList(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        return userDao.getByIdList(idList);
    }

    public BigDecimal getAllAmount(List<Long> saleIdList) {
        BigDecimal amount = userDao.getAllAmount(saleIdList);
        return amount == null ? BigDecimal.ZERO : amount;
    }

    public List<Long> selectIdByMobile(List<String> mobileList) {
        return userDao.selectIdByMobile(mobileList);
    }

    public List<UserDTO> getUserIdByParentId(List<Long> parentIdList) {
        return userDao.getUserIdByParentId(parentIdList);
    }

    public AppUserDO getByMobile(String mobile) {
        return userDao.getByMobile(mobile);
    }

    public List<AppUserDO> getByMobiles(List<String> mobiles) {
        return userDao.getByMobiles(mobiles);
    }

    public AppUserDO getByMobileAndRole(String mobile, Integer role) {
        return userDao.getByMobileAndRole(mobile, role);
    }

    public AppUserDO get(Long id) {
        return userDao.get(id);
    }

    public int updateSwitch(AppUserDO user) {
        return userDao.updateWithdrawSwitch(user);
    }

    public List<AppUserDO> list(Map<String, Object> map) {
        return userDao.list(map);
    }

    public int count(Map<String, Object> map) {
        return userDao.count(map);
    }

    public int save(AppUserDO user) {
        return userDao.save(user);
    }

    public int update(AppUserDO user) {
        return userDao.update(user);
    }

    public int updateSaleIdByUserId(Long saleId, String teamFlag, List<Long> userIdList) {
        return userDao.updateSaleIdByUserId(saleId, teamFlag, userIdList);
    }

    public int remove(Long id) {
        return userDao.remove(id);
    }

    public int batchRemove(Long[] ids) {
        return userDao.batchRemove(ids);
    }

    /**
     * 批量修改运营人员所属
     * @param ids
     * @param saleId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int batchMove(Long[] ids, Long saleId) {
        int res = 0;
        for (Long id : ids) {
            AppUserDO appUserDO = new AppUserDO();
            appUserDO.setId(id);
            appUserDO.setSaleId(saleId);
            res += userDao.update(appUserDO);
        }
        return res;
    }

    public int updateFirstRechargeById(Long id) {
        return userDao.updateFirstRechargeById(id);
    }

    public int updateUserVIP(Long userId, Integer memberLevel) {
        return userDao.updateUserVIP(userId, memberLevel);
    }

    /**
     * 获取根据用户parentId授权关联id
     * @param userDO
     * @return
     */
    public Set<Long> getAuthByParentId(UserDO userDO) {
        DictDO dictDO = dictService.getByKey(DictConsts.FXKF_ROLE_ID);
        String s = dictDO.getValue();
        String[] roleIds = s.split("\\|");
        Set<Long> ids = Sets.newHashSet();
        for (String roleId : roleIds) {
            List<RoleDO> roleDOList = roleService.list(userDO.getUserId());
            if (CollectionUtils.isNotEmpty(roleDOList)) {
                for (RoleDO roleDO : roleDOList) {
                    if (roleDO.getRoleId().equals(Long.valueOf(roleId)) && roleDO.getRoleSign().equalsIgnoreCase("true")) {
                        ids.addAll(queryParentLinkIds(userDO.getAppUserId()));
                        if (CollectionUtils.isEmpty(ids)) {
                            ids.add(-1L);
                        }
                        return ids;
                    }
                }
            }
        }
        return ids;
    }


    /**
     * 查询关联的用户id
     * @param appUserId
     * @return
     */
    private List<Long> queryParentLinkIds(Long appUserId) {
        List<Long> ids = Lists.newArrayList();
        doQueryParentId(appUserId, ids);
        return ids;
    }

    /**
     * 查询关联的用户id
     * @param appUserId
     * @param ids
     */
    private void doQueryParentId(Long appUserId, List<Long> ids) {
        List<Long> id = getIdByParentId(appUserId);
        if (CollectionUtils.isNotEmpty(id)) {
            id.forEach(i -> {
                ids.add(i);
                doQueryParentId(i, ids);
            });
        }
    }

    /**
     * 查询用户关联营销人员信息
     * @param userId
     * @return
     */
    public AppUserDO getSaleInfo(Long userId) {
        AppUserDO appUserDO = get(userId);
        if (null == appUserDO || null == appUserDO.getSaleId() || 1L == appUserDO.getSaleId()) {
            return null;
        }
        AppUserDO saleUserDO = get(appUserDO.getSaleId());
        if (null == saleUserDO || 2 == saleUserDO.getRole()) {
            return null;
        }
        return saleUserDO;
    }

    /**
     * 批量标记联系人
     * @param ids
     * @param saleId
     */
    @Transactional(rollbackFor = Exception.class)
    public int batchContact(Long[] ids, Long saleId) {
        int res = 0;
        for (Long id : ids) {
            AppUserDO appUserDO = new AppUserDO();
            appUserDO.setId(id);
            appUserDO.setContactSaleId(saleId);
            res += userDao.update(appUserDO);
        }
        return res;
    }
}
