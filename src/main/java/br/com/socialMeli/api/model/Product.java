package br.com.socialMeli.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "post")
public class Product {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "type", nullable = false)
    private String type;

    @Basic
    @Column(name = "brand", nullable = false)
    private String brand;

    @Basic
    @Column(name = "color")
    private String color;

    @Basic
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "post_product")
    private Post post;

    public Product(String productName, String type, String brand, String color, String notes) {
        this.name = productName;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.notes = notes;
    }
}
