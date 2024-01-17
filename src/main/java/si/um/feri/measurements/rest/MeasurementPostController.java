package si.um.feri.measurements.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.measurements.dao.MeasurementRepository;
import si.um.feri.measurements.dao.ProductRepository;
import si.um.feri.measurements.dto.post.PostMeasurementDTO;
import si.um.feri.measurements.dto.post.PostMeasurementResponseDTO;
import si.um.feri.measurements.vao.Measurement;
import si.um.feri.measurements.vao.Product;

import java.util.logging.Logger;

@Path("/measurements")
public class MeasurementPostController {

    private static final Logger log = Logger.getLogger(MeasurementPostController.class.getName());

    @Inject
    ProductRepository productRepository;

    @Inject
    MeasurementRepository measurementRepository;

    @POST
    @Path("/product_measurement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postProductMeasurement(PostMeasurementDTO m) {
        Product product = productRepository.findById(m.id());
        if (product == null) {
            log.info(() -> "/product_measurement sent: " + m + "; Product not found!");
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("product-not-found").build();
        }

        Measurement measurement = new Measurement(m, product);
        boolean ok = true;

        // Action?
        if (m.avgTemperature() < product.getMinMeasure() || m.avgTemperature() > product.getMaxMeasure()) {
            log.info(() -> "/product_measurement sent: " + m + "; product: " + product + "; ACTION NEEDED");
            ok = false;
        }

        // Save
        measurement.setOk(ok);
        measurementRepository.persist(measurement);

        // Response
        return Response.ok(new PostMeasurementResponseDTO(ok ? "ok" : "not_ok")).build();
    }
}