package rwnb.rw_blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rwnb.rw_blog.NotFoundException;
import rwnb.rw_blog.dao.TagsRepository;
import rwnb.rw_blog.entity.Tag;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsRepository tagsRepository;
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagsRepository.save(tag);
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagsRepository.getOne(id);
    }
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public List<Tag> listTags() {
        return tagsRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort.Order order=new Sort.Order(Sort.Direction.DESC, "blogs.size");
        Pageable pageable= PageRequest.of(0,size,Sort.by(order));
        return tagsRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t=tagsRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }else {
            BeanUtils.copyProperties(tag,t);
        }
        return tagsRepository.save(t);
    }
    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagsRepository.findByName(name);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagsRepository.findAllById(covertToList(ids));
    }
    private  List<Long> covertToList(String ids){
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String [] idarray=ids.split(",");
            for(int i=0;i<idarray.length;i++)
            {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
