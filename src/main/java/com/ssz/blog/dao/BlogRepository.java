package com.ssz.blog.dao;

import com.ssz.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/2
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select t from Blog t where t.recommend=true")
    List<Blog> findRecommendTop(Pageable pageable);

//    一般的sql语句: select * from t_blogs where title like '%百分号里面才是要查询的字符串，但是下面的语句不会自动加百分号，所以在传值的时候要先处理 %'
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);
}
