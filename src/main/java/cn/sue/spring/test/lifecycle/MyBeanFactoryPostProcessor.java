package cn.sue.spring.test.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author Sue
 * @className MyBeanFactoryPostProcessor
 * @create 2023/4/25
 **/
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("BeanFactoryPostProcessor{}实现类MyBeanFactoryPostProcessor{}的构造函数");
    }

    // 允许我们在工厂里所有的bean被加载进来后但是还没初始化前，对所有bean的属性进行修改也可以add属性值，该操作在对应bean的构造函数执行前
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
        System.out.println("BeanFactoryPostProcessor.postProcessBeanFactory()，来自MyBeanFactoryPostProcessor");
        //获取到Spring中所有的beanName
        String[] beanStr = arg0.getBeanDefinitionNames();
        //循环打印
        for (String beanName : beanStr) {
            System.out.print("已加载的bean name:" + beanName + ";" + "\n");
        }
        System.out.println();
    }

}