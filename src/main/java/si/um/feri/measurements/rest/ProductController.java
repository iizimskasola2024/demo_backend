package si.um.feri.measurements.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import si.um.feri.measurements.dao.ProductRepository;
import si.um.feri.measurements.vao.Product;

import java.util.List;

@Path("/products")
public class ProductController {

    @Inject
    ProductRepository productRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        return //TODO() productRepository.listAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Product addProduct(Product product){
        return //TODO() productRepository.persistAndFlush(product);
    }


}
