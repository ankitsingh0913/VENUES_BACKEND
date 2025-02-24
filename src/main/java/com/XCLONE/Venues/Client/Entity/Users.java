package com.XCLONE.Venues.Client.Entity;
import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private String userName;
    private String email;
    private String phoneNo;
    private String image;
    @NonNull
    private String password;
    private List<String> roles;
    @DBRef
    private List<Products> productsEntries = new ArrayList<>();
}
