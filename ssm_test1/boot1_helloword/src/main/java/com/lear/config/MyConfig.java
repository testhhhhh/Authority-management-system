package com.lear.config;

import ch.qos.logback.classic.db.DBHelper;
import com.lear.bean.Car;
import com.lear.bean.Role;
import com.lear.bean.UserInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件,也就是说容器中也能获取到这个配置类
 * 3、proxyBeanMethods：代理bean的方法
 *      全配置Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 *      轻量级配置Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 *      组件依赖必须使用Full模式默认。其他默认是否Lite模式
 * 4、@Import({User.class, DBHelper.class})
 *      给容器中调用这两个组件的无参构造器自动创建出这两个类型的组件对象、默认组件的名字就是全类名(无参构造器，所以相对应的类一定要提供无参构造器)
 * 5.@ImportResource("classpath:bean.xml")导入Spring的配置文件
 * 6.条件装配：满足Conditional指定的条件，则进行组件注入
 *
 *
 */
@Import({UserInfo.class,DBHelper.class})
@Configuration(proxyBeanMethods = true)//告诉SpringBoot这是一个配置类 == 配置文件
//@ConditionalOnBean(name = "tom")//容器中有tom这个组件就加载配置类里面的组件
@ConditionalOnMissingBean(name = "tom")//容器中没有这个组件就加载配置类里面的组件
@ImportResource("classpath:bean.xml")//原生配置文件引入
@EnableConfigurationProperties(Car.class)//开启Car的绑定配置功能，并且把这个car这个组件自动注册到容器中
public class MyConfig {


    /**
     * Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     * @return

     */

    @Bean//给容器中添加组件，以方法名作为组件的id，返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public UserInfo user01(){
        UserInfo userInfo = new UserInfo("user01", "ADMIN");
        //user组件依赖了role组件
        userInfo.setRole(role01());
        return userInfo;

    }

    @Bean(name = "tom2")
    public Role role01(){
        return new Role("role01");
    }

    @Bean//你自己配了一个字符编码过滤器，那框架就会优先用你的，底层就不帮你配置这个玩意了
    public CharacterEncodingFilter characterEncodingFilter(){
        return new CharacterEncodingFilter("UTF-8");
    }
}
