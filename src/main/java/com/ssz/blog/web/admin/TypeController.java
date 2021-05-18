package com.ssz.blog.web.admin;

import com.ssz.blog.pojo.Type;
import com.ssz.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
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
 * @date 2020/7/30
 */
@Service
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/type")
    public String type(@PageableDefault(size = 10 , sort = {"id"} , direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page" , typeService.listType(pageable));//model.addAttribute方法是用来向前端传数据的，通过el表达式${}可以访问
        return "admin/type";
    }

    @GetMapping("/type/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    @GetMapping("/type/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type" , typeService.getType(id));
        return "admin/type-input";
    }

    @PostMapping("/type")
    public String post(@Valid Type type, BindingResult result,  RedirectAttributes attributes){
        Type type2 = typeService.getTypeByName(type.getName());
        System.out.println(type2);
        if(type2 != null){
            result.rejectValue("name", "nameError", "不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }
      Type type1 =  typeService.saveType(type);
        if (type1 == null){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
      return "redirect:/admin/type";
    }

    @PostMapping("/type/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes){
        Type type2 = typeService.getTypeByName(type.getName());
        System.out.println(type2);
        if(type2 != null){
            result.rejectValue("name", "nameError", "不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }
        Type type1 =  typeService.updateType(id,type);
        if (type1 == null){
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/type";
    }

    @GetMapping("/type/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/type";
    }
}
