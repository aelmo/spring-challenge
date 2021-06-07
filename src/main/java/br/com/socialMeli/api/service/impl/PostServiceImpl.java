package br.com.socialMeli.api.service.impl;

import br.com.socialMeli.api.dto.request.PostRequestDTO;
import br.com.socialMeli.api.dto.request.ProductRequestDTO;
import br.com.socialMeli.api.dto.response.PostResponseFindDTO;
import br.com.socialMeli.api.dto.response.ProductResponseFindDTO;
import br.com.socialMeli.api.model.Category;
import br.com.socialMeli.api.model.Post;
import br.com.socialMeli.api.model.Product;
import br.com.socialMeli.api.model.User;
import br.com.socialMeli.api.repository.*;
import br.com.socialMeli.api.service.PostService;
import br.com.socialMeli.api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FollowersRepository followersRepository;

    private final ProductService productService;

    public PostServiceImpl(final PostRepository postRepository, final UserRepository userRepository, final ProductRepository productRepository, final CategoryRepository categoryRepository, final FollowersRepository followersRepository, final ProductService productService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.followersRepository = followersRepository;
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

    @Override
    public List<PostResponseFindDTO> findPostByFollowed(Long userId) {
        logger.info("Post Service - Find Post By Followed");

        try {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                List<Long> userFollowed = followersRepository.getFollowedListById(userId);
                if (userFollowed.size() > 0) {
                    List<PostResponseFindDTO> postResponseFindDTOS = new ArrayList<>();
                    for (Long id : userFollowed) {
                        List<Post> postsBySeller = postRepository.findAllByUserAndCreatedAtBetweenOrderByCreatedAtAsc(userRepository.findById(id).get(), getDateTwoWeeksEarlier(new Date()), new Date());
                        for (Post post : postsBySeller) {
                            List<ProductResponseFindDTO> productResponseFindDTOS = new ArrayList<>();
                            for (Product product : post.getProduct()) {
                                ProductResponseFindDTO productResponseFindDTO = new ProductResponseFindDTO(
                                        product.getId(),
                                        product.getName(),
                                        product.getType(),
                                        product.getBrand(),
                                        product.getColor(),
                                        product.getNotes()
                                );

                                productResponseFindDTOS.add(productResponseFindDTO);
                            }

                            PostResponseFindDTO postResponseFindDTO = new PostResponseFindDTO(
                                    post.getId(),
                                    post.getCreatedAt(),
                                    productResponseFindDTOS,
                                    post.getCategory().getId(),
                                    post.getPrice()
                            );

                            postResponseFindDTOS.add(postResponseFindDTO);
                        }
                    }
                    return postResponseFindDTOS;
                }
                logger.error("User doesn't follow anyone");
                return null;
            }

            logger.error("User not found");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    private Date getDateTwoWeeksEarlier(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -14);
        return calendar.getTime();
    }
}
