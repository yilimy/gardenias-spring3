package copy.org.apache.ibatis.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/5/10 20:02
 */
public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList<>();
    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    @Override
    public String handleToken(String content) {
        // name ---> add list
        parameterMappings.add(new ParameterMapping(content));
        // #{name} ===> ?
        return "?";
    }

}
