package fun.shiyang;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * @author ay
 * @create 2020-09-01 12:45
 */
@Data
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;

}