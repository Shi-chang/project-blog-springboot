package com.springboot.blog.security;

import com.springboot.blog.payload.CategoryDto;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
}
