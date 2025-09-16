package com.dev.syf_demo.service;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import com.dev.syf_demo.model.AppUser;
import com.dev.syf_demo.model.AuthenticatedUserDetails;
import com.dev.syf_demo.model.ImageDto;
import com.dev.syf_demo.model.Images;
import com.dev.syf_demo.repository.ImageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserService userService;


    private final String imgBBApiKey = "6c969335f1ab774031774cd39b90cb35";
    private final String apiDomain = "https://api.imgbb.com/1/";
    private final RestTemplate restTemplate;
    MultiValueMap<String, String> formData;
    String response;


    public ImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   public String uploadImage(String imageBase64)
   {
       response = "";
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.MULTIPART_FORM_DATA);

       formData = new LinkedMultiValueMap<>();
       formData.add("image", imageBase64);

       String url = String.format( "%supload?key=%s", apiDomain, imgBBApiKey );

       try{

           response = restTemplate.postForObject(url,formData,String.class,headers);
           saveImageData(response);

       }catch(Exception ex){

           response = ex.getMessage();
       }

       return  response;
   }

   public void saveImageData(String response)
   {
       ImageDto imageDto;
       try{
            imageDto = new ObjectMapper().readValue(response, ImageDto.class);

            Map<String, String> kvp = getDeleteTokenAndImageId(imageDto.data);
            
            Images image = new Images();
            image.setAppuser(getCurrentUser());
            image.setName(imageDto.data.image.name);
            image.setUrl(imageDto.data.image.url);
            image.setDeleteToken(kvp.get("deleteToken"));
            image.setApiImageId(kvp.get("apiImageId"));
            imageRepository.save(image);

       }catch(JsonProcessingException ex){
           response = ex.getMessage();
       }
   }

    public String deleteImage(Integer imageId)
    {

        try{
            AppUser appUser;
            appUser = getCurrentUser();
            response = "";

            Images image;
            Optional<Images> optionalImage = appUser.getImages()
                    .stream()
                    .filter(img -> img.getId().equals(imageId))
                    .findFirst();

            if(!optionalImage.isEmpty()) { image = optionalImage.get(); } else throw new EntityNotFoundException("Image not found.");

            response = deleteImageFromServer(image.getDeleteToken(),image.getApiImageId());
            response += deleteImageFromDatabase(imageId);

        }catch(Exception ex){
            response = ex.getMessage();
        }
        return response;
    }

    private Map<String,String> getDeleteTokenAndImageId(ImageDto.Data data)
    {
        String[] deleteTokenUrl = data.delete_url.split("/");
        Map<String,String> kvp = new HashMap<>();
        kvp.put("apiImageId",deleteTokenUrl[3]);
        kvp.put("deleteToken",deleteTokenUrl[4]);
        return kvp;
    }

    private String  deleteImageFromServer(String deleteToken, String apiImageId)
    {
        String url =  "https://ibb.co/json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        try{

            formData = new LinkedMultiValueMap<>();
            formData.add("action", "delete");
            formData.add("delete", "image");
            formData.add("from", "resource");
            formData.add("deleting[id]", apiImageId);
            formData.add("deleting[hash]", deleteToken);

            response = "API response => " +  restTemplate.postForObject(url,formData,String.class,headers);

        }catch(Exception ex){

            response = ex.getMessage();
        }

        return  response;
    }

    private String deleteImageFromDatabase(Integer id)
    {
        try {
            imageRepository.deleteImagesById(id);
        } catch (Exception ex) {
            response = ex.getMessage();
        }
        return " DB response => Success. Deleted from database.";
    }

    private AppUser getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                AuthenticatedUserDetails userDetails = (AuthenticatedUserDetails) principal;
                return userService.GetUserById(userDetails.getId());
            } else {
                return null;
            }
        }
        return null; // Or throw an exception if no user is authenticated
    }
}
