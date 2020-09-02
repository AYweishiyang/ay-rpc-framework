package fun.shiyang.exception;

import fun.shiyang.enumeration.RpcError;
import fun.shiyang.enumeration.RpcErrorMessage;

/**
 * @author ay
 * @create 2020-09-01 18:33
 */
public class RpcException extends RuntimeException{
    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(RpcErrorMessage error, String detail) {
        super(error.getMessage() + ": " + detail);
    }
    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
