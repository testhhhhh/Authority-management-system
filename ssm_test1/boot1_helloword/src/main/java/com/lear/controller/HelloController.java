package com.lear.controller;
import com.lear.bean.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 *@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
 * 写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
 * 注意：在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，
 * 他的效果等同于通过response对象输出指定格式的数据。(这个注解也可以写在方法上，但
 * 也可以写在类名上，与Controller注解一起使用就等同于@RestController注解
 */
//@ResponseBody
//@Controller
@Slf4j
@RestController//代表方法上返回的字符串是返回给浏览器的而不是跳转至某个页面
public class HelloController {
    @Autowired
    private Car car;

    @RequestMapping("/hello2")
    public String handle01(@RequestParam(name = "name")String name) {
        log.info("请求进来了....");
        return "Hello,Spring Boot 2   绝绝子"+name;
    }

    @RequestMapping("/car")
    public Car car(){
        return car;
    }

}
