package cn.sue.spring.test.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author Sue
 * @className User
 * @create 2023/4/1
 **/
@Component
public class UserService {

    @Value("name")
    private User user;

    ApplicationContext applicationContext;

    public UserService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private String userName;
    private String sex;

    public void init() {
        System.out.println("执行初始化方法");
    }

    public void testListener() {
        applicationContext.publishEvent("发布了一个事件");
    }
}