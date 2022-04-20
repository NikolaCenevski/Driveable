package com.wp.driveable.driveableapi.repository;

import com.wp.driveable.driveableapi.entity.Post;
import com.wp.driveable.driveableapi.entity.ReportPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportPostRepository extends JpaRepository<ReportPost,Long> {
    public ReportPost findByPost(Post post);
}
