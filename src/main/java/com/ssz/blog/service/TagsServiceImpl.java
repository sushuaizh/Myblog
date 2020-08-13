package com.ssz.blog.service;

import com.ssz.blog.NotFoundException;
import com.ssz.blog.dao.TagsRepository;
import com.ssz.blog.po.Tags;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/1
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Transactional
    @Override
    public Tags saveTags(Tags tags) {
        return  tagsRepository.save(tags);
    }

    @Transactional
    @Override
    public Tags getTags(Long id) {
        return tagsRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tags updateTags(Long id, Tags tags) {
        Tags tags1 = tagsRepository.getOne(id);
        if (tags1==null){
            throw  new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tags,tags1);
        return tagsRepository.save(tags1);
    }

    @Transactional
    @Override
    public void deleteTags(Long id) {
            tagsRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tags getTagsByName(String name) {
        return tagsRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tags> ListTags(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }

    @Override
    public List<Tags> listTags() {
        return tagsRepository.findAll();
    }

    @Override
    public List<Tags> listTags(String ids) {
        return tagsRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tags> listTop(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagsRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids){
        ArrayList<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids!=null){
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
