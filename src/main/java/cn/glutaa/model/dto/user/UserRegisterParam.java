package cn.glutaa.model.dto.user;

import lombok.Data;

@Data
public class UserRegisterParam {

    private String jsCode;
    private String verificationCode;
    private String openId;//用户唯一标识
    private String unionId;//用户在开放平台的唯一标识符
    private String realName;
    private String idNumber;
    private String phoneNumber;

}
