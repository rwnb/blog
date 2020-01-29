package rwnb.rw_blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rwnb.rw_blog.entity.Tag;

import java.util.List;

public interface TagsService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTags();

    List<Tag> listTagTop(Integer size);

    Tag updateTag(Long id, Tag tag);

    Tag getTagByName(String name);

    void deleteTag(Long id);
    List<Tag> listTag(String ids);
}
