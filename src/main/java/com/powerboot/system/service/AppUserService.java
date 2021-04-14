package com.powerboot.system.service;

import com.powerboot.common.utils.StringUtils;
import com.powerboot.system.dao.AppUserDao;
import com.powerboot.system.domain.AppUserDO;
import com.powerboot.system.dto.UserDTO;
import com.powerboot.system.response.TaskResponse;
import com.powerboot.system.response.UserCountResp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserDao userDao;

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

    public int updateFirstRechargeById(Long id) {
        return userDao.updateFirstRechargeById(id);
    }

    public int updateUserVIP(Long userId, Integer memberLevel) {
        return userDao.updateUserVIP(userId, memberLevel);
    }


}
