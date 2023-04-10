package cn.glutaa.interceptor;

import cn.glutaa.model.dto.core.Result;
import cn.glutaa.service.core.RedisService;
import cn.glutaa.util.StringHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 身份验证拦截器，从请求头中获取Authorization，从而判断用户身份
 */
public class AuthorizationInterceptor  implements HandlerInterceptor {

    @Resource
    private RedisService redisService;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equalsIgnoreCase(request.getMethod()))	{//放行OPTIONS请求
            System.out.println("options");
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StringHelper.isNullOrWhitespace(token) || redisService.getExpire(token) < 60) { //token不存在或过期
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(objectMapper.writeValueAsString(Result.NOT_AUTHORIZED()));
            return false;
        }
        //延长token有效时间
        redisService.setExpire(token,31*60);
        return true;
    }
}
