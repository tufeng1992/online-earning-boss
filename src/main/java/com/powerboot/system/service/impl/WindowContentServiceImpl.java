package com.powerboot.system.service.impl;

import com.powerboot.common.utils.JsonUtils;
import com.powerboot.system.consts.DictConsts;
import com.powerboot.system.dao.WindowContentDao;
import com.powerboot.system.domain.WindowContentDO;
import com.powerboot.system.service.WindowContentService;
import com.powerboot.utils.RedisUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WindowContentServiceImpl implements WindowContentService {

    @Autowired
    private WindowContentDao windowContentDao;

    @Override
    public WindowContentDO get(Integer id) {
        return windowContentDao.get(id);
    }

    @Override
    public List<WindowContentDO> list(Map<String, Object> map) {
        return windowContentDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return windowContentDao.count(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(WindowContentDO windowContent) {
        int count = windowContentDao.save(windowContent);
        if (count > 0) {
            refreshCache();
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(WindowContentDO windowContent) {
        int count = windowContentDao.update(windowContent);
        if (count > 0) {
            refreshCache();
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer id) {
        int count = windowContentDao.remove(id);
        if (count > 0) {
            refreshCache();
        }
        return count;
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return windowContentDao.batchRemove(ids);
    }

    public void refreshCache() {
        String windowContentKey = "windowContent";
        List<WindowContentDO> list = windowContentDao.getAll();
        RedisUtils.setValue(windowContentKey, JsonUtils.toJSONString(list), DictConsts.DICT_CACHE_LIVE_TIME);
    }

}
