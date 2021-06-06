package br.com.socialMeli.api.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@DynamicInsert
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Basic
    @Column(name = "cpf", nullable = false, unique = true)
    @Size(min = 11, max = 11)
    private String cpf;

    @Basic
    @Column(name = "is_seller", nullable = false, columnDefinition = "bool default false")
    private Boolean isSeller;

    @OneToMany(mappedBy = "follower")
    private List<Followers> followers;

    @OneToMany(mappedBy = "followed")
    private List<Followers> following;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Basic
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public User(String name, String email, String cpf, Boolean isSeller, Date date) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.isSeller = isSeller;
        this.createdAt = date;
    }

}
