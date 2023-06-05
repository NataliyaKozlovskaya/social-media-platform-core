package com.social.media.platform.core.models;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Picture")
public class Picture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Lob
    private String icon;
    //private File icon;
    //private Image icon;
    //private byte[] icon;
    //private Path icon;
    //private Multipart icon;

//    @ManyToOne
//    @JoinColumn(name = "post_id", updatable = false)
//    private Post post;
}
