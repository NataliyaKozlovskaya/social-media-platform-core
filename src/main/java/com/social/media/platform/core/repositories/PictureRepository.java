package com.social.media.platform.core.repositories;

import com.social.media.platform.core.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    //void save(byte[] b);
   // void save(MultipartFile[] file);

    void save(MultipartFile file);

     Path save(String originalFilename);


    //void save(Path path);
}
