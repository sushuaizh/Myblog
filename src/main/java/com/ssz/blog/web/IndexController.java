package com.ssz.blog.web;

import com.ssz.blog.NotFoundException;
import com.ssz.blog.service.BlogService;
import com.ssz.blog.service.TagsService;
import com.ssz.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sushuaizhen
 * @date 2020/7/23
 */
@Controller
public class IndexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagsService tagsService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = "updateTime",direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
    model.addAttribute("blogs" , blogService.listBlog(pageable));
    model.addAttribute("types",typeService.listTop(6));
    model.addAttribute("tags", tagsService.listTop(10));
    model.addAttribute("recommends",blogService.listRecommendTop(6));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = "updateTime",direction = Sort.Direction.DESC)Pageable pageable,
                         @RequestParam String query , Model model){
        model.addAttribute("blogs",blogService.listSearchBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        System.out.println("=======index=======");
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlog(Model model){
        model.addAttribute(blogService.listRecommendTop(3));
        return "_fragments :: newBlogList";
    }
}
