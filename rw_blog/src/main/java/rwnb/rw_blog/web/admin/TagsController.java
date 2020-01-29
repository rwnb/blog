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
import rwnb.rw_blog.entity.Tag;
import rwnb.rw_blog.entity.Type;
import rwnb.rw_blog.service.TagsService;

@Controller
@RequestMapping("/admin")
public class TagsController {
    @Autowired
    private TagsService tagsService;
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model)
    {
        model.addAttribute("page",  tagsService.listTag(pageable));
        return"admin/tags";
    }
    @RequestMapping("/tagsInput")
    public String tagsInput(Model model){

      model.addAttribute("tags",new Tag());
    return "admin/addTags";
    }
    @PostMapping("/postTags/{id}")
    public String postType(Tag tag, @PathVariable Long id, RedirectAttributes attributes){
        Tag t=null;
        if(id==null){
            t=tagsService.saveTag(tag);
        }else {
            t=tagsService.updateTag(id,tag);
        }

        if(t==null){
            //给提示
            attributes.addFlashAttribute("message","操作失败!");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/tags";
    }
    @PostMapping("/postTags")
    public String postType_01(Tag tag,RedirectAttributes attributes){
        Tag t=tagsService.saveTag(tag);
        if(t==null){
            //给提示
            attributes.addFlashAttribute("message","操作失败!");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tags",tagsService.getTag(id));
        return "admin/tags-input";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagsService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功！！");
        return  "redirect:/admin/tags";
    }
}
