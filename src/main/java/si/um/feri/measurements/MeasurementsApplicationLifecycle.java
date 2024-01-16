package si.um.feri.measurements;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import si.um.feri.measurements.vao.Product;

import java.util.logging.Logger;

@ApplicationScoped
public class MeasurementsApplicationLifecycle {

    private static final Logger log = Logger.getLogger(MeasurementsApplicationLifecycle.class.getName());

    void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting...");
        populateTestDataIfNotPresent();
    }

    @Transactional
    void populateTestDataIfNotPresent() {
        if (Product.count() > 0) {
            log.info("populateTestData - skipped.");
            return;
        }
        log.info("populateTestData initiated.");

        Product p1 = new Product();
        p1.name = "Milka Classic";
        p1.minMeasure = -5.0;
        p1.maxMeasure = 18.0;
        p1.persist();

        Product p2 = new Product();
        p2.name = "Chicken Breasts";
        p2.minMeasure = -25.0;
        p2.maxMeasure = -8.0;
        p2.persist();
    }
}
