package com.UdemyDemo.Udemy.Project.payload.response;

import lombok.Data;
import lombok.Getter;

@Data
public class InvalidLoginResponse {
    private String username;
    private String password;
    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }
}
