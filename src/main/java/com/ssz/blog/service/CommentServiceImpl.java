package com.ssz.blog.service;

import com.ssz.blog.dao.CommentRepository;
import com.ssz.blog.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/9
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;



    @Override
    public List<Comment> listCommentById(Long blogId) {

        return repository.findByBlogId(blogId, Sort.by(Sort.Direction.DESC,"createTime"));
    }

//    点击回复，获取到回复对象comment，再通过该comment获取其父对象id，默认为-1，如果不为-1，就通过该id来设置其父对象,再设置时间，再保存到数据库
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(repository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return repository.save(comment);
    }
}
