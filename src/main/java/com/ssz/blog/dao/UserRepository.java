package com.ssz.blog.dao;

import com.ssz.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sushuaizhen
 * @date 2020/7/25
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

    User findByUsername(String name);
}
