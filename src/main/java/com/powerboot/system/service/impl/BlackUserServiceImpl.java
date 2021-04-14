package com.powerboot.system.service.impl;

import com.powerboot.system.consts.DictConsts;
import com.powerboot.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.BlackUserDao;
import com.powerboot.system.domain.BlackUserDO;
import com.powerboot.system.service.BlackUserService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlackUserServiceImpl implements BlackUserService {
    private static Logger logger = LoggerFactory.getLogger(BlackUserServiceImpl.class);
    private static final String BLACK_USER_NAME = "BLACK_USER:NAME:%s";
    private static final String BLACK_USER_MOBILE = "BLACK_USER:MOBILE:%s";
    private static final String BLACK_USER_EMAIL = "BLACK_USER:EMAIL:%s";
    private static final String BLACK_USER_IFSC = "BLACK_USER:IFSC:%s";

    @Autowired
    private BlackUserDao blackUserDao;

    @Override
    public BlackUserDO get(Long id) {
        return blackUserDao.get(id);
    }

    @Override
    public List<BlackUserDO> list(Map<String, Object> map) {
        return blackUserDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return blackUserDao.count(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(BlackUserDO blackUser) {
        trimBlackUserDO(blackUser);
        int count = blackUserDao.save(blackUser);
        if (count > 0) {
            refreshCache(blackUser);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BlackUserDO blackUser) {
        trimBlackUserDO(blackUser);
        BlackUserDO old = get(blackUser.getId());
        int count = blackUserDao.update(blackUser);
        if (count > 0) {
            deleteBlack(old);
            refreshCache(blackUser);
        }
        return count;
    }

    private void refreshCache(BlackUserDO blackUser) {
        if (StringUtils.isNotBlank(blackUser.getName())) {
            String key = String.format(BLACK_USER_NAME, blackUser.getName());
            RedisUtils.setValue(key, "1", DictConsts.DICT_CACHE_LIVE_TIME);
        }
        if (StringUtils.isNotBlank(blackUser.getMobile())) {
            String key = String.format(BLACK_USER_MOBILE, blackUser.getMobile());
            RedisUtils.setValue(key, "1", DictConsts.DICT_CACHE_LIVE_TIME);
        }
        if (StringUtils.isNotBlank(blackUser.getEmail())) {
            String key = String.format(BLACK_USER_EMAIL, blackUser.getEmail());
            RedisUtils.setValue(key, "1", DictConsts.DICT_CACHE_LIVE_TIME);
        }
        if (StringUtils.isNotBlank(blackUser.getIfsc())) {
            String key = String.format(BLACK_USER_IFSC, blackUser.getIfsc());
            RedisUtils.setValue(key, "1", DictConsts.DICT_CACHE_LIVE_TIME);
        }
    }

    private void deleteBlack(BlackUserDO blackUser) {
        if (StringUtils.isNotBlank(blackUser.getName())) {
            String key = String.format(BLACK_USER_NAME, blackUser.getName());
            RedisUtils.remove(key);
        }
        if (StringUtils.isNotBlank(blackUser.getMobile())) {
            String key = String.format(BLACK_USER_MOBILE, blackUser.getMobile());
            RedisUtils.remove(key);
        }
        if (StringUtils.isNotBlank(blackUser.getEmail())) {
            String key = String.format(BLACK_USER_EMAIL, blackUser.getEmail());
            RedisUtils.remove(key);
        }
        if (StringUtils.isNotBlank(blackUser.getIfsc())) {
            String key = String.format(BLACK_USER_IFSC, blackUser.getIfsc());
            RedisUtils.remove(key);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Long id) {
        BlackUserDO blackUser = get(id);
        int count = blackUserDao.remove(id);
        if (count > 0) {
            deleteBlack(blackUser);
        }
        return count;
    }

    private void trimBlackUserDO(BlackUserDO blackUser) {
        if (StringUtils.isNotBlank(blackUser.getName())) {
            blackUser.setName(blackUser.getName().trim());
        }
        if (StringUtils.isNotBlank(blackUser.getMobile())) {
            blackUser.setMobile(blackUser.getMobile().trim());
        }
        if (StringUtils.isNotBlank(blackUser.getEmail())) {
            blackUser.setEmail(blackUser.getEmail().trim());
        }
        if (StringUtils.isNotBlank(blackUser.getIfsc())) {
            blackUser.setIfsc(blackUser.getIfsc().trim());
        }
    }

    @Override
    public int batchRemove(Long[] ids) {
        return blackUserDao.batchRemove(ids);
    }

}
