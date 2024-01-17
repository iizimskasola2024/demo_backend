package si.um.feri.measurements.dao;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import si.um.feri.measurements.dto.post.PostMeasurementDTO;
import si.um.feri.measurements.vao.Measurement;
import si.um.feri.measurements.vao.Product;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class MeasurementRepositoryTest {

    @Inject
    MeasurementRepository measurementRepository;

    @Inject
    ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    @Transactional
    void setUp() {
        measurementRepository.deleteAll();
        productRepository.deleteAll();

        product1 = new Product();
        product1.setName("Test product 1");
        product1.setMinMeasure(-12.5);
        product1.setMaxMeasure(25.0);
        productRepository.persist(product1);

        product2 = new Product();
        product2.setName("Test product 2");
        product2.setMinMeasure(-12.5);
        product2.setMaxMeasure(25.0);
        productRepository.persist(product2);
    }

    @Test
    @Transactional
    void countByProductTest() {
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product1.getId(), 15.0), product1));
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product1.getId(), 15.0), product1));
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product2.getId(), 15.0), product2));
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product2.getId(), 15.0), product2));
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product2.getId(), 15.0), product2));

        assertEquals(2, measurementRepository.countByProduct(product1));
        assertEquals(3, measurementRepository.countByProduct(product2));
    }

    @Test
    @Transactional
    void findByCreatedGreaterThanTest() {
        LocalDateTime testDate = LocalDateTime.now().minusDays(1);

        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product1.getId(), 15.0), product1));
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product1.getId(), 15.0), product1));
        measurementRepository.persist(new Measurement(new PostMeasurementDTO(product2.getId(), 15.0), product2));

        List<Measurement> results = measurementRepository.findByCreatedGreaterThan(testDate);
        assertEquals(3, results.size());
    }
}