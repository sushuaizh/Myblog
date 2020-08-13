package com.ssz.blog.service;

import com.ssz.blog.po.Blog;
import com.ssz.blog.po.Type;
import com.ssz.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/2
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog>  listBlog(BlogQuery blog, Pageable pageable);

    Page<Blog>  listBlog(Pageable pageable);

    Page<Blog>  listSearchBlog(String query, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id , Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendTop(Integer size);

}
