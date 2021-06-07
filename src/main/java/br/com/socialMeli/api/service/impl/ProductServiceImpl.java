package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.model.Post;
import br.com.socialMeli.api.model.Product;
import br.com.socialMeli.api.repository.PostRepository;
import br.com.socialMeli.api.repository.ProductRepository;
import br.com.socialMeli.api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final PostRepository postRepository;

    public ProductServiceImpl(final ProductRepository productRepository, final PostRepository postRepository) {
        this.productRepository = productRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Product> assignToPostById(final Product product, final Long postToAssign) {
        logger.info("Product Service - Assign To Post By Id");

        Optional<Post> post = postRepository.findById(postToAssign);

        return productRepository.findById(product.getId())
                    .map(pr -> {
                        pr.setName(product.getName());
                        pr.setType(product.getType());
                        pr.setBrand(product.getBrand());
                        pr.setColor(product.getColor());
                        pr.setNotes(product.getNotes());
                        pr.setPost(post.get());

                        Product updated = productRepository.save(pr);

                        return ResponseEntity.ok().body(updated);
                    }).orElse(ResponseEntity.notFound().build());
    }
}
