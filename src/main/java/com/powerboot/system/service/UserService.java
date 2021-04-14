package com.powerboot.system.service;

import com.powerboot.common.domain.Tree;
import com.powerboot.system.domain.DeptDO;
import com.powerboot.system.domain.UserDO;
import com.powerboot.system.vo.UserVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
	UserDO get(Long id);

	List<UserDO> getByLeader(Long userId);

	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserDO user);

	int updateUser(UserDO user);

	int updateByParam(UserDO entity,UserDO param);

	int update(UserDO user);

	int remove(Long userId);

	int batchremove(Long[] userIds);

	boolean exit(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	int resetPwd(UserVO userVO,UserDO userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<DeptDO> getTree();

	int updatePersonal(UserDO userDO);

    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;

	BigDecimal getUserRechargeAmount(Long id);

}
