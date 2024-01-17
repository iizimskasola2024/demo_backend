package si.um.feri.measurements.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.feri.measurements.vao.Measurement;
import si.um.feri.measurements.vao.Product;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MeasurementRepository implements PanacheRepository<Measurement> {

    public Long countByProduct(Product p) {
        return find("product", p).count();
    }

    public List<Measurement> findByCreatedGreaterThan(LocalDateTime created) {
        return list("created > ?1", created);
    }
}
