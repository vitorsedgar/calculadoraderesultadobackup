package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.entity.CategoryEntity;
import com.br.ages.calculadoraback.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity saveCategory(String categoryName) {
        CategoryEntity entity = CategoryEntity.builder()
                .name(categoryName)
                .build();
        return categoryRepository.save(entity);
    }

    public CategoryEntity getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
