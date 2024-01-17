package si.um.feri.measurements.dao;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import si.um.feri.measurements.dto.MeasurementDTO;
import si.um.feri.measurements.dto.ProductDTO;
import si.um.feri.measurements.dto.post.PostMeasurementDTO;
import si.um.feri.measurements.dto.post.PostMeasurementResponseDTO;
import si.um.feri.measurements.rest.MeasurementHistoryController;
import si.um.feri.measurements.rest.MeasurementPostController;
import si.um.feri.measurements.rest.ProductController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class RestScenarioTest {

    @Inject
    ProductController productController;

    @Inject
    MeasurementPostController measurementPostController;

    @Inject
    MeasurementHistoryController measurementHistoryController;

    @Inject
    MeasurementRepository measurementRepository;

    private ProductDTO newProduct;

    @BeforeEach
    @Transactional
    void createProduct() {
        measurementRepository.deleteAll();

        Response response = productController.postProduct(
                new ProductDTO(0L, "Product", 22.0, -23.5)
        );
        assertNotNull(response);
        newProduct = (ProductDTO) response.getEntity();
    }

    @Test
    void checkProductExistence() {
        Response response = productController.getProductById(newProduct.id().intValue());
        assertNotNull(response);
        ProductDTO fromServer = (ProductDTO) response.getEntity();
        assertEquals(fromServer.name(), newProduct.name());
        assertEquals(-23.5, fromServer.minMeasure(), 0.0001);
    }

    @Test
    void newOKMeasurement() {
        Response response = measurementPostController.postProductMeasurement(
                new PostMeasurementDTO(newProduct.id(), 5.5)
        );
        assertNotNull(response);

        PostMeasurementResponseDTO postMeasurementResponse = response.readEntity(PostMeasurementResponseDTO.class);
        assertEquals("ok", postMeasurementResponse.result());
    }

    @Test
    void newMeasurementForFakeProduct() {
        Response response = measurementPostController.postProductMeasurement(
                new PostMeasurementDTO(1111L, 5.5)
        );
        assertNotNull(response);
        String res = (String) response.getEntity();
        assertEquals("product-not-found", res);
    }

    @Test
    void newNotOKMeasurement() {
        Response response = measurementPostController.postProductMeasurement(
                new PostMeasurementDTO(newProduct.id(), -23.6)
        );
        assertNotNull(response);
        PostMeasurementResponseDTO postMeasurementResponse = response.readEntity(PostMeasurementResponseDTO.class);
        assertEquals("not_ok", postMeasurementResponse.result());
    }

    @Test
    void measurementHistory() {
        measurementPostController.postProductMeasurement(new PostMeasurementDTO(newProduct.id(), 1));
        measurementPostController.postProductMeasurement(new PostMeasurementDTO(newProduct.id(), 1.4));
        measurementPostController.postProductMeasurement(new PostMeasurementDTO(newProduct.id(), 0.8));

        // Fetch history and assert
        List<MeasurementDTO> history = measurementHistoryController.getHistory();
        assertNotNull(history);
        assertEquals(3, history.size(), "The history size should be exactly 3.");
    }

}
