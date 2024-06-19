package com.mi.repair.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.mi.repair.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Kuroko
 * @description
 * @date 2024/6/9 18:50
 */
@Service
@Slf4j
public class MsmServiceImpl implements MsmService {
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    @Override
    public void completedSend(String phone) {
        Config config = new Config().setAccessKeyId(accessKeyId)
                                    .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            Client client = new Client(config);
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName("mirepairs").setTemplateCode(templateCode).setPhoneNumbers(phone);
            RuntimeOptions runtime = new RuntimeOptions();
            try {
                // 复制代码运行请自行打印 API 的返回值
                client.sendSmsWithOptions(request, runtime);
            } catch (TeaException error) {
                // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
                // 错误 message
                log.info(error.getMessage());
                // 诊断地址
                System.out.println(error.getData().get("Recommend"));
                com.aliyun.teautil.Common.assertAsString(error.message);
            } catch (Exception _error) {
                TeaException error = new TeaException(_error.getMessage(), _error);
                // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
                // 错误 message
                log.info(error.getMessage());
                // 诊断地址
                System.out.println(error.getData().get("Recommend"));
                com.aliyun.teautil.Common.assertAsString(error.message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
