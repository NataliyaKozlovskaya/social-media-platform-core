package com.social.media.platform.core.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Friendship")
public class Friendship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", updatable = false)
    private User friend;

    @Transient
    private Boolean flag;

    private Boolean subscription;
    private Boolean friendship;
    private Boolean correspondence;

}
