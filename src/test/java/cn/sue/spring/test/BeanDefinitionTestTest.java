package cn.sue.spring.test;

import cn.sue.spring.test.core.*;
import org.junit.Test;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class BeanDefinitionTestTest {

    /**
     * BeanDefinition表示Bean定义，BeanDefinition中存在很多属性用来描述一个Bean的特点。比如：
     * ● class，表示Bean类型
     * ● scope，表示Bean作用域，单例或原型等
     * ● lazyInit：表示Bean是否是懒加载
     * ● initMethodName：表示Bean初始化时要执行的方法
     * ● destroyMethodName：表示Bean销毁时要执行的方法
     * ● 还有很多...
     * 在Spring中，我们经常会通过以下几种方式来定义Bean：
     * 1. <bean/>
     * 2. @Bean
     * 3. @Component(@Service,@Controller)
     * 这些，我们可以称之申明式定义Bean。
     * 我们还可以编程式定义Bean，那就是直接通过BeanDefinition，比如：
     */
    @Test
    public void testBeanDefinition() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        // 生成一个BeanDefinition对象，并设置beanClass为User.class，并注册到ApplicationContext中
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(UserService.class);
        //我们还可以通过BeanDefinition设置一个Bean的其他属性
        beanDefinition.setScope("prototype"); // 设置作用域
        beanDefinition.setInitMethodName("init"); // 设置初始化方法
        beanDefinition.setLazyInit(true); // 设置懒加载
        context.registerBeanDefinition("beanName", beanDefinition);
        System.out.println(context.getBean("beanName"));
    }

    /**
     * BeanDefinitionReader
     * 接下来，我们来介绍几种在Spring源码中所提供的BeanDefinition读取器（BeanDefinitionReader），
     * 这些BeanDefinitionReader在我们使用Spring时用得少，但在Spring源码中用得多，相当于Spring源码的基础设施。
     */

    /**
     * 可以直接把某个类转换为BeanDefinition，并且会解析该类上的注解，比如
     * 注意：它能解析的注解是：@Conditional，@Scope、@Lazy、@Primary、@DependsOn、@Role、@Description
     */
    @Test
    public void testAnnotatedBeanDefinitionReader() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(context);
        // 将User.class解析为BeanDefinition
        annotatedBeanDefinitionReader.register(UserService.class);
        System.out.println(context.getBean("user"));
    }

    /**
     * 可以解析<bean/>标签
     */
    @Test
    public void testXmlBeanDefinitionReader() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        int i = xmlBeanDefinitionReader.loadBeanDefinitions("spring.xml");
        System.out.println(context.getBean("user"));
    }

    /**
     * ClassPathBeanDefinitionScanner是扫描器，但是它的作用和BeanDefinitionReader类似，
     * 它可以进行扫描，扫描某个包路径，对扫描到的类进行解析，比如，扫描到的类上如果存在@Component注解，
     * 那么就会把这个类解析为一个BeanDefinition，
     * 比如：
     */
    @Test
    public void testClassPathBeanDefinitionScanner() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
        scanner.scan("cn.sue.spring.test");
        System.out.println(context.getBean("user"));
    }

    //spring容器可以列为applicationContext BeanFactory BeanDefinitionMap 单例池
    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(UserService.class);
        // 可以注册bean，获取bean
        beanFactory.registerBeanDefinition("user", beanDefinition);
        System.out.println(beanFactory.getBean("user"));
    }

    /**
     * 国际化
     */

    @Test
    public void testMessageSource() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getMessage("test", null, new Locale("en")));
    }

    /**
     * 资源加载
     */
    @Test
    public void testResource() throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Resource resource = context.getResource("file:///Users/sue/code/spring-code-study/src/main/java/cn/sue/spring/test/User.java");
        System.out.println(resource.contentLength());

        //还可以
        Resource resource1 = context.getResource("https://www.baidu.com");
        System.out.println(resource1.contentLength());
        System.out.println(resource1.getURL());

        Resource resource2 = context.getResource("classpath:spring.xml");
        System.out.println(resource2.contentLength());
        System.out.println(resource2.getURL());

        //还可以一次性获取多个
        Resource[] resources = context.getResources("classpath:cn/sue/spring/*.class");
        for (Resource resource3 : resources) {
            System.out.println(resource3.contentLength());
            System.out.println(resource3.getFilename());
        }
    }

    /**
     * 获取运行时环境
     */
    @Test
    public void testEnv() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Map<String, Object> systemEnvironment = context.getEnvironment().getSystemEnvironment();
        System.out.println(systemEnvironment);

        System.out.println("=======");

        Map<String, Object> systemProperties = context.getEnvironment().getSystemProperties();
        System.out.println(systemProperties);

        System.out.println("=======");

        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        System.out.println(propertySources);

        System.out.println("=======");

        System.out.println(context.getEnvironment().getProperty("NO_PROXY"));
        System.out.println(context.getEnvironment().getProperty("sun.jnu.encoding"));
        System.out.println(context.getEnvironment().getProperty("zhouyu"));
    }

    @Test
    public void testListener() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(context);
        // 将User.class解析为BeanDefinition
        annotatedBeanDefinitionReader.register(UserService.class);
        UserService user = (UserService) context.getBean("userService");
        user.testListener();
    }

    @Test
    public void testStringToUserPropertyEditor() {
        StringToUserPropertyEditor propertyEditor = new StringToUserPropertyEditor();
        propertyEditor.setAsText("1");
        User value = (User) propertyEditor.getValue();
        System.out.println(value);
    }

    @Test
    public void testStringToUserPropertyEditor2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(context);
        // 将User.class解析为BeanDefinition
        annotatedBeanDefinitionReader.register(UserService.class);
        UserService user = (UserService) context.getBean("userService");
        System.out.println(user);
    }

    @Test
    public void testStringToUserPropertyEditor3() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToUserConverter());
        User value = conversionService.convert("1", User.class);
        System.out.println(value);    }

    @Test
    public void testStringToUserPropertyEditor4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(context);
        // 将User.class解析为BeanDefinition
        annotatedBeanDefinitionReader.register(UserService.class);
        UserService user = (UserService) context.getBean("userService");
        System.out.println(user);
    }

    @Test
    public void testTypeConverter() {
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        typeConverter.registerCustomEditor(User.class, new StringToUserPropertyEditor());
//typeConverter.setConversionService(conversionService);
        User value = typeConverter.convertIfNecessary("1", User.class);
        System.out.println(value);
    }

    @Test
    public void testLifeCycle() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");

        System.out.println("user");
    }
}