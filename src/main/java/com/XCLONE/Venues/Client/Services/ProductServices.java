package com.XCLONE.Venues.Client.Services;

import com.XCLONE.Venues.Client.Entity.Users;
import com.XCLONE.Venues.Client.DTOs.ProductDTO;
import com.XCLONE.Venues.Client.Entity.Products;
import com.XCLONE.Venues.Client.Repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserServices userServices;



    @Transactional
    public Products saveProduct(ProductDTO productDTO, String userName){
        try {
            Users user = userServices.findByUserName(userName);
            MultipartFile image = productDTO.getImageFile();
            String imageFileName = null;

            if (image != null && !image.isEmpty()) {
                String uploadDir = "public/images/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Define a unique file name
                String uniqueFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                imageFileName = uploadDir + uniqueFileName;

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, uploadPath.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            Products product = new Products();
            product.setUsers(user);
            product.setName(productDTO.getName());
            product.setBrand(productDTO.getBrand());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setImageFileName(imageFileName);
            Products save = productRepository.save(product);
            user.getProductsEntries().add(product);
            userServices.saveUser(user);
            return save;
        } catch (Exception e){
            System.out.println("Exception: "+ e);
            throw new RuntimeException("An error occurred while saving the entry. "+e);
        }
    }

    public void saveEntry(Products products){
        try {
            productRepository.save(products);
        } catch (Exception e){
            System.out.println("Exception: "+ e);
        }
    }

    public List<Products> getAll(){
        return productRepository.findAll();
    }

    public Optional<Products> findById(ObjectId id){
        return productRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            Users user = userServices.findByUserName(userName);
            removed = user.getProductsEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userServices.saveUser(user);
                productRepository.deleteById(id);
            }
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry.",e);
        }
        return removed;
    }

}
