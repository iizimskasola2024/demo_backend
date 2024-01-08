package si.um.feri.measurements.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import si.um.feri.measurements.dao.MeasurementRepository;
import si.um.feri.measurements.dao.ProductRepository;
import si.um.feri.measurements.dto.post.PostMeasurement;
import si.um.feri.measurements.dto.post.PostMeasurementResponse;
import si.um.feri.measurements.vao.Measurement;

import java.util.List;
import java.util.logging.Logger;

@Path("/measurements")
public class MeasurementController {

    private static final Logger log = Logger.getLogger(MeasurementController.class.toString());

    @Inject
    MeasurementRepository measurementRepository;

    @Inject
    ProductRepository productRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Measurement> getAllProducts() {
        //TODO()
    }
    boolean ok = true;
    @POST
    public RestResponse<PostMeasurementResponse> addMeasurement(PostMeasurement m) {
        //TODO()
    }
}
