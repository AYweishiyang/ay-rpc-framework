package fun.shiyang.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author ay
 * @create 2020-09-04 8:29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Component
public @interface RpcService {
    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";
}
