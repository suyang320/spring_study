package cn.sue.spring.test.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author Sue
 * @className MyBeanPostProcessor
 * @create 2023/4/25
 **/
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    public MyBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcessor{}实现类MyBeanPostProcessor{}的构造函数");
    }

    // 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的业务
    @Override
    public Object postProcessAfterInitialization(Object arg0, String arg1)
            throws BeansException {

        if (arg0.getClass() == TestBeanA.class || arg0.getClass() == TestBeanB.class) {
            System.out
                    .println("BeanPostProcessor.postProcessAfterInitialization() 来自MyBeanPostProcessor，beanName:" + arg1);
        }
        return arg0;
    }

    // 实例化、依赖注入、初始化后完成一些定制的业务
    @Override
    public Object postProcessBeforeInitialization(Object arg0, String arg1)
            throws BeansException {

        if (arg0.getClass() == TestBeanA.class || arg0.getClass() == TestBeanB.class) {
            System.out
                    .println("BeanPostProcessor.postProcessBeforeInitialization() 来自MyBeanPostProcessor，beanName:" + arg1);
        }
        return arg0;
    }

}