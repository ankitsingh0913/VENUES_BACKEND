package com.XCLONE.Venues.Client.Repository;

import com.XCLONE.Venues.Client.Entity.Products;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repositorya
public interface ProductRepository extends MongoRepository<Products, ObjectId> {

}
