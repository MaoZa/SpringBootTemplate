package cn.dawnland.springboottemplate.interceptor;

import cn.dawnland.springboottemplate.models.UserSession;
import cn.dawnland.springboottemplate.models.result.ReturnData;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(uri.startsWith("/admin")){
            return true;
        }else {
//            if ("127.0.0.1".equals(request.getRemoteAddr()) || "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) || "ximuok".equals(request.getParameter("h"))) {
//                if (request.getSession().getAttribute("userSession") == null) {
//                    request.getSession().setAttribute("userSession", new UserSession(1L, "127.0.0.1"));
//                    logger.info("远程ip为：" + request.getRemoteAddr() + "设定userSession为1L测试用户");
//                }
//            }
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            if (userSession == null) {
                response.setHeader("Content-Type", "application/json");
                response.getWriter().println(JSON.toJSON(ReturnData.isFail("用户未登录").status(-1)));
                // TODO: 2018/12/14 此处响应也需进行加密
                return false;
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { }


}
