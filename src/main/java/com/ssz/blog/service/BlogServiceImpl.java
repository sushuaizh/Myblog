package com.ssz.blog.service;

import com.ssz.blog.NotFoundException;
import com.ssz.blog.dao.BlogRepository;
import com.ssz.blog.dao.TypeRepository;
import com.ssz.blog.pojo.Blog;
import com.ssz.blog.pojo.Type;
import com.ssz.blog.util.MarkdownUtils;
import com.ssz.blog.util.MyBeanUtils;
import com.ssz.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author sushuaizhen
 * @date 2020/8/2
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository repository;

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Blog getBlog(Long id) {
        return repository.getOne(id);
    }


//    博客内容转html
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
      Blog blog =  repository.getOne(id);
      if(blog == null){
          throw new NotFoundException("博客不存在");
      }
      Blog blog1 = new Blog();
      BeanUtils.copyProperties(blog,blog1);
      String content = blog1.getContent();
      blog1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

      repository.upadateViews(id);
      return blog1;

    }

    @Override
    public Page<Blog> listBlog(BlogQuery blog, Pageable pageable) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listSearchBlog(String query, Pageable pageable) {
        return repository.findByQuery(query,pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = repository.findGroupYear();
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        for(String year :years){
            map.put(year,repository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return repository.count();
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }

        return repository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = repository.getOne(id);
        if (blog1 == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return repository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        repository.deleteById(id);
    }

    //推荐
    @Transactional
    @Override
    public List<Blog> listRecommendTop(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC,"updateTime"));
        return repository.findRecommendTop(pageable);
    }
}
