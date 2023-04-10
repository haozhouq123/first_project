package cn.glutaa.controller.miniapp.user;

import cn.glutaa.model.dto.core.CheckSmsVerifyCodeParam;
import cn.glutaa.model.dto.core.Result;
import cn.glutaa.model.dto.core.WeChatUserIdentifyResult;
import cn.glutaa.model.dto.enumeration.WechatErrorCode;
import cn.glutaa.model.dto.user.UserBaseInfo;
import cn.glutaa.model.dto.user.UserRegisterParam;
import cn.glutaa.service.user.IUserService;
import cn.glutaa.service.core.IAuthenticationService;
import cn.glutaa.service.core.RedisService;
import cn.glutaa.util.StringHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("user")
@Api(tags = "小程序用户相关接口")

public class UserAccountController {

    private IAuthenticationService authenticationService;
    private RedisService redisService;
    private IUserService userService;
    private HttpServletRequest req;

    @GetMapping("login")
    @ApiOperation("用户登录（创建会话）")
    public Result userLogin(HttpServletResponse resp, String jsCode){
        try{
            if(StringHelper.isNullOrWhitespace(jsCode))
                return Result.PARAM_INVALID();
            //微信身份认证
            WeChatUserIdentifyResult identifyResult = authenticationService.wechatUserAuthentication(jsCode);
            if(identifyResult.getErrcode() != WechatErrorCode.SUCCESS.code())
                //认证失败，通过code获取errmsg，覆盖返回的message
                return Result.WECHAT_USER_IDENTIFY_FAILED()
                        .setMsg(WechatErrorCode.getMessageByCode(identifyResult.getErrcode()));
            //获取用户基本信息
            UserBaseInfo userBaseInfo = userService.getUserBaseInfo(identifyResult.getOpenid());
            if(userBaseInfo == null)
                //查询不到用户信息，用户未注册
                return Result.ACCOUNT_UNREGISTERED();
            //创建会话
            String token = userService.createUserSession(userBaseInfo);
            if(token == null) return Result.FAIL().setMsg("会话创建失败！");
            //将Token放到响应头
            resp.setHeader("Authorization",token);
            return Result.SUCCESS(userBaseInfo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.SYSTEM_ERROR();
        }
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public Result userRegister(@RequestBody UserRegisterParam param) {
        try{
            //微信身份认证
            WeChatUserIdentifyResult identifyResult = authenticationService.wechatUserAuthentication(param.getJsCode());
            if(identifyResult.getErrcode() != WechatErrorCode.SUCCESS.code())
                //认证失败
                return Result.WECHAT_USER_IDENTIFY_FAILED()
                        .setMsg(WechatErrorCode.getMessageByCode(identifyResult.getErrcode()));
            //检查手机号是否验证成功
            String tempPhone = (String)redisService.get("USER:REGISTER:VERIFIED_PHONE_NUMBER:"+identifyResult.getOpenid());
            if(StringHelper.isNullOrWhitespace(tempPhone) || !tempPhone.equals(param.getPhoneNumber()))
                return Result.FAIL().setMsg("手机号验证已失效，请从新验证！");
            //注册操作
            param.setOpenId(identifyResult.getOpenid());
            param.setUnionId(identifyResult.getUnionid());
            return userService.userRegister(param);
        }catch (Exception e){
            e.printStackTrace();
            return Result.SYSTEM_ERROR();
        }
    }

    @GetMapping("getSmsVerificationCode")
    @ApiOperation("获取注册手机号验证码")
    public Result getSmsVerificationCode(String jsCode, String phoneNumber){
        try{
            //验证是否为微信用户
            WeChatUserIdentifyResult wechatResult = authenticationService.wechatUserAuthentication(jsCode);
            if(wechatResult.getErrcode() != WechatErrorCode.SUCCESS.code())
                return Result.WECHAT_USER_IDENTIFY_FAILED().setMsg(WechatErrorCode.getMessageByCode(wechatResult.getErrcode()));
            //验证手机号是否合法
            if(!StringHelper.isPhoneNumber(phoneNumber))
                return Result.PARAM_INVALID().setMsg("请输入正确的手机号码！");
            if(userService.sendCheckPhoneNumberCode(wechatResult.getOpenid(),phoneNumber)){
                return Result.SUCCESS().setMsg("验证码已发送，请注意查收！");
            }
            return Result.FAIL().setMsg("手机验证码发送失败！");
        }catch (Exception e){
            e.printStackTrace();
            return Result.SYSTEM_ERROR();
        }
    }

    @PostMapping("checkSmsVerificationCode")
    @ApiOperation("验证注册手机号")
    public Result checkSmsVerificationCode(@RequestBody CheckSmsVerifyCodeParam param){
        try{
            //验证是否为微信用户
            WeChatUserIdentifyResult wechatResult = authenticationService.wechatUserAuthentication(param.getJsCode());
            if(wechatResult.getErrcode() != WechatErrorCode.SUCCESS.code())
                return Result.WECHAT_USER_IDENTIFY_FAILED().setMsg(WechatErrorCode.getMessageByCode(wechatResult.getErrcode()));
            //验证手机号是否合法
            if(!StringHelper.isPhoneNumber(param.getPhoneNumber()))
                return Result.PARAM_INVALID().setMsg("请输入正确的手机号码！");
            //检查验证码是否正确
            if(userService.checkPhoneNumberCode(wechatResult.getOpenid(),param.getPhoneNumber(),param.getVerificationCode()))
                return Result.SUCCESS();
            return Result.FAIL().setMsg("验证码错误！");
        }catch (Exception e){
            e.printStackTrace();
            return Result.SYSTEM_ERROR();
        }
    }



}

