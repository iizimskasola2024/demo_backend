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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private LocalDateTime created = LocalDateTime.now();
    private double maxMeasure;
    private double minMeasure;

    public Product(ProductDTO dto) {
        setName(dto.name());
        setMaxMeasure(dto.maxMeasure());
        setMinMeasure(dto.minMeasure());
    }

    public void updateFrom(ProductDTO dto) {
        setName(dto.name());
        setMaxMeasure(dto.maxMeasure());
        setMinMeasure(dto.minMeasure());
    }

    public ProductDTO toDto() {
        return new ProductDTO(id, name, maxMeasure, minMeasure);
    }
}
