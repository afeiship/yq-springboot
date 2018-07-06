package cn.jzyunqi;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
public class AdminRestBaseController {
    @Resource
    protected ApplicationEventPublisher publisher;

    //这里没有抽取公共方法publishEvent是因为抽取后，idea无法定位了，为方便编码，不再抽取公共方法
}
