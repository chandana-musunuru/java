package com.aop.spring.service;

import org.springframework.stereotype.Service;
import com.aop.spring.annotation.LogExecution;
@Service
public class UserService {

    @LogExecution
    public String getUserInfo(String username) {
        System.out.println("Executing getUserInfo for: " + username);
        return "User Info for " + username;
    }

    public void performAction() {
        System.out.println("Performing an important action...");
    }
}
