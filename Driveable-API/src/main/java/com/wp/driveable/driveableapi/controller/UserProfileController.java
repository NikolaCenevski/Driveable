package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.service.PostService;
import com.wp.driveable.driveableapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {
    private final UserService userService;
    private final PostService postService;
    public UserProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/edit/phoneNumber")
    public void editPhoneNumber(@RequestBody String number)
    {
        userService.editPhoneNumber(number);
    }
    @PostMapping("/edit/email")
    public void editEmail(@RequestBody String email)
    {
        userService.editMail(email);
    }
    @PostMapping("/edit/password")
    public void editPassword(@RequestBody String password)
    {
        userService.editPassword(password);
    }
    @GetMapping("/posts")
    public List<PostResponse> getAllPostsByUser()
    {
        return postService.getAllPostsByUser();
    }
    @PostMapping("/edit/price/{id}")
    public void editPassword(@PathVariable long id,@RequestBody int price)
    {
        postService.editPrice(price,id);
    }
}
