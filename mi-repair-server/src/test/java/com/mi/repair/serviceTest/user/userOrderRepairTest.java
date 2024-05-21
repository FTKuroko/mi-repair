package com.mi.repair.serviceTest.user;

import com.mi.repair.dto.UserLoginDTO;
import com.mi.repair.vo.UserLoginVO;
import com.mi.repair.dto.OrderRepairSubmitDTO;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.service.UserService;
import com.mi.repair.vo.OrderRepairSubmitVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/15 14:33
 */
@SpringBootTest
public class userOrderRepairTest {
    @Autowired
    OrderRepairService orderRepairService;

    @Autowired
    UserService userService;
    @Test
    void testSubmit(){
        // 1、用户登录
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUserName("jackeylove");
        userLoginDTO.setPassword("11111111111");
        UserLoginVO login = userService.login(userLoginDTO);
        System.out.println(login);
        // 2、用户下单
        OrderRepairSubmitDTO dto = new OrderRepairSubmitDTO();
        //dto.setUserId(1L);
        dto.setUserName("zhangsan");
        dto.setUserAddr("河北秦皇岛");
        dto.setUserPhone("xxxxxxxx");
        dto.setGoodsInfo("小米14");
        dto.setSn("xxxxxxxxx");
        dto.setDesc("手机电池有问题，需要频繁充电");
        OrderRepairSubmitVO submitVO = orderRepairService.submitOrderRepair(dto);
        System.out.println(submitVO);
    }
}
