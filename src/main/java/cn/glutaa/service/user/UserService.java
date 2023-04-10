package cn.glutaa.service.user;

import cn.glutaa.model.dto.core.Result;
import cn.glutaa.model.dto.user.UserBaseInfo;
import cn.glutaa.model.dto.user.UserRegisterParam;
import cn.glutaa.model.entity.User;
import cn.glutaa.repository.user.UserRepository;
import cn.glutaa.service.core.AuthenticationService;
import cn.glutaa.service.core.RedisService;
import cn.glutaa.util.StringHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

    private final AuthenticationService authenticationService;
    private final RedisService redisService;
    private final ModelMapper modelMapper;
    public final UserRepository userRepository;

    //首次注册，验证手机号，发送手机验证码
    public boolean sendCheckPhoneNumberCode(String openId, String phoneNumber) {
        try{
            String code = (int)(Math.random() * (999999-100000+100000)) + "";//生成6位验证码
            //将验证码存入redis,有效时间5分钟
            redisService.set("USER:REGISTER:SMS_VERIFY_CODE:"+openId,new VerificationCodeTemp(phoneNumber,code),60*6);
            return authenticationService.sendSmsMessage(phoneNumber,"您的注册验证码为："+code+"，有效时间5分钟。");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPhoneNumberCode(String openId, String phoneNumber, String verificationCode) {
        try{
            //获取缓存中的验证信息
            VerificationCodeTemp temp = (VerificationCodeTemp)redisService.get("USER:REGISTER:SMS_VERIFY_CODE:"+openId);
            //验证信息不存在或验证信息不匹配
            if(temp == null || !temp.getKey().equals(phoneNumber) || !temp.getVerificationCode().equals(verificationCode))
                return false;
            //手机号验证成功，保留30分钟，30分钟未注册需要重新重新验证
            return redisService.set("USER:REGISTER:VERIFIED_PHONE_NUMBER:"+openId,phoneNumber,60*31);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //用户注册
    public Result userRegister(UserRegisterParam param) {

        //todo 验证手机号是否注册

        User user = modelMapper.map(param,User.class);
        user.setCreateTime(new Date());
        user.setUserName(StringHelper.getRandomString(10));
        user.setGender(0);
        user.setStatus(1);
        userRepository.save(user);
        return Result.SUCCESS().setMsg("注册成功！");
    };

    //获取用户基本信息
    public UserBaseInfo getUserBaseInfo(String openId) {
        User user = userRepository.getByOpenId(openId);
        return modelMapper.map(user,UserBaseInfo.class);
    }

    //创建用户会话
    public String createUserSession(UserBaseInfo userBaseInfo) {
        String token = StringHelper.getRandomString(128);
        if(redisService.set("USER:SESSION:"+ token,userBaseInfo,60*31))
            return token;
        return null;
    }
}

@Data
@AllArgsConstructor
class VerificationCodeTemp{
    private String key;
    private String verificationCode;
}
