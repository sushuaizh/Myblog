package com.ssz.blog.web.admin;

import com.ssz.blog.pojo.Blog;
import com.ssz.blog.pojo.User;
import com.ssz.blog.service.BlogService;
import com.ssz.blog.service.BlogServiceImpl;
import com.ssz.blog.service.TagsService;
import com.ssz.blog.service.TypeService;
import com.ssz.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author sushuaizhen
 * @date 2020/7/30
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String LIST = "admin/blogs";
    private static final String INPUT = "admin/blogs/input";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagsService tagsService;

    //首页博客跳转
    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                       BlogQuery blog, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page" ,blogService.listBlogByUser(blog,pageable,user));
        return "admin/blogs";
    }

    //搜索
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page" ,blogService.listBlog(blog,pageable));
        return "admin/blogs :: bloglist";
    }


    //新增
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTags(model);
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    private void setTypeAndTags(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags",tagsService.listTags());
    }

    //编辑
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id , Model model){
        setTypeAndTags(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blogService.getBlog(id));
        return "admin/blogs-input";
    }

    //新增和编辑里的发布和保存
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagsService.listTags(blog.getIds()));
        Blog blog1;
        if(blog.getId() == null){
            blog1 = blogService.saveBlog(blog);
        }else{
            blog1 = blogService.updateBlog(blog.getId(),blog);
        }
        if(blog1 == null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
         blogService.deleteBlog(id);
         attributes.addFlashAttribute("message", "删除成功");
         return REDIRECT_LIST;
    }
}
