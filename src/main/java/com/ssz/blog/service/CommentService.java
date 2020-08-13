package com.ssz.blog.service;

import com.ssz.blog.po.Comment;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/9
 */
public interface CommentService {

    List<Comment>  listCommentById(Long blogId);

    Comment saveComment(Comment comment);
}
