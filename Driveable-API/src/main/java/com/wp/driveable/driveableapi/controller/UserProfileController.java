package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.exceptions.BadRequestException;
import com.wp.driveable.driveableapi.exceptions.NotFoundException;
import com.wp.driveable.driveableapi.service.PostService;
import com.wp.driveable.driveableapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MessageResponse> editPhoneNumber(@RequestBody String number) {
        return ResponseEntity.ok(userService.editPhoneNumber(number));
    }

    @PostMapping("/edit/email")
    public ResponseEntity<MessageResponse> editEmail(@RequestBody String email) {
        return ResponseEntity.ok(userService.editMail(email));
    }

    @PostMapping("/edit/password")
    public ResponseEntity<MessageResponse> editPassword(@RequestBody String password) {
        return ResponseEntity.ok(userService.editPassword(password));
    }

    @GetMapping("/posts")
    public List<PostResponse> getAllPostsByUser() {
        return postService.getAllPostsByUser();
    }

    @PostMapping("/edit/price/{id}")
    public ResponseEntity<MessageResponse> editPrice(@PathVariable long id, @RequestBody int price) {
        try {
            return ResponseEntity.ok(postService.editPrice(price, id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
