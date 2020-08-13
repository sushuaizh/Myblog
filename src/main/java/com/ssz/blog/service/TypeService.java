package com.ssz.blog.service;

import com.ssz.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/7/30
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTop(Integer size);

    Type updateType(Long id,Type type);

    void deleteType(Long id);
}
