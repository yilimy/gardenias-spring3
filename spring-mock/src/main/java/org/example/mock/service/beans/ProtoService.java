package org.example.mock.service.beans;

import org.example.mock.spring.IComponent;
import org.example.mock.spring.IScope;

/**
 * 原型bean
 * @author caimeng
 * @date 2024/6/13 14:24
 */
@IComponent
@IScope(IScope.PROTOTYPE)
public class ProtoService {
}
