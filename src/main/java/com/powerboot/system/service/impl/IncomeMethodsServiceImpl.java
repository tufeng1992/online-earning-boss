package com.powerboot.system.service.impl;

import com.powerboot.common.utils.JsonUtils;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.dao.IncomeMethodsDao;
import com.powerboot.system.domain.IncomeMethodsDO;
import com.powerboot.system.service.IncomeMethodsService;
import com.powerboot.utils.RedisUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncomeMethodsServiceImpl implements IncomeMethodsService {

    @Autowired
    private IncomeMethodsDao incomeMethodsDao;

    @Override
    public IncomeMethodsDO get(Integer id) {
        return incomeMethodsDao.get(id);
    }

    @Override
    public List<IncomeMethodsDO> list(Map<String, Object> map) {
        return incomeMethodsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return incomeMethodsDao.count(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(IncomeMethodsDO incomeMethods) {
        int count = incomeMethodsDao.save(incomeMethods);
        if (count > 0) {
            refreshCache();
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IncomeMethodsDO incomeMethods) {
        int count = incomeMethodsDao.update(incomeMethods);
        if (count > 0) {
            refreshCache();
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer id) {
        int count = incomeMethodsDao.remove(id);
        if (count > 0) {
            refreshCache();
        }
        return count;
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return incomeMethodsDao.batchRemove(ids);
    }

    public void refreshCache() {
        String incomeMethodsKey = "IncomeMethods";
        List<IncomeMethodsDO> list = incomeMethodsDao.getAll();
        RedisUtils.setValue(incomeMethodsKey, JsonUtils.toJSONString(list), DictConsts.DICT_CACHE_LIVE_TIME);
    }

}
