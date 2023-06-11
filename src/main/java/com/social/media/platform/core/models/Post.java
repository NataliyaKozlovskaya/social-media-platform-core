package com.social.media.platform.core.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@EqualsAndHashCode(exclude = {"listPictures"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"listPictures"})
@Entity
@Table(name = "Post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Max(value=30, message = "Title should not be more than 30 characters")
    private String title;

    @Max(value=200, message = "Content should not be more than 200 characters")
    private String content;

    private Date time;
    private Date updatedTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> listPictures = new ArrayList<>();

}
