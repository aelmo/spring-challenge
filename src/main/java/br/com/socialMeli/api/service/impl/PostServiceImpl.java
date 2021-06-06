package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.request.ProductRequestDTO;
import br.com.socialMeli.api.model.*;
import br.com.socialMeli.api.repository.CategoryRepository;
import br.com.socialMeli.api.repository.PostRepository;
import br.com.socialMeli.api.repository.ProductRepository;
import br.com.socialMeli.api.repository.UserRepository;
import br.com.socialMeli.api.service.PostService;
import br.com.socialMeli.api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    public PostServiceImpl(final PostRepository postRepository, final UserRepository userRepository, final ProductRepository productRepository, final CategoryRepository categoryRepository, final ProductService productService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public Post saveNewPost(PostRequestDTO postRequestDTO) {
        logger.info("Post Service - Save New Post");

        try {
            Optional<User> user = userRepository.findById(postRequestDTO.getUserId());
            Optional<Category> category = categoryRepository.findById(postRequestDTO.getCategory());

            if (user.isPresent() && user.get().getIsSeller()) {
                if (category.isPresent()) {
                    Post newPost = new Post(
                            user.get(),
                            new Date(),
                            category.get(),
                            postRequestDTO.getPrice()
                    );

                    List<Product> products = new ArrayList<>();

                    for (ProductRequestDTO productDTO : postRequestDTO.getDetails()) {
                        Product product = new Product(
                                productDTO.getProductName(),
                                productDTO.getType(),
                                productDTO.getBrand(),
                                productDTO.getColor(),
                                productDTO.getNotes()
                        );

                        productRepository.save(product);

                        products.add(product);
                    }

                    newPost.setProduct(products);

                    Post post = postRepository.save(newPost);

                    for (Product product : products)
                        productService.assignToPostById(product, post.getId());

                    return post;
                }
                logger.error("Category not found");
                return null;
            }

            logger.error("User not found/User is not a seller");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }
}
