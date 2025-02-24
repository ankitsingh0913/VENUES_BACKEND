package com.XCLONE.Venues.Client.Controllers;

import com.XCLONE.Venues.Client.DTOs.ProductDTO;
import com.XCLONE.Venues.Client.Entity.Products;
import com.XCLONE.Venues.Client.Repository.ProductRepository;
import com.XCLONE.Venues.Client.Services.ProductServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductServices productServices;

    @GetMapping("/")
    public String showProductList(){
        try{
            return "index";
        } catch (Exception ex){
            return "redirect:/";
        }
    }

    @GetMapping("/customerLogin")
    public String showLoginPage(){
        try{
            return "login";
        } catch (Exception ex){
            return "redirect:/index";
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts(){
        List<Products> allProducts = repository.findAll();
        if (allProducts != null && !allProducts.isEmpty()) {
            return new ResponseEntity<>(allProducts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createProduct(
            @ModelAttribute ProductDTO productDTO,
            @RequestParam("imageFile") MultipartFile imageFile,
            HttpSession session
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            productDTO.setImageFile(imageFile);
            Products products = productServices.saveProduct(productDTO,userName);
            if(ObjectUtils.isEmpty(products)){
                session.setAttribute("errorMsg","Product not saved");
            } else {
                session.setAttribute("succMsg","Product saved successfully");
            }
            return "redirect:/category";
        } catch (Exception e) {
            return "redirect:/category";
        }
    }


//    @GetMapping("/edit")
//    public String showEditPage(Model model, @RequestParam ObjectId id){
//        try{
//            Products product = repository.findById(id).get();
//            model.addAttribute("product",product);
//
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setName(product.getName());
//            productDTO.setBrand(product.getBrand());
//            productDTO.setCategory(product.getCategory());
//            productDTO.setPrice(product.getPrice());
//            productDTO.setDescription(product.getDescription());
//
//            model.addAttribute("productDTO",productDTO);
//
//        } catch (Exception ex) {
//            System.out.println("Exception: " + ex.getMessage());
//            return "redirect:/products";
//        }
//        return "products/EditProduct";
//    }
//
//    @PostMapping("/edit")
//    public String updateProduct(
//            Model model,
//            @RequestParam ObjectId id,
//            @Valid @ModelAttribute ProductDTO productDTO,
//            BindingResult result){
//        try {
//            Products product = repository.findById(id).get();
//            model.addAttribute("product",product);
//            if(result.hasErrors()){
//                return "products/EditProduct";
//            }
//            if(!productDTO.getImageFile().isEmpty()){
//                String uploadDir = "/public/images/";
//                Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());
//
//                try{
//                    Files.delete(oldImagePath);
//                } catch (Exception ex){
//                    System.out.println("Exception: " + ex.getMessage());
//                }
//                MultipartFile image = productDTO.getImageFile();
//                Date createdAt = new Date();
//                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
//                try (InputStream inputStream = image.getInputStream()){
//                    Files.copy(inputStream,Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
//                }
//                product.setImageFileName(storageFileName);
//            }
//            product.setName(productDTO.getName());
//            product.setBrand(productDTO.getBrand());
//            product.setCategory(productDTO.getCategory());
//            product.setPrice(productDTO.getPrice());
//            product.setDescription(productDTO.getDescription());
//            repository.save(product);
//
//        } catch (Exception ex) {
//            System.out.println("Exception: " + ex.getMessage());
//        }
//        return "redirect:/products";
//    }
//
//    @GetMapping("/delete")
//    public String deleteProduct(@RequestParam ObjectId id){
//        try {
//            Products product = repository.findById(id).get();
//
//            Path imagePath = Paths.get("public/images/"+product.getImageFileName());
//            try {
//                Files.delete(imagePath);
//            } catch (Exception x){
//                System.out.println("Exception: "+x.getMessage());
//            }
//            repository.delete(product);
//        } catch (Exception ex){
//            System.out.println("Exception: " + ex.getMessage());
//        }
//
//        return "redirect:/products";
//    }

}
