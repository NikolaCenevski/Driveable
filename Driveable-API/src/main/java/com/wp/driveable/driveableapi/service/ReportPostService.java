package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.Response.PostResponse;
import com.wp.driveable.driveableapi.dto.Response.ReasonResponse;
import com.wp.driveable.driveableapi.dto.Response.ReportPostResponse;
import com.wp.driveable.driveableapi.entity.*;
import com.wp.driveable.driveableapi.exceptions.NotFoundException;
import com.wp.driveable.driveableapi.repository.PostRepository;
import com.wp.driveable.driveableapi.repository.ReasonRepository;
import com.wp.driveable.driveableapi.repository.ReportPostRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public ReportPostResponse getReportPostById(long id) throws NotFoundException {
        Post post=postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report post not found"));
        ReportPost reportPost = reportPostRepository.findByPost(post);
        if (reportPost==null)
        {
            throw new NotFoundException("Report post not found");
        }
        return mapToReportPostResponse(reportPost);
    }

    public ReportPostResponse mapToReportPostResponse(ReportPost reportPost) {
        ReportPostResponse reportPostResponse = new ReportPostResponse();
        Post post = reportPost.getPost();
        reportPostResponse.setId(reportPost.getId());
        reportPostResponse.setPostId(post.getId());
        reportPostResponse.setCar(post.getCar());
        reportPostResponse.setColor(post.getColor());
        reportPostResponse.setMileage(post.getMileage());
        reportPostResponse.setDate(post.getDate());
        reportPostResponse.setDescription(post.getDescription());
        reportPostResponse.setHorsepower(post.getHorsepower());
        reportPostResponse.setTitle(post.getTitle());
        reportPostResponse.setCarType(post.getCarTypes().stream().map(CarType::getType).collect(Collectors.toList()));
        reportPostResponse.setPrice(post.getPrice());
        reportPostResponse.setIsNew(post.getIsNew());
        reportPostResponse.setManufacturingYear(post.getManufacturingYear());
        reportPostResponse.setName(post.getCreator().getName());
        reportPostResponse.setSurname(post.getCreator().getSurname());
        reportPostResponse.setPhoneNumber(post.getCreator().getPhoneNumber());
        reportPostResponse.setNumOfImages(post.getImages().size());

        reportPostResponse.setReasons(reportPost.getReasons().stream().
                map(this::mapToReasonResponse)
                .collect(Collectors.toList()));

        return reportPostResponse;
    }

    public ReasonResponse mapToReasonResponse(Reason reason) {
        ReasonResponse reasonResponse = new ReasonResponse();
        reasonResponse.setDescription(reason.getDescription());
        reasonResponse.setName(reason.getUser().getName());
        reasonResponse.setSurname(reason.getUser().getSurname());
        return reasonResponse;
    }

    public MessageResponse reportPost(long id, String description) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        ReportPost reportPost = reportPostRepository.findByPost(post);
        Reason reason = new Reason();
        reason.setDescription(description);
        reason.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reasonRepository.save(reason);
        if (reportPost == null) {
            reportPost = new ReportPost();
            reportPost.setPost(post);
        }
        reportPost.getReasons().add(reason);
        reportPostRepository.save(reportPost);
        return new MessageResponse("Post reported");
    }

    public List<PostResponse> getAllPosts() {
        return reportPostRepository.findAll().stream()
                .map(post -> postService.mapToPostResponse(post.getPost()))
                .collect(Collectors.toList());
    }

    public MessageResponse approvePost(long id) throws NotFoundException {
        ReportPost reportPost = reportPostRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("Post not found"));

        reportPostRepository.delete(reportPost);
        return new MessageResponse("Post approved");
    }

    public MessageResponse deletePost(long id) throws NotFoundException {
        ReportPost reportPost = reportPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Post post = reportPost.getPost();
        reportPostRepository.delete(reportPost);
        postRepository.delete(post);
        return new MessageResponse("Post deleted");
    }
}
