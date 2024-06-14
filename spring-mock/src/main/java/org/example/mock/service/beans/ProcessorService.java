package org.example.mock.service.beans;

import lombok.Getter;
import lombok.Setter;
import org.example.mock.spring.IComponent;
import org.example.mock.spring.IInitializingBean;

/**
 * @author caimeng
 * @date 2024/6/13 17:49
 */
@IComponent
public class ProcessorService implements IInitializingBean {

    @Setter
    @Getter
    private String processor;

    @Override
    public void afterPropertiesSet() {
        System.out.println("ProcessorService IInitializingBean ...");
        this.processor = "IInitializingBean";
    }
}
