package br.com.socialMeli.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "follower")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Followers {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_fk")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_user_fk")
    private User to;

}
