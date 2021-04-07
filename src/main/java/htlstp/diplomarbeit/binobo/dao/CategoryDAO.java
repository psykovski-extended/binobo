package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void delete(Category category);

}
