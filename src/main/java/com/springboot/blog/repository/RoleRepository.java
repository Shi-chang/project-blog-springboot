package com.springboot.blog.repository;

import com.springboot.blog.entity.Role;

import java.util.Optional;

public interface RoleRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
