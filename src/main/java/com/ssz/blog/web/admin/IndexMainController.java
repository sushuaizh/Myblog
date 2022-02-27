package com.ssz.blog.web.admin;

import com.ssz.blog.pojo.Blog;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sushuaizhen
 * @date 2021/5/24
 */
@Controller
@RequestMapping("/admin")
public class IndexMainController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagsService tagsService;

    @GetMapping("/index_main")
    public String index(@PageableDefault(size = 8, sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("blogs" , blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTop(6));
        model.addAttribute("tags", tagsService.listTop(10));
        model.addAttribute("recommends",blogService.listRecommendTop(6));
        return "admin/index_main";
    }

    @GetMapping("/about")
    public String about(){

        return "admin/about";
    }
}
