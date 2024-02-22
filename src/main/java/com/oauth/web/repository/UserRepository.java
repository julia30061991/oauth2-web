package com.oauth.web.repository;

import com.oauth.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {

    boolean existsUserByEmail(String email);

    User findByUserId(int id);

    @Override
    List<User> findAll();

    @Override
    <S extends User> S saveAndFlush(S entity);

    void deleteByUserId(int id);
}