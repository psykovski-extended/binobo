package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void delete(Category category);
}
