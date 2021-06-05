package com.lear.bean;

import lombok.*;

import java.util.List;
@Data//getset方法
@ToString//tostring方法，编译这个类的时候自动生成这个方法
//@AllArgsConstructor//全参构造器
@NoArgsConstructor//无参构造器
@EqualsAndHashCode//重写哈希code
public class Role {
    private String id;
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }



}
