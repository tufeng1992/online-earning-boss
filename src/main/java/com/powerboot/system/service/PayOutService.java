package com.powerboot.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.powerboot.system.dao.PayOutDao;
import com.powerboot.system.domain.PayOutDO;

@Service
public class PayOutService {

    @Autowired
    private PayOutDao payOutDao;

    public PayOutDO get(Long id) {
        return payOutDao.get(id);
    }

    public List<PayOutDO> list(Map<String, Object> map) {
        return payOutDao.list(map);
    }

    public int count(Map<String, Object> map) {
        return payOutDao.count(map);
    }

    public int save(PayOutDO payOut) {
        return payOutDao.save(payOut);
    }

    public int update(PayOutDO payOut) {
        return payOutDao.update(payOut);
    }

    public int remove(Long id) {
        return payOutDao.remove(id);
    }

    public int batchRemove(Long[] ids) {
        return payOutDao.batchRemove(ids);
    }

}
