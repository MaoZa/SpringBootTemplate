package cn.dawnland.springboottemplate.advice;

import cn.dawnland.springboottemplate.utils.AESOperator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author Cap_Sub
 */
//@RestControllerAdvice(annotations = RestController.class) 关闭请求解密
public class RequestBodyAdviceImpl implements RequestBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(RequestBodyAdviceImpl.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            return new MyHttpInputMessage(inputMessage);
        } catch (Exception e) {
            logger.error("解密出错 无效请求跳过");
            return null;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage {

        private HttpHeaders headers;
        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            String bodyContent = IOUtils.toString(inputMessage.getBody(), "UTF-8");
            this.headers = inputMessage.getHeaders();
            logger.debug("请求headers:" + inputMessage.getHeaders() + " | 请求body:" + bodyContent);
            if (!StringUtils.isEmpty(bodyContent)) {
                this.body = IOUtils.toInputStream(AESOperator.Decrypt(bodyContent), "UTF-8");
            }
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
