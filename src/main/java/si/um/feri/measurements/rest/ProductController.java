package si.um.feri.measurements.rest;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.measurements.dto.ProductDTO;
import si.um.feri.measurements.vao.Product;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    private static final Logger log = Logger.getLogger(ProductController.class.getName());

    @GET
    public List<ProductDTO> getAllProducts() {
        return Product.<Product>listAll().stream() // Explicitly specify the type
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id) {
        Product product = Product.findById(id);
        if (product == null) {
            log.info(() -> "/products/" + id + " ; Product not found!");
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        return Response.ok(product.toDto()).build();
    }

    @POST
    @Transactional
    public Response postProduct(ProductDTO dto) {
        Product product = new Product(dto);
        product.persist();
        return Response.ok(product.toDto()).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response putProduct(@PathParam("id") int id, ProductDTO dto) {
        Product product = Product.findById(id);
        if (product == null) {
            log.info(() -> "/products/" + id + " ; Product not found!");
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        product.updateFrom(dto);
        product.persist();
        return Response.ok(product.toDto()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteProduct(@PathParam("id") int id) {
        Product product = Product.findById(id);
        if (product == null) {
            log.info(() -> "/products/" + id + " ; Product not found!");
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
        product.delete();
        return Response.ok("Product deleted").build();
    }

}
