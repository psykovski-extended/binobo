package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

}
