package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.Response.Response;
import com.wp.driveable.driveableapi.dto.requests.PostRequest;
import com.wp.driveable.driveableapi.exceptions.NotFoundException;
import com.wp.driveable.driveableapi.service.PostService;
import com.wp.driveable.driveableapi.service.ReportPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final ReportPostService reportPostService;

    public PostController(PostService postService, ReportPostService reportPostService) {
        this.postService = postService;
        this.reportPostService = reportPostService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllPostsByUser(@PathVariable long id) {
        try {
            return ResponseEntity.ok(postService.getAllPostsByUser(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPostById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(postService.getPostById(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<MessageResponse> reportPost(@PathVariable long id, @RequestBody String description) {
        try {
            return ResponseEntity.ok(reportPostService.reportPost(id, description));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
