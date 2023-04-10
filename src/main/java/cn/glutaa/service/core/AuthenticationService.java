package cn.glutaa.service.core;

import cn.glutaa.model.dto.core.WeChatUserIdentifyResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Data
@Service
@NoArgsConstructor
@AllArgsConstructor
//扫描配置文件中的bean
@ConfigurationProperties(prefix = "wechatkey")
public class AuthenticationService implements IAuthenticationService{

    // appId、secret、reqUrl三个值从yml文件中注入
    private String appId;
    private String secret;
    private String reqUrl;

    private RedisService redisService;
    private IHttpRequestService requestService;


    public WeChatUserIdentifyResult wechatUserAuthentication(String jsCode) throws IllegalAccessException {
        Map<String,String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("js_code", jsCode);
        return requestService.sendGetRequestReturnData(reqUrl, params, WeChatUserIdentifyResult.class);
    }

    public boolean sendSmsMessage(String phoneNumber, String message) {
        try{
            //todo 调用api发送短信验证码

            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
