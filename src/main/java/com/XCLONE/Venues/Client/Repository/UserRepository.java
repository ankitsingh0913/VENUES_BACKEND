package com.XCLONE.Venues.Client.Repository;
import com.XCLONE.Venues.Client.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, ObjectId> {

    Users findByUserName(String username);
    void deleteByUserName(String username);
}
