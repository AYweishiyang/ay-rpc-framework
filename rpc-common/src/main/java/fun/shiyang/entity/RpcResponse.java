package fun.shiyang.entity;

import fun.shiyang.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ay
 * @create 2020-09-01 15:03
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer statusCode;
    /**
     * 响应状态补充信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    /**
     * 用于快速生成成功与失败的响应对象。其中，statusCode属性可以自行定义，客户端服务端一致即可
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    /**
     * 用于快速生成成功与失败的响应对象。其中，statusCode属性可以自行定义，客户端服务端一致即可
     * @param code
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
