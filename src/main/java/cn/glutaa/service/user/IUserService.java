package cn.glutaa.service.user;

import cn.glutaa.model.dto.core.Result;
import cn.glutaa.model.dto.user.UserBaseInfo;
import cn.glutaa.model.dto.user.UserRegisterParam;

public interface IUserService {

    boolean sendCheckPhoneNumberCode(String openId, String phoneNumber);

    boolean checkPhoneNumberCode(String openId, String phoneNumber, String verificationCode);

    Result userRegister(UserRegisterParam param) throws Exception;

    String createUserSession(UserBaseInfo userBaseInfo);

    UserBaseInfo getUserBaseInfo(String openId);


}
