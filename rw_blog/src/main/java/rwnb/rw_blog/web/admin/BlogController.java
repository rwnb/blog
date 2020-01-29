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
import rwnb.rw_blog.entity.Blog;
import rwnb.rw_blog.entity.Type;
import rwnb.rw_blog.entity.User;
import rwnb.rw_blog.service.BlogService;
import rwnb.rw_blog.service.TagsService;
import rwnb.rw_blog.service.TypeService;
import rwnb.rw_blog.vo.BlogQuery;

import javax.servlet.http.HttpSession;;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagsService tagsService;
    @GetMapping("/admin")
    public String list(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "/admin/blog";
    }
    @PostMapping("/admin/search")
    public String search(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "/admin/blog :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagsService.listTags());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }
    @GetMapping("/blogs/{id}/input")
    public String editinput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagsService.listTags());
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/editBlog";
    }
    @PostMapping("/postBlog")
    public String postBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType((long)3));
        blog.setTags(tagsService.listTag(blog.getTagIds()));
         Blog b;
         if(blog.getId()==null){
             b=blogService.saveBlog(blog);
         }else {
             b=blogService.updateBlog(blog.getId(),blog);
         }
        if(b==null){
            //给提示
            attributes.addFlashAttribute("message","操作失败!");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/admin";
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/admin";
    }
}
