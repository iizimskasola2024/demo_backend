package si.um.feri.measurements.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.feri.measurements.vao.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
}
