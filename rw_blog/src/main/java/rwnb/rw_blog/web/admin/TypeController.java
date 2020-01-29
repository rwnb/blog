package rwnb.rw_blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rwnb.rw_blog.entity.Type;
import rwnb.rw_blog.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model)
    {
        model.addAttribute("page",  typeService.listType(pageable));
        return"admin/types";
    }
    @RequestMapping("/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/addTypes";
    }
    @PostMapping("/postType/{id}")
    public String postType(Type type, @PathVariable Long id,RedirectAttributes attributes){
        Type t=null;
        if(id==null){
            t=typeService.saveType(type);
        }else {
            t=typeService.updateType(id,type);
        }

        if(t==null){
            //给提示
            attributes.addFlashAttribute("message","操作失败!");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/types";
    }
    @PostMapping("/postType")
    public String postType_01(Type type,RedirectAttributes attributes){
        Type t=typeService.saveType(type);
        if(t==null){
            //给提示
            attributes.addFlashAttribute("message","操作失败!");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！！");
        return  "redirect:/admin/types";
    }
}
