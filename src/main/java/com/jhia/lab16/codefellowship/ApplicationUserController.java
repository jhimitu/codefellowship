package com.jhia.lab16.codefellowship;

import javafx.geometry.Pos;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public String getAllUsers(Model m) {
        Iterable<ApplicationUser> users = applicationUserRepository.findAll();
        System.out.println("USERS: " + users);

        m.addAttribute("users", users);
        return "users";
    }

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
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            newUser,
            null, new ArrayList<>()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable long id, Model m, Principal p) {
        ApplicationUser user = applicationUserRepository.findById(id).get();
        List<Post> posts = user.posts;

        m.addAttribute("posts", posts);
        m.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/users/{id}")
    public RedirectView followUser(@PathVariable long id, Principal p) {
        ApplicationUser currentUser = applicationUserRepository.getByUsername(p.getName());
        ApplicationUser otherUser = applicationUserRepository.findById(id).get();

        currentUser.following.add(otherUser);
        otherUser.followers.add(currentUser);

        applicationUserRepository.save(currentUser);
        applicationUserRepository.save(otherUser);
        return new RedirectView("/users");
    }

    @GetMapping("/feed")
    public String getFeed(Model m, Principal p) {
        ApplicationUser user = applicationUserRepository.getByUsername(p.getName());
        Set<ApplicationUser> following = user.following;

        m.addAttribute("following", following);
        return "feed";
    }

    @GetMapping("/myprofile")
    public String getUsersProfile(Model m, Principal p) {
        ApplicationUser user = applicationUserRepository.getByUsername(p.getName());
        List<Post> posts = user.posts;

        m.addAttribute("posts", posts);
        m.addAttribute("user", user);
        return "usersprofile";
    }

    @PostMapping("/myprofile")
    public RedirectView createPost(Principal p, String body) {
        ApplicationUser user = applicationUserRepository.getByUsername(p.getName());
        Post post = new Post(body, LocalDate.now(), user);
        postRepository.save(post);

        return new RedirectView("/myprofile");
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
