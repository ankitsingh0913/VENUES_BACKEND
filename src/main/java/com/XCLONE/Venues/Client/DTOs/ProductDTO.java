package com.XCLONE.Venues.Client.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ProductDTO {
    @NotEmpty(message = "The name is Required")
    private String name;
    @NotEmpty(message = "The brand is Required")
    private String brand;
    @NotEmpty(message = "The category is required")
    private String category;
    @Min(0)
    private double price;
    @Size(min = 10,message = "The description should be at least 10 characters")
    @Size(max = 200,message = "Cannot exceed more than 200 characters")
    private String description;
    @JsonIgnore
    private MultipartFile imageFile;

}
