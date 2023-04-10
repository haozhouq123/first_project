package cn.glutaa;

import cn.glutaa.model.dto.user.UserRegisterParam;
import cn.glutaa.model.entity.User;
import cn.glutaa.repository.user.UserRepository;
import cn.glutaa.service.core.HttpRequestService;
import cn.glutaa.service.core.RedisService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootTest
class GlutaaApiApplicationTests {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HttpRequestService httpService;
    @Autowired
    RedisService redisService;
    @Autowired
    UserRepository userRepository;


    @Test
    void contextLoads() {
    }


    @Test
    public void restTemplateTest(){
        Object resp = restTemplate.getForObject("https://v-api.2345.com/shortVideo/api/GetListByLocation?page=1&size=100&location=tianqihome-shortMovie&_=1678013158572", Object.class);
    }

    @Test
    public void redisTest(){
        redisService.set("testkey","test value");
        String str = (String) redisService.get("testkey");
        boolean result = redisService.delOneByKey("testkey");
    }

    @Test
    public void addUserTest(){
        User user = new User();
        user.setOpenId("asdfg12345");
        user.setUnionId("12345asdfg");
        user.setUserName("asdfg");
        user.setGender(0);
        user.setRealName("李小四");
        user.setPhoneNumber("12345123450");
        user.setIdNumber("123451234512345123");
        user.setStatus(1);
        user.setCreateTime(new Date());
        User result = userRepository.saveAndFlush(user);
    }

    @Autowired
    ModelMapper modelMapper;
    @Test
    public void modelMapperTest(){
        UserRegisterParam param = new UserRegisterParam();
        param.setUnionId("123456");
        param.setIdNumber("123456789012345678");
        param.setOpenId("1234qwer");
        param.setRealName("qwer");
        param.setOpenId("12345678");

        User user = modelMapper.map(param,User.class);
    }
}