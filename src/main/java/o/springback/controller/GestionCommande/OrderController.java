package o.springback.controller.GestionCommande;

import jakarta.validation.constraints.NotNull;
import o.springback.Interfaces.GestionCommande.OrderProductService;
import o.springback.Interfaces.GestionCommande.OrderService;
import o.springback.dto.GestionCommande.OrderProductDto;
import o.springback.entities.GestionCommande.Order;
import o.springback.entities.GestionCommande.OrderProduct;
import o.springback.entities.GestionCommande.OrderStatus;
import o.springback.entities.GestionProduits.Products;
import o.springback.exception.ResourceNotFoundException;
import o.springback.services.GestionCommande.PDFGeneratorService;
import o.springback.services.GestionProduits.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/commande")
public class OrderController {


    ProductService productService;
    OrderService orderService;
    OrderProductService orderProductService;

    public OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<Order> list() {
        return this.orderService.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderForm form) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);
        Order order = new Order();
        order.setStatus(OrderStatus.UNFINISHED.name());
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.findById(dto
                    .getProduct()
                    .getIdProduit()), dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);

        this.orderService.update(order);

        order.getOrderProducts().forEach(orderProduct -> {
            Products product = orderProduct.getProduct();
            product.setQuantiteDisponible(product.getQuantiteDisponible() - orderProduct.getQuantity());
            productService.update(product);
        });

        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/orders/{id}")
                .buildAndExpand(order.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<String> errors = new ArrayList<>();

        for (OrderProductDto op : orderProducts) {
            Products product = productService.findById(op.getProduct().getIdProduit());

            if (product == null) {
                errors.add("Product with ID " + op.getProduct().getIdProduit() + " not found");
            } else if (product.getQuantiteDisponible() <= 0) {
                errors.add("Product " + product.getIdProduit() + " is out of stock");
            } else if (product.getQuantiteDisponible() < op.getQuantity()) {
                errors.add("Insufficient stock for product " + product.getIdProduit() +
                        ". Available: " + product.getQuantiteDisponible() +
                        ", Requested: " + op.getQuantity());
            }
        }

        if (!errors.isEmpty()) {
            throw new ResourceNotFoundException(String.join(", ", errors));
        }
    }

    public static class OrderForm {

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }


    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        orderService.deleteCommande(id);
    }



    @Autowired
    private PDFGeneratorService pdfGeneratorService;
    @GetMapping("/{orderId}/invoice")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);


            byte[] pdfBytes = pdfGeneratorService.generateOrderInvoice(order);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename("facture-" + orderId + ".pdf")
                            .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
