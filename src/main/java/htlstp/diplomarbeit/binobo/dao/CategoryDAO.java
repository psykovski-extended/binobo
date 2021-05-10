package htlstp.diplomarbeit.binobo.dao;

import htlstp.diplomarbeit.binobo.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryDAO {//extends CrudRepository<Category, Long> {
    List<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void delete(Category category);

}
