package com.example.admin.interceptor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@Component
public class RedisCountInterceptor implements HandlerInterceptor {

    //@Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        //默认每次访问当前uri就会计数+1
        redisTemplate.opsForValue().increment(uri);
        return true;
    }
}
