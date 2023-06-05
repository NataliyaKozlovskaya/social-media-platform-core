package com.social.media.platform.core.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="from_user_id", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="to_user_id", updatable = false)
    private User user2;

    @Max(value=200, message = "Context should not be more than 200 characters")
    private String context;


}
