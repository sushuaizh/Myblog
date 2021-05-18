package com.ssz.blog.web;

import com.ssz.blog.pojo.Type;
import com.ssz.blog.service.BlogService;
import com.ssz.blog.service.TypeService;
import com.ssz.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author sushuaizhen
 * @date 2020/8/16
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Type> typeList = typeService.listTop(10000);
        if(id == -1){
            id = typeList.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",typeList);
        model.addAttribute("blogs",blogService.listBlog(blogQuery,pageable));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
