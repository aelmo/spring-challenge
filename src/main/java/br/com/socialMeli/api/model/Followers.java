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
    @JoinColumn(name = "follower_user_fk")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_user_fk")
    private User followed;

    public Followers(User userFollowing, User userFollowed) {
        this.follower = userFollowing;
        this.followed = userFollowed;
    }
}
