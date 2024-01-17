package si.um.feri.measurements;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import si.um.feri.measurements.dao.MeasurementRepository;
import si.um.feri.measurements.dao.ProductRepository;
import si.um.feri.measurements.dto.post.PostMeasurementDTO;
import si.um.feri.measurements.vao.Measurement;
import si.um.feri.measurements.vao.Product;

import java.util.logging.Logger;

@ApplicationScoped
public class MeasurementsApplicationLifecycle {

    private static final Logger log = Logger.getLogger(MeasurementsApplicationLifecycle.class.getName());

    @Inject
    ProductRepository productRepository;

    @Inject
    MeasurementRepository measurementRepository;

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting...");
        populateTestDataIfNotPresent();
    }

    @Transactional
    void populateTestDataIfNotPresent() {
        if (productRepository.count() > 0) {
            log.info("populateTestData - skipped.");
            return;
        }
        log.info("populateTestData initiated.");

        Product p1 = new Product();
        p1.setName("Milka Classic");
        p1.setMinMeasure(-5.0);
        p1.setMaxMeasure(18.0);
        productRepository.persist(p1);

        Product p2 = new Product();
        p2.setName("Chicken Breasts");
        p2.setMinMeasure(-25.0);
        p2.setMaxMeasure(-8.0);
        productRepository.persist(p2);

        PostMeasurementDTO m1 = new PostMeasurementDTO(p1.getId(), 12);
        Measurement me1 = new Measurement(m1, p1);
        measurementRepository.persist(me1);

        PostMeasurementDTO m2 = new PostMeasurementDTO(p2.getId(), -10);
        Measurement me2 = new Measurement(m2, p2);
        measurementRepository.persist(me2);
    }
}