package fun.shiyang.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ay
 * @create 2020-09-01 14:48
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 调用方法的参数
     */
    private Object[] parameters;
    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
