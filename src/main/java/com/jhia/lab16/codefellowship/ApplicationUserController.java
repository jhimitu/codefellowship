package com.jhia.lab16.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users")
    public RedirectView createUser(
            String username,
            String password,
            String firstName,
            String lastName,
            String dateOfBirth,
            String bio
    ) {
        ApplicationUser newUser = new ApplicationUser(
            username,
            bCryptPasswordEncoder.encode(password),
            firstName,
            lastName,
            dateOfBirth,
            bio
        );

        applicationUserRepository.save(newUser);
        return new RedirectView("/");
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

}
