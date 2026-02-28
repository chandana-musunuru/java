package com.example.ioc;

// Step 1: Create a service WITHOUT Spring
class EmailService {
    public void sendEmail(String message) {
        System.out.println("Sending email: " + message);
    }
}

// Step 2: UserService creates its own dependency
class UserService {
    private EmailService emailService;
    
    // YOU create the object yourself
    public UserService() {
        this.emailService = new EmailService(); // YOU control object creation
    }
    
    public void registerUser(String userName) {
        System.out.println("Registering user: " + userName);
        emailService.sendEmail("Welcome " + userName);
    }
}

public class WithoutIoc {
    public static void main(String[] args) {
        // YOU create objects manually
        UserService userService = new UserService();
        userService.registerUser("John");
        
        // Problem: UserService is TIGHTLY COUPLED to EmailService
        // Can't easily swap EmailService with SmsService
    }
}