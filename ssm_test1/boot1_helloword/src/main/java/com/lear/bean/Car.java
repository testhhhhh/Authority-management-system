package com.lear.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 只有在容器中的组件，才会拥有SpringBoot提供的强大功能
 * @Component + @ConfigurationProperties：
 *    @Component:将组件交给ioc容器管理
 *    @ConfigurationProperties(prefix = "mycar"):配置绑定，
 *          找出springboot核心配置文件application.properties中以mycar为前缀的配置信息注入到标注的类car里面，然后要
 *          结合@Component注解将初始化完的组件放入ioc容器中
 *@EnableConfigurationProperties + @ConfigurationProperties：
 *    因为有时候要与配置绑定的类可能是第三方的jar包类，不可能改人家的代码，在人家类上加上@Component使其生效
 *    所以可以在myconfig配置类中用@EnableConfigurationProperties注解，作用是
 *    使使用 @ConfigurationProperties 注解的类生效。说白了 @EnableConfigurationProperties
 *    相当于把使用 @ConfigurationProperties 的类进行了一次注入。
 *
 */
//@Component//2、把这个Car这个组件自动注册到容器中
@ConfigurationProperties(prefix = "mycar")//1、开启Car配置绑定功能
public class Car {
    private String brand;
    private Integer price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
