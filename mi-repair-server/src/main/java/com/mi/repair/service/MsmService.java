package com.mi.repair.service;

/**
 * @author Kuroko
 * @description
 * @date 2024/6/9 18:18
 */
public interface MsmService {
    // 任务完成后的消息通知
    void completedSend(String phone);
}
