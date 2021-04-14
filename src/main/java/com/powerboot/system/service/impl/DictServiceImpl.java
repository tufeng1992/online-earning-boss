package com.powerboot.system.service.impl;

import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.dao.DictDao;
import com.powerboot.system.domain.DictDO;
import com.powerboot.system.service.DictService;
import com.powerboot.utils.RedisUtils;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DictServiceImpl implements DictService {

    private static Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

    @Autowired
    private DictDao dictDao;

    @Override
    public DictDO get(Long id) {
        return dictDao.get(id);
    }

    @Override
    public DictDO getByKey(String key) {
        return dictDao.getByKey(key);
    }

    @Override
    public List<DictDO> list(Map<String, Object> map) {
        return dictDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dictDao.count(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(DictDO dict) {
        int count = dictDao.save(dict);
        if (count > 0) {
            RedisUtils.setValue(dict.getKey(), dict.getValue(), DictConsts.DICT_CACHE_LIVE_TIME);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DictDO dict) {
        int count = dictDao.update(dict);
        if (count > 0) {
            RedisUtils.setValue(dict.getKey(), dict.getValue(), DictConsts.DICT_CACHE_LIVE_TIME);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByKey(String key, String value) {
        int count = dictDao.updateByKey(key, value);
        if (count > 0) {
            RedisUtils.setValue(key, value, DictConsts.DICT_CACHE_LIVE_TIME);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Long id) {
        DictDO dict = get(id);
        if (dict == null) {
            return 0;
        }
        int count = dictDao.remove(id);
        if (count > 0) {
            RedisUtils.remove(dict.getKey());
        }
        return count;
    }

    @Override
    public int batchRemove(Long[] ids) {
        return dictDao.batchRemove(ids);
    }

}
