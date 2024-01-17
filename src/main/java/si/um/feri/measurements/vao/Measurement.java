package si.um.feri.measurements.vao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.feri.measurements.dto.MeasurementDTO;
import si.um.feri.measurements.dto.post.PostMeasurementDTO;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "measvalue")
    private double value;

    private LocalDateTime created = LocalDateTime.now();

    private boolean isOk = true;

    @ManyToOne
    private Product product;

    public Measurement(PostMeasurementDTO m, Product p) {
        setValue(m.avgTemperature());
        setProduct(p);
    }

    public MeasurementDTO toDto() {
        return new MeasurementDTO(id, MeasurementDTO.JSON_DATE_FORMAT.format(created), (product != null) ? product.getId() : (long) -1, value, isOk);
    }

}
