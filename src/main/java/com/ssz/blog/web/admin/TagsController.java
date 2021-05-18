package com.ssz.blog.web.admin;

import com.ssz.blog.pojo.Tags;
import com.ssz.blog.service.TagsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author sushuaizhen
 * @date 2020/8/1
 */
@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    private TagsServiceImpl tagsService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = "id" , direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){
        model.addAttribute("page", tagsService.ListTags(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tags", new Tags());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("tags" , tagsService.getTags(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tags tags, BindingResult result, RedirectAttributes redirectAttributes){
        Tags tags1 = tagsService.getTagsByName(tags.getName());
        if (tags1 != null){
            result.rejectValue("name", "nameError", "不能添加重复分类");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tags tags2 = tagsService.saveTags(tags);
        if(tags2 == null)
        {
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else
            redirectAttributes.addFlashAttribute("message", "新增成功");
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tags tags, BindingResult result,
                           @PathVariable Long id, RedirectAttributes redirectAttributes){
        Tags tags1 = tagsService.getTagsByName(tags.getName());
        if (tags1 != null){
            result.rejectValue("name", "nameError", "不能添加重复标签");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tags tags2 = tagsService.updateTags(id,tags);
        if(tags2 == null)
        {
            redirectAttributes.addFlashAttribute("message","修改失败");
        }else{
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }

        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){

        tagsService.deleteTags(id);
            redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
