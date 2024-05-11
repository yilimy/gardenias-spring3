package copy.org.apache.ibatis.parsing;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author caimeng
 * @date 2024/5/10 20:04
 */
@Data
@AllArgsConstructor
public class ParameterMapping {
    /**
     * sql中#{name}，赋值给 property
     */
    private String property;
}
