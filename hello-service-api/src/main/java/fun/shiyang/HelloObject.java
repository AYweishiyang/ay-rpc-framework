package fun.shiyang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author ay
 * @create 2020-09-01 12:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;

}