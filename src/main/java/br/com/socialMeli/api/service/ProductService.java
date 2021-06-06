package br.com.socialMeli.api.service;

import br.com.socialMeli.api.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<Product> assignToPostById(final Product product, final Long postToAssign);
}
