package com.XCLONE.Venues.Client.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection= "Product")
public class Products {
    @Id
    private ObjectId id;
    @JsonIgnore
    private Users users;
    @NonNull
    private String name;
    private String brand;
    private String category;
    private double price;
    private String description;
    private Date createdAt;  // Changed from Data to Date
    private String imageFileName;
    private List<String> images;
}