package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.Response.Response;
import com.wp.driveable.driveableapi.exceptions.NotFoundException;
import com.wp.driveable.driveableapi.service.ReportPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Response> getReportPostById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(reportPostService.getReportPostById(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/post/{id}/approve")
    public ResponseEntity<MessageResponse> approvePost(@PathVariable long id) {
        try {
            return ResponseEntity.ok(reportPostService.approvePost(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/post/{id}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable long id) {
        try {
            return ResponseEntity.ok(reportPostService.deletePost(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
