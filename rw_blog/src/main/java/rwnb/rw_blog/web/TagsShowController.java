package rwnb.rw_blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rwnb.rw_blog.entity.Tag;
import rwnb.rw_blog.service.BlogService;
import rwnb.rw_blog.service.TagsService;
import rwnb.rw_blog.vo.BlogQuery;

import java.util.List;

@Controller
public class TagsShowController {
    @Autowired
    private TagsService tagsService;
    @Autowired
    private BlogService blogService;
   @GetMapping("/tag/{id}")
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model,
   @PathVariable Long id){
       List<Tag> tags=tagsService.listTagTop(100);
       if(id==-1){
           id=tags.get(0).getId();

       }
       model.addAttribute("tags",tags);
       model.addAttribute("page",blogService.listBlog(id,pageable));
       model.addAttribute("activeTypeId",id);
        return "tags";
    }
}
