package cn.glutaa;

import cn.glutaa.model.dto.core.Result;
import cn.glutaa.model.dto.user.UserBaseInfo;
import cn.glutaa.model.dto.user.UserRegisterParam;
import cn.glutaa.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UserServiceTesst {

    @Autowired
    private UserService userService;

    @Test
    public void userRegisterTest(){
        UserRegisterParam param = new UserRegisterParam();
        param.setOpenId("12345678");
        param.setRealName("JEF");
        param.setIdNumber("123456789012345678");
        param.setUnionId("1234567890");
        param.setPhoneNumber("1234567890");
        Result result = userService.userRegister(param);
    }

    @Test
    public void getUserBaseInfoTest(){
        UserBaseInfo userInfo = userService.getUserBaseInfo("12345678");
    }
}
