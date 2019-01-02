package cn.dawnland.springboottemplate.advice;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Cap_Sub
 */

@Deprecated
public class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        if(!"T".equals(servletRequest.getParameter("flag"))){
//            servletResponse.getWriter().println();
//        }
    }
}
