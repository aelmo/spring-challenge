package br.com.socialMeli.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "product_brand")
    private Brand brand;

    @Basic
    @Column(name = "color")
    private String color;

    @Basic
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "post_product")
    private Post post;

}
