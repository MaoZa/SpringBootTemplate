package cn.dawnland.springboottemplate.advice;

import cn.dawnland.springboottemplate.utils.AESOperator;
import cn.dawnland.springboottemplate.utils.SerializedField;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Cap_Sub
 */
/**
 * 返回数据加密
 */
@Deprecated
// TODO: 2018/12/14 暂时停用响应加密
//@RestControllerAdvice(annotations = RestController.class)
public class ResponseBodyAdviceImpl implements ResponseBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(ResponseBodyAdviceImpl.class);

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        boolean encode = false;
        if (returnType.getMethod().isAnnotationPresent(SerializedField.class)) {
            //获取注解配置的包含和去除字段
            SerializedField serializedField = returnType.getMethodAnnotation(SerializedField.class);
            //是否加密
            encode = serializedField.encode();
        }
        if (encode) {
            logger.debug("对方法method :" + returnType.getMethod().getName() + "返回数据进行加密");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                return AESOperator.Encrypt(result);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return body;
    }
}
