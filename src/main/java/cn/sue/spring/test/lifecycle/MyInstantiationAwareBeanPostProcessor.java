package cn.sue.spring.test.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

/**
 * <p>
 *
 * </p>
 *
 * @author Sue
 * @className MyInstantiationAwareBeanPostProcessor
 * @create 2023/4/25
 **/
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out
                .println("InstantiationAwareBeanPostProcessorAdapter{}实现类MyInstantiationAwareBeanPostProcessor{}的构造函数");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass() == TestBeanA.class || bean.getClass() == TestBeanB.class) {
            System.out
                    .println("InstantiationAwareBeanPostProcessorAdapter.postProcessBeforeInitialization() 来自MyInstantiationAwareBeanPostProcessor，beanName:" + beanName);

        }
        return bean;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean.getClass() == TestBeanA.class || bean.getClass() == TestBeanB.class) {
            System.out
                    .println("InstantiationAwareBeanPostProcessorAdapter.postProcessAfterInitialization() 来自MyInstantiationAwareBeanPostProcessor，beanName:" + beanName);
        }

        return bean;
    }

    // 接口方法、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs,
                                                    PropertyDescriptor[] pds, Object bean, String beanName)
            throws BeansException {

        if (bean.getClass() == TestBeanA.class || bean.getClass() == TestBeanB.class) {
            System.out
                    .println("InstantiationAwareBeanPostProcessorAdapter.postProcessPropertyValues() 来自MyInstantiationAwareBeanPostProcessor，beanName:" + beanName);
        }

        return pvs;
    }

}