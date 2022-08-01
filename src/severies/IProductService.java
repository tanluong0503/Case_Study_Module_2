package severies;

import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    void add(Product newProduct);

    void update(Product newProduct);

    Product findById(long id);

    boolean exists(long id);

    boolean existsById(long id);

    void deleteById(long id);

    List<Product> sortASC();

    List<Product> sortDESC();
}
