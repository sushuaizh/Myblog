package com.ssz.blog.service;

import com.ssz.blog.po.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/1
 */
public interface TagsService {

    Tags saveTags(Tags tags);

    Object getTags(Long id);

    Tags updateTags(Long id,Tags tags);

    void deleteTags(Long id);

    Tags getTagsByName(String name);

    Page<Tags>  ListTags(Pageable pageable);

    List<Tags>  listTags();

    List<Tags> listTags(String ids);

    List<Tags> listTop(Integer size);
}
