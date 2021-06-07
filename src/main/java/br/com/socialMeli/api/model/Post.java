package br.com.socialMeli.api.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Post {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_category")
    private Category category;

    @Basic
    @Column(name = "price", nullable = false)
    private Double price;

    @Basic
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "id")
    private List<Product> product;

    public Post(User user, Date date, Category category, Double price) {
        this.user = user;
        this.createdAt = date;
        this.category = category;
        this.price = price;
    }
}
