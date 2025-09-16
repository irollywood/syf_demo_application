package com.dev.syf_demo.controller;

import com.dev.syf_demo.model.ImageUpload;
import com.dev.syf_demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody ImageUpload image)
    {
        return new ResponseEntity<>(imageService.uploadImage(image.Base64Image), HttpStatus.OK) ;
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id)
    {
        return new ResponseEntity<>(imageService.deleteImage(id),HttpStatus.OK);
    }
}

