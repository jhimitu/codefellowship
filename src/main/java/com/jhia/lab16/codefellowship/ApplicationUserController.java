package com.jhia.lab16.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
            String dateOfBirthString,
            String bio
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfBirth;

        dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
        ApplicationUser newUser = new ApplicationUser(
            username,
            bCryptPasswordEncoder.encode(password),
            firstName,
            lastName,
            dateOfBirth,
            bio
        );

        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable long id, Model m, Principal p) {
        ApplicationUser user = applicationUserRepository.findById(id).get();
        m.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/myprofile")
    public String getUsersProfile(Model m, Principal p) {
        ApplicationUser user = applicationUserRepository.getByUsername(p.getName());
        m.addAttribute("user", user);
        return "usersprofile";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

}
