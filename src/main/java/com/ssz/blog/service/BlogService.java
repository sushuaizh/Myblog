package com.ssz.blog.service;

import com.ssz.blog.pojo.Blog;
import com.ssz.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author sushuaizhen
 * @date 2020/8/2
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog>  listBlog(BlogQuery blog, Pageable pageable);

    Page<Blog>  listBlog(Pageable pageable);

    Page<Blog>  listBlog(Long tagId,Pageable pageable);

    Page<Blog>  listSearchBlog(String query, Pageable pageable);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id , Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendTop(Integer size);



}
