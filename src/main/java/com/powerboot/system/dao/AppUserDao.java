package com.powerboot.system.dao;

import com.powerboot.system.domain.AppUserDO;

import com.powerboot.system.domain.BalanceDO;
import com.powerboot.system.dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 *
 */
@Mapper
public interface AppUserDao {

    Integer selectJustRegNoLoginCount(@Param("saleIdList") List<Long> saleIdList);

    List<Long> selectIdByMobile(@Param("mobileList") List<String> mobileList);

    int updateMoney(@Param("id") Long userId, @Param("amount") BigDecimal amount);

    int updateMoneyVip(@Param("id") Long userId, @Param("amount") BigDecimal amount, @Param("memberLevel") Integer memberLevel);

    AppUserDO get(Long id);

    AppUserDO getByMobile(String mobile);

    List<AppUserDO> getByMobiles(@Param("mobiles") List<String> mobiles);

    AppUserDO getByMobileAndRole(@Param("mobile")String mobile,@Param("role")Integer role);

    List<AppUserDO> list(Map<String, Object> map);

    List<AppUserDO> listTest(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(AppUserDO user);

    int update(AppUserDO user);

    int updateBatch(List<AppUserDO> user);

    int updateSaleIdByUserId(@Param("saleId") Long saleId,
        @Param("teamFlag") String teamFlag,
        @Param("userIdList") List<Long> userIdList);

    int remove(Long id);

    int batchRemove(Long[] ids);

    int updateWithdrawSwitch(AppUserDO user);

    int updateFirstRechargeById(@Param("id") Long id);

    int updateUserVIP(@Param("id") Long userId, @Param("memberLevel") Integer memberLevel);

    Integer getUserCount(@Param("role") Integer role, @Param("startDate") LocalDate startDate,
                         @Param("endDate") LocalDate endDate, @Param("saleIdList") List<Long> saleIdList,
                         @Param("userReferral") Integer userReferral, @Param("saleReferral") Integer saleReferral);

    Long getIdByMobile(@Param("mobile") String mobile);

    List<Long> getIdByParentId(@Param("parentId") Long parentId);

    List<UserDTO> getUserIdByParentId(@Param("parentIdList") List<Long> parentIdList);

    List<UserDTO> getByParentIdList(@Param("list") List<Long> idList);

    List<UserDTO> getByIdList(@Param("list") List<Long> idList);

    BigDecimal getAllAmount(@Param("saleIdList") List<Long> saleIdList);

    /**
     * 查询用户数量
     * @param params
     * @return
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 查询激活用户数量
     * @param params
     * @return
     */
    Integer selectActivateCount(Map<String, Object> params);
}
