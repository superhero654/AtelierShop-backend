package com.su.ateliershop.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private String address;
}
