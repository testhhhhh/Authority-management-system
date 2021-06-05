package com.lear.bean;

import lombok.*;

import java.util.List;

/**
 * 与数据库中的users对应
 */
@Data
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode//重写哈希code
public class UserInfo {
    private String username;
    private String email;
    private Role role;

    public UserInfo(String username, String email) {

        this.username = username;
        this.email = email;

    }



}
