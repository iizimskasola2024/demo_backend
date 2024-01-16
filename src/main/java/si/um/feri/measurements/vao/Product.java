package si.um.feri.measurements.vao;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.feri.measurements.dto.ProductDTO;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Product extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public String name;
    public LocalDateTime created = LocalDateTime.now();
    public double maxMeasure;
    public double minMeasure;

    public Product(ProductDTO dto) {
        this.name = dto.name();
        this.maxMeasure = dto.maxMeasure();
        this.minMeasure = dto.minMeasure();
    }

    public void updateFrom(ProductDTO dto) {
        this.name = dto.name();
        this.maxMeasure = dto.maxMeasure();
        this.minMeasure = dto.minMeasure();
    }

    public ProductDTO toDto() {
        return new ProductDTO(id, name, maxMeasure, minMeasure);
    }
}
