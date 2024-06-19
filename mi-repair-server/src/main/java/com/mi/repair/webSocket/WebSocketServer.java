package com.mi.repair.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/20 14:08
 */
@Component
@ServerEndpoint("/ws/{sid}/{role}")
@Slf4j
public class WebSocketServer {
    // 会话对象
    private static Map<String, Session> sessionMap = new HashMap<>();
    /**
     * 连接建立成功调用
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid,@PathParam("role") String role){
      String key = role.equals("0")?"用户:"+sid:"工程师:"+sid;
      log.info("客户端:" + key + "建立连接");
      sessionMap.put(key, session);
    }

    /**
     * 收到客户端消息后调用
     * @param message
     * @param sid
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid){
        log.info("收到来自客户端:" + sid + "的消息:" + message);
    }

    /**
     * 连接断开
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid,@PathParam("role") String role){
        String key = role.equals("0")?"用户:"+sid:"工程师:"+sid;
        log.info("连接断开:" + key);
        sessionMap.remove(key);
    }

    public void sendToAllClient(String message){
        Collection<Session> sessions = sessionMap.values();
        for(Session session : sessions){
            try{
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void sendToClient(String key,String message){
        if(sessionMap.containsKey("用户:"+key)){
            Session session = sessionMap.get("用户:"+key);
            try{
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void sendToWorker(String key,String message){
        if(sessionMap.containsKey("工程师:"+key)){
            Session session = sessionMap.get("工程师:"+key);
            try{
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
