package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.Response.ReportPostResponse;
import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.service.ReportPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {
    private final ReportPostService reportPostService;

    public ModeratorController(ReportPostService reportPostService) {
        this.reportPostService = reportPostService;
    }

    @GetMapping("/posts")
    public List<PostResponse> getAllPosts() {
        return reportPostService.getAllPosts();
    }

    @GetMapping("/post/{id}")
    public ReportPostResponse getReportPostById(@PathVariable long id) {
        return reportPostService.getReportPostById(id);
    }
    @GetMapping("/post/{id}/approve")
    public void approvePost(@PathVariable long id)
    {
        reportPostService.approvePost(id);
    }
    @GetMapping("/post/{id}/delete")
    public void deletePost(@PathVariable long id)
    {
        reportPostService.deletePost(id);
    }


}
