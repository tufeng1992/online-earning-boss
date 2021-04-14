package com.boss;

import com.powerboot.BossApplication;
import com.powerboot.system.domain.DataBossVo;
import com.powerboot.system.service.SummaryTableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BossApplication.class)
public class DataTest {
    @Autowired
    SummaryTableService summaryTableService;
    @Test
    public void dataTest(){
        List<DataBossVo> list = summaryTableService.list();
        list.forEach(System.out::println);
    }
}
