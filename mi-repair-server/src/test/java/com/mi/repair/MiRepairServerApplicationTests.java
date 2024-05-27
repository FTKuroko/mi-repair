package com.mi.repair;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MiRepairServerApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void printAllBeanNames() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        System.out.println();
    }
}
