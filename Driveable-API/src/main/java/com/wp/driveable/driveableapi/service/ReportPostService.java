package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.entity.Reason;
import com.wp.driveable.driveableapi.entity.ReportPost;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.repository.PostRepository;
import com.wp.driveable.driveableapi.repository.ReasonRepository;
import com.wp.driveable.driveableapi.repository.ReportPostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReportPostService {
    private final ReportPostRepository reportPostRepository;
    private final PostRepository postRepository;
    private final ReasonRepository reasonRepository;
    public ReportPostService(ReportPostRepository reportPostRepository, PostRepository postRepository, ReasonRepository reasonRepository) {
        this.reportPostRepository = reportPostRepository;
        this.postRepository = postRepository;
        this.reasonRepository = reasonRepository;
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
}
