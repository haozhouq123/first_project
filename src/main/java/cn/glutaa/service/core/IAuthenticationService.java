package cn.glutaa.service.core;

import cn.glutaa.model.dto.core.WeChatUserIdentifyResult;

public interface IAuthenticationService {


    //微信用户验证
    WeChatUserIdentifyResult wechatUserAuthentication(String jsCode) throws IllegalAccessException;

    //发送短信验证码
    boolean sendSmsMessage(String phoneNumber, String message);
}
