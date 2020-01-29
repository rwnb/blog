package rwnb.rw_blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rwnb.rw_blog.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
