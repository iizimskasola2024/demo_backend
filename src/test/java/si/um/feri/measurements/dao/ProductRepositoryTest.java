package si.um.feri.measurements.dao;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import si.um.feri.measurements.vao.Product;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductRepositoryTest {

    @Inject
    ProductRepository productRepository;

    @Inject
    MeasurementRepository measurementRepository;

    private Product product;

    @BeforeEach
    @Transactional
    void beforeEach() {
        measurementRepository.deleteAll();
        productRepository.deleteAll();
        product = new Product();
        product.setName("Test product");
        product.setMaxMeasure(25.0);
        product.setMinMeasure(-12.5);
        productRepository.persist(product);
    }

    @Test
    @Transactional
    void addAnotherProductTest() {
        assertEquals(1, productRepository.count());
        assertEquals("Test product", product.getName());
        assertEquals(25.0, product.getMaxMeasure(), 0.0001);
        assertEquals(-12.5, product.getMinMeasure(), 0.0001);

        Product p = new Product();
        p.setName("Another product");
        productRepository.persist(p);

        assertEquals(2, productRepository.count());

        Product foundProduct = productRepository.findById(p.getId());
        assertNotNull(foundProduct);
        assertEquals("Another product", foundProduct.getName());
        assertEquals(0, foundProduct.getMaxMeasure(), 0.0001);
        assertEquals(0, foundProduct.getMinMeasure(), 0.0001);
    }

    @Test
    @Transactional
    void deleteProductTest() {
        assertEquals(1, productRepository.count());
        productRepository.deleteById(product.getId());
        assertEquals(0, productRepository.count());
    }
}
