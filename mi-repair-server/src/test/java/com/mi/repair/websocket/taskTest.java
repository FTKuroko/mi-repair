package com.mi.repair.websocket;

import com.mi.repair.webSocket.WebSocketServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/20 14:43
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class taskTest {
    @Autowired
    private WebSocketServer server;
    @Test
    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessageToClient() {
        server.sendToAllClient("这是来自服务端的消息：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
    }

}
