package com.XCLONE.Venues.Client.Controllers;
import com.XCLONE.Venues.Client.DTOs.UserDTO;
import com.XCLONE.Venues.Client.Entity.Users;
import com.XCLONE.Venues.Client.Services.UserDetailsServiceIMPL;
import com.XCLONE.Venues.Client.Services.UserServices;
import com.XCLONE.Venues.Utils.JWTUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/client/public")
@Slf4j
@Tag(name = "Public APIs",description = "Health Status, Signup, Login User")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserServices userServices;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/signup-User")
    public ResponseEntity<?> signupUser(@RequestBody UserDTO user){
        try {
            Users newUser = new Users();
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setUserName(user.getUserName());
            newUser.setPassword(user.getPassword());
            newUser.setPhoneNo(user.getPhoneNo());
            userServices.saveNewUser(newUser);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/signup-seller")
    public ResponseEntity<?> signupSeller(@RequestBody UserDTO user){
        try {
            Users newUser = new Users();
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setUserName(user.getUserName());
            newUser.setPassword(user.getPassword());
            newUser.setPhoneNo(user.getPhoneNo());
            userServices.saveNewAdmin(newUser);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Users user){
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
//            UserDetails userDetails = userDetailsServiceIMPL.loadUserByUsername(user.getUserName());
//            String jwt = jwtUtils.generateToken(userDetails.getUsername());
//            return new ResponseEntity<>(jwt,HttpStatus.CREATED);
//        } catch (Exception e){
//            log.error("Exception occurred while createAuthenticationToken", e);
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            UserDetails userDetails = userDetailsServiceIMPL.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("token", jwt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred during login", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Invalid credentials");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

}
