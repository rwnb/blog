package rwnb.rw_blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rwnb.rw_blog.entity.Tag;
import rwnb.rw_blog.entity.Type;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type updateType(Long id,Type type);

    Type getTypeByName(String name);

    void deleteType(Long id);
}
