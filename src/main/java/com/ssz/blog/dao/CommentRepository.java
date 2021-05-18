package com.ssz.blog.dao;

import com.ssz.blog.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/9
 */
public interface CommentRepository extends JpaRepository<Comment ,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
