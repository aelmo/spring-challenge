package br.com.socialMeli.api.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

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
@ToString
@EqualsAndHashCode
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date birthDate;

    @Basic
    @Column(name = "cpf", nullable = false, unique = true)
    @Size(min = 11, max = 11)
    private String cpf;

    @Basic
    @Column(name = "is_seller", nullable = false, columnDefinition = "bool default false")
    private Boolean isSeller;

    @OneToMany(mappedBy = "followed")
    private List<Followers> followed;

    @OneToMany(mappedBy = "follower")
    private List<Followers> followers;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Basic
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

}
