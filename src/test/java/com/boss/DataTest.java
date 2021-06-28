//package com.boss;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.powerboot.BossApplication;
//import com.powerboot.system.dao.AppUserDao;
//import com.powerboot.system.dao.BalanceDao;
//import com.powerboot.system.dao.FinancialOrderDao;
//import com.powerboot.system.dao.PayDao;
//import com.powerboot.system.domain.AppUserDO;
//import com.powerboot.system.domain.BalanceDO;
//import com.powerboot.system.domain.FinancialOrderDO;
//import com.powerboot.system.domain.PayDO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BossApplication.class)
//public class DataTest {
////    @Autowired
////    SummaryTableService summaryTableService;
////    @Test
////    public void dataTest(){
////        List<DataBossVo> list = summaryTableService.list();
////        list.forEach(System.out::println);
////    }
//    @Autowired
//    private AppUserDao appUserDao;
//
//    @Autowired
//    private PayDao payDao;
//
//    @Autowired
//    private FinancialOrderDao financialOrderDao;
//
//    @Autowired
//    private BalanceDao balanceDao;
//
//    @Test
//    public void test01() {
//        List<AppUserDO> appUserDOList = appUserDao.listTest(Maps.newHashMap());
//        Map<Long, AppUserDO> map = Maps.newHashMap();
//        appUserDOList.forEach(appUserDO -> map.put(appUserDO.getId(), appUserDO));
//        AtomicInteger res = new AtomicInteger();
//        List<AppUserDO> updateBatch = Lists.newArrayList();
//        appUserDOList.forEach(appUserDO -> {
//            Long saleId = findTopParent(appUserDO.getParentId(), map);
//            AppUserDO update = new AppUserDO();
//            update.setId(appUserDO.getId());
//            update.setSaleId(saleId);
//            updateBatch.add(update);
//            if (updateBatch.size() > 5000) {
//                res.addAndGet(appUserDao.updateBatch(updateBatch));
//                updateBatch.clear();
//            }
//        });
//        if (updateBatch.size() > 0) {
//            res.addAndGet(appUserDao.updateBatch(updateBatch));
//        }
//        System.out.println(res.get());
//    }
//
//    @Test
//    public void test02() {
//        List<PayDO> payDOList = payDao.listTest(Maps.newHashMap());
//
//        Map<Long, AppUserDO> map = Maps.newHashMap();
//        List<PayDO> updateBatch = Lists.newArrayList();
//        payDOList.forEach(payDO -> {
//            PayDO update = new PayDO();
//            AppUserDO appUserDO = map.get(payDO.getUserId());
//            if (null == appUserDO) {
//                appUserDO = appUserDao.get(payDO.getUserId());
//            }
//            if (null == appUserDO) {
//                return;
//            }
//            map.put(payDO.getUserId(), appUserDO);
//            update.setSaleId(appUserDO.getSaleId());
//            update.setId(payDO.getId());
//            updateBatch.add(update);
//            if (updateBatch.size() > 5000) {
//                payDao.updateBatch(updateBatch);
//                updateBatch.clear();
//            }
//        });
//        if (updateBatch.size() > 0) {
//            payDao.updateBatch(updateBatch);
//        }
//        System.out.println("end");
//    }
//
//    @Test
//    public void test03() {
//        List<BalanceDO> payDOList = balanceDao.listTest(Maps.newHashMap());
//        Map<Long, AppUserDO> map = Maps.newHashMap();
//        List<AppUserDO> list = appUserDao.listTest(Maps.newHashMap());
//        list.forEach(appUserDO -> map.put(appUserDO.getId(), appUserDO));
//        List<BalanceDO> updateBatch = Lists.newArrayList();
//        payDOList.forEach(payDO -> {
//            BalanceDO update = new BalanceDO();
//            AppUserDO appUserDO = map.get(payDO.getUserId());
//            if (null == appUserDO) {
//                appUserDO = appUserDao.get(payDO.getUserId());
//            }
//            if (null == appUserDO) {
//                return;
//            }
//            map.put(payDO.getUserId(), appUserDO);
//            update.setSaleId(appUserDO.getSaleId());
//            update.setId(payDO.getId());
//            updateBatch.add(update);
//            if (updateBatch.size() > 5000) {
//                balanceDao.updateBatch(updateBatch);
//                updateBatch.clear();
//            }
//        });
//        if (updateBatch.size() > 0) {
//            balanceDao.updateBatch(updateBatch);
//        }
//        System.out.println("end");
//    }
//
//    private Long findTopParent(Long parentId, Map<Long, AppUserDO> map) {
//        AppUserDO appUserDO = map.get(parentId);
//        if (null == appUserDO) {
//            appUserDO = appUserDao.get(parentId);
//        }
//        if (null == appUserDO || appUserDO.getParentId() == null) {
//            return parentId;
//        }
//        map.put(appUserDO.getId(), appUserDO);
//        return findTopParent(appUserDO.getParentId(), map);
//    }
//}
