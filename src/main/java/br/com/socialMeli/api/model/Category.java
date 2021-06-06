package br.com.socialMeli.api.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Category {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "id")
    private List<Post> posts;

    public Category(String name) {
        this.name = name;
    }
}
