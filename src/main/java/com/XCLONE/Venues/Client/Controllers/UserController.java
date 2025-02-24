package com.XCLONE.Venues.Client.Controllers;
import com.XCLONE.Venues.Client.Entity.Users;
import com.XCLONE.Venues.Client.Repository.UserRepository;
import com.XCLONE.Venues.Client.Services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<?> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userServices.findByUserName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody Users user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users userInDB = userServices.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userServices.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
