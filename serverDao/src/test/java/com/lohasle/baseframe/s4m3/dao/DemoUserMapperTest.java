package com.lohasle.baseframe.s4m3.dao;

import com.lohasle.baseframe.s4m3.dao.base.SpringTransactionalTestCase;
import com.lohasle.baseframe.s4m3.entities.DemoUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

public class DemoUserMapperTest extends SpringTransactionalTestCase {

    @Autowired
    private DemoUserMapper mapper;

    @Rollback(false) //写入数据库
    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i <1000 ; i++) {
            DemoUser demoUser = new DemoUser();
            demoUser.setName("hello"+i);
            demoUser.setCreateTime(new Date());
            mapper.insert(demoUser);
        }
    }
}