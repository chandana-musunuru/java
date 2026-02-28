package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
class SpringEmailService {
    public void sendEmail(String message) {
        System.out.println("Spring Email: " + message);
    }
}

// Step 2: Spring injects the dependency
@Service
class INVERSIONOFCONTROL {
    private final SpringEmailService emailService;
    
    // Spring AUTOMATICALLY creates and injects EmailService
    @Autowired
    public INVERSIONOFCONTROL(SpringEmailService emailService) {
        this.emailService = emailService; // Spring gives it to you!
    }
    
    public void registerUser(String userName) {
        System.out.println("Registering user: " + userName);
        emailService.sendEmail("Welcome " + userName);
    }
}