package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.Response.ReasonResponse;
import com.wp.driveable.driveableapi.dto.Response.ReportPostResponse;
import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.entity.Reason;
import com.wp.driveable.driveableapi.entity.ReportPost;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.repository.PostRepository;
import com.wp.driveable.driveableapi.repository.ReasonRepository;
import com.wp.driveable.driveableapi.repository.ReportPostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportPostService {
    private final ReportPostRepository reportPostRepository;
    private final PostRepository postRepository;
    private final ReasonRepository reasonRepository;
    private final PostService postService;
    public ReportPostService(ReportPostRepository reportPostRepository, PostRepository postRepository, ReasonRepository reasonRepository, PostService postService) {
        this.reportPostRepository = reportPostRepository;
        this.postRepository = postRepository;
        this.reasonRepository = reasonRepository;
        this.postService = postService;
    }

    public  ReportPostResponse getReportPostById(long id) {

        ReportPost reportPost=reportPostRepository.getById(id);
        return mapToReportPostResponse(reportPost);
    }
    public ReportPostResponse mapToReportPostResponse(ReportPost reportPost)
    {
        ReportPostResponse reportPostResponse=new ReportPostResponse();
        Post post=reportPost.getPost();
        reportPostResponse.setId(reportPost.getId());
        reportPostResponse.setCar(post.getCar());
        reportPostResponse.setColor(post.getColor());
        reportPostResponse.setDescription(post.getDescription());
        reportPostResponse.setHorsepower(post.getHorsepower());
        reportPostResponse.setTitle(post.getTitle());
        reportPostResponse.setCarType(post.getCarType());
        reportPostResponse.setPrice(post.getPrice());
        reportPostResponse.setIsNew(post.getIsNew());
        reportPostResponse.setManufacturingYear(post.getManufacturingYear());
        reportPostResponse.setName(post.getCreator().getName());
        reportPostResponse.setSurname(post.getCreator().getSurname());
        reportPostResponse.setPhoneNumber(post.getCreator().getPhoneNumber());
        reportPostResponse.setNumOfImages(post.getImages().size());
        reportPostResponse.setReasons(reportPost.getReasons().stream().map(this::mapToReasonResponse).collect(Collectors.toList()));
        return reportPostResponse;
    }
    public ReasonResponse mapToReasonResponse(Reason reason)
    {
        ReasonResponse reasonResponse=new ReasonResponse();
        reasonResponse.setDescription(reason.getDescription());
        reasonResponse.setName(reason.getUser().getName());
        reasonResponse.setSurname(reason.getUser().getSurname());
        return reasonResponse;
    }

    public void reportPost(long id, String description) {
        Post post=postRepository.getById(id);
        ReportPost reportPost=reportPostRepository.findByPost(post);
        Reason reason=new Reason();
        reason.setDescription(description);
        reason.setUser((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reasonRepository.save(reason);
        if (reportPost==null)
        {
            reportPost=new ReportPost();
            reportPost.setPost(post);
        }
        reportPost.getReasons().add(reason);
        reportPostRepository.save(reportPost);
    }

    public List<PostResponse> getAllPosts() {
       return reportPostRepository.findAll().stream().map(post->postService.mapToPostResponse(post.getPost())).collect(Collectors.toList());
    }

    public void approvePost(long id) {
        ReportPost reportPost=reportPostRepository.getById(id);
        reportPostRepository.delete(reportPost);
    }
    public void deletePost(long id) {
        ReportPost reportPost=reportPostRepository.getById(id);
        Post post=reportPost.getPost();
        reportPostRepository.delete(reportPost);
        postRepository.delete(post);
    }
}
