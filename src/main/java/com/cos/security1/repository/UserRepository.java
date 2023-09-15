package com.cos.security1.repository;

import com.cos.security1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 기본적인 CRUD 함수를 JpaRepository가 들고 있음.
// @Repository라는 애노테이션이 없어도 Ioc가 됨. -> JpaRepository를 상속했기 때문에

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
