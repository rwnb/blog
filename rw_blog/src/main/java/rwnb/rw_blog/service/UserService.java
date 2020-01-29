package rwnb.rw_blog.service;

import rwnb.rw_blog.entity.User;

public interface UserService {
    User checkUser(String username,String password);
}
