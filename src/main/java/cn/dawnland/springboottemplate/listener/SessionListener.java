package cn.dawnland.springboottemplate.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Auther: Cap_SUb
 * @Date: 2018/12/5 09:51
 * @Description:
 */

@WebListener
public class SessionListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(SessionListener.class);

    /**
     * 记录session的数量
     */
    public int count = 0;

    //监听session的创建,synchronized 防并发bug
    @Override
    public synchronized void sessionCreated(HttpSessionEvent arg0) {
        count++;
        arg0.getSession().getServletContext().setAttribute("loginCount", count);
        logger.info("【Session监听器】在线人数+1;" + "当前在线人数:" + arg0.getSession().getServletContext().getAttribute("loginCount"));
    }
    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent arg0) {//监听session的撤销
        count--;
        arg0.getSession().getServletContext().setAttribute("loginCount", count);
        logger.info("【Session监听器】在线人数-1;" + "当前在线人数:" + arg0.getSession().getServletContext().getAttribute("loginCount"));
    }
}