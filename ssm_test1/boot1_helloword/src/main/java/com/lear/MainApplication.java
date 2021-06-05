package com.lear;

import ch.qos.logback.classic.db.DBHelper;
import com.lear.bean.Role;
import com.lear.bean.UserInfo;
import com.lear.config.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 主程序类   ctrl+f  :  在控制台查找输出
 * @SpringBootApplication：这是一个springboot应用
 *
 * @SpringBootApplication
 * 等同于
 * @SpringBootConfiguration：@Configuration。代表当前是一个配置类,也就是说main程序也是一个配置类
 * @ComponentScan("com.atguigu.boot")：指定扫描哪些，Spring注解；
 * @EnableAutoConfiguration：
 *              @AutoConfigurationPackage：自动配置包，指定了默认的包规则
 *                   @Import(AutoConfigurationPackages.Registrar.class)  //给容器中导入一个组件
 *                      //利用Registrar给容器批量注册一系列组件即导入一系列组件
 *                          //将指定的一个包下的所有组件导入进来？MainApplication 所在包下。（
 *                          因为@SpringBootApplication里面的@AutoConfigurationPackage这个注解随着@SpringBootApplication标注在main函数上，
 *                          所以就会扫描该注解所在类所在的包下的所有组件并且全部导入到容器中）
 *              @Import(AutoConfigurationImportSelector.class)
 *                  AutoConfigurationImportSelector.class：
 *                      1、利用getAutoConfigurationEntry(annotationMetadata);给容器中批量导入一些组件
 *                              2、调用List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes)获取到所有需要导入到容器中的配置类
 *                                  3、利用工厂加载 Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader)；得到所有的组件
 *                                      4、从META-INF/spring.factories位置来加载一个文件。
 *                                          默认扫描我们当前系统里面所有META-INF/spring.factories位置的文件
 *                                          spring-boot-autoconfigure-2.3.4.RELEASE.jar包里面也有META-INF/spring.factories
 *                                              文件里面写死了spring-boot一启动就要给容器中加载的所有配置类
 *                                               2spring-boot-autoconfigure-2.3.4.RELEASE.jar/META-INF/spring.factories
 *                                               按需开启自动配置项：虽然我们127个场景的所有自动配置启动的时候默认全部加载。xxxxAutoConfiguration
 *                                                                 按照条件装配规则（@Conditional），最终会按需配置。
 *
 * 修改默认配置：
 *      例如：@Bean
 *         @ConditionalOnBean(MultipartResolver.class)  //容器中有这个类型组件
 *         @ConditionalOnMissingBean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME) //容器中没有这个名字 multipartResolver 的组件
 *         public MultipartResolver multipartResolver(MultipartResolver resolver) {
 *             //给@Bean标注的方法传入了对象参数，这个参数的值就会从容器中找。
 *             //SpringMVC multipartResolver。防止有些用户配置的文件上传解析器不符合规范
 *             // Detect if the user has created a MultipartResolver but named it incorrectly
 *             return resolver;//直接把用户命名的resolver这个MultipartResolver对象返回，但是是以这个方法的方法名multipartResolver为id返回并放入到ioc容器中
 *             //相当于如果用户不知道文件上传解析器id是规定multipartResolver的，即使起名字错误了，框架也会帮他把起错名字的对象再起个正确的名字返回并放在ioc容器中供他使用
 *             //从头到尾只有一个bean但是是有两个不同名字的bean
 *         }
 *          给容器中加入了文件上传解析器；
 *
 * SpringBoot默认会在底层配好所有的组件。但是如果用户自己配置了以用户的优先
 *      例如：@Bean
 *     @ConditionalOnMissingBean//没有这个bean框架就帮你配，有这个bean，就用你自己配的那个
 *     public CharacterEncodingFilter characterEncodingFilter() {
 *     //
 *     }
 *
 *
 * 总结：
 * • SpringBoot先加载所有的自动配置类  xxxxxAutoConfiguration
 * • 每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。xxxxProperties里面拿。xxxProperties和配置文件进行了绑定
 * • 生效的配置类就会给容器中装配很多组件
 * • 只要容器中有这些组件，相当于这些功能就有了
 * • 定制化配置
 * • 用户直接自己@Bean替换底层的组件
 * • 用户去看这个组件是获取的配置文件什么值就去修改。
 * xxxxxAutoConfiguration ---> 组件  ---> xxxxProperties里面拿值  ----> application.properties
 */
@SpringBootApplication(scanBasePackages = "com.lear")
public class MainApplication {
    public static void main(String[] args) {
        //1.返回ioc容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class,args);//固定写法
        //2.查看容器里面的组件
//        String[] names = run.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(name);
//        }
        //3.从容器中获取组件
//        Role tom = run.getBean("tom", Role.class);
//        Role tom2 = run.getBean("tom", Role.class);
//        System.out.println("组件："+(tom==tom2));

        //com.lear.config.MyConfig$$EnhancerBySpringCGLIB$$5a7b160@1fac1d5c
        //这个bean本身就是个SpringCGLIB增强了的代理对象
//        MyConfig bean = run.getBean(MyConfig.class);
//        System.out.println(bean);

        //如果@Configuration(proxyBeanMethods = true)代理对象调用方法。
        // SpringBoot总会检查这个组件是否在容器中有。如果有就拿，如果没有就再来调用新创
        //保持组件单实例
        //如果@Configuration(proxyBeanMethods = false)
        // SpringBoot会一直调用新创对象，不管容器里面有没有一定是直接新创，所以肯定与容器里面的对象不是同一个对象
//        UserInfo userInfo = bean.user01();
//        UserInfo userInfo1 = bean.user01();
//        UserInfo userInfo1 = run.getBean("user01", UserInfo.class);
//        System.out.println(userInfo==userInfo1);
//        Role tom = run.getBean("tom", Role.class);
//        Role role = userInfo1.getRole();
//        System.out.println(role==tom);


        //获取组件
//        String[] beanNamesForType = run.getBeanNamesForType(UserInfo.class);
//        System.out.println("========================================");
//        for (String s:beanNamesForType){
//            System.out.println(s);
//        }
//        String[] beanNamesForType1 = run.getBeanNamesForType(DBHelper.class);
//        System.out.println("-----------------------------");
//        for (String s:beanNamesForType1){
//            System.out.println(s);
//        }
//
//        boolean tom = run.containsBean("tom");
//        System.out.println(tom);

        /**
         * xml文件要用classpath加载的时候才会实例化xml文件里面的bean，不然用注解的话，
         * springboot压根就不知道bean.xml配置文件用来干嘛，所以不会加载配置文件里面的bean放在ioc里面
         *@ImportResource引入原生配置文件，有了这个注解就能加载原生配置文件放在ioc容器里面
         */
//        boolean u = run.containsBean("aaa");
//        System.out.println(u);


    }
}
