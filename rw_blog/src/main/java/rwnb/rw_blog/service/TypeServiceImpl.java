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
import rwnb.rw_blog.dao.TypeRepository;
import rwnb.rw_blog.entity.Tag;
import rwnb.rw_blog.entity.Type;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
  private TypeRepository typeRepository;
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort.Order order=new Sort.Order(Sort.Direction.DESC, "blogs.size");
        Pageable pageable= PageRequest.of(0,size,Sort.by(order));
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
       Type t=typeRepository.getOne(id);
       if(t==null){
            throw new NotFoundException("不存在该类型");
       }else {
           BeanUtils.copyProperties(type,t);
       }
        return typeRepository.save(t);
    }

    @Override
    public Type getTypeByName(String name) {

        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
