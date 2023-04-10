package cn.glutaa.model.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * 微信身份认证返回结果，参考官方：
 *    https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html
 */
public class WeChatUserIdentifyResult {

    private String session_key;//会话密钥
    private String unionid;//用户在开放平台的唯一标识符
    private String openid;//用户唯一标识
    private String errmsg;//错误信息
    private int errcode;//错误码

}