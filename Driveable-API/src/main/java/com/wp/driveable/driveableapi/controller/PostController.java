package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.requests.PostRequest;
import com.wp.driveable.driveableapi.service.PostService;
import com.wp.driveable.driveableapi.service.ReportPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController{
    private final PostService postService;
    private final ReportPostService reportPostService;

    public PostController(PostService postService, ReportPostService reportPostService) {
        this.postService = postService;
        this.reportPostService = reportPostService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts()
    {
        return postService.getAllPosts();
    }
    @GetMapping("/user/{id}")
    public List<PostResponse> getAllPostsByUser(@PathVariable long id)
    {
        return postService.getAllPostsByUser(id);
    }
    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable long id)
    {
        return postService.getPostById(id);
    }
    @PostMapping("/create")
    public void createPost(@RequestBody PostRequest postRequest)
    {
        postService.createPost(postRequest);
    }
    @PostMapping("/{id}/report")
    public void reportPost(@PathVariable long id,@RequestBody String description)
    {
       reportPostService.reportPost(id,description);
    }
}
