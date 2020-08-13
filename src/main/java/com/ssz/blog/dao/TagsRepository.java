package com.ssz.blog.dao;

import com.ssz.blog.po.Tags;
import com.ssz.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/1
 */
public interface TagsRepository extends JpaRepository<Tags, Long> {

    Tags findByName(String name);

    @Query("select t from Tags t")
    List<Tags> findTop(Pageable pageable);
}
