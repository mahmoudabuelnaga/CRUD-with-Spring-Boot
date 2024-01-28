package com.naga.howtodoinjava.controllers;

import com.naga.howtodoinjava.persistences.UserPersistence;
import com.naga.howtodoinjava.repositories.UserRepository;
import com.naga.howtodoinjava.responses.Message;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

     @GetMapping(path = "")
     public List<UserPersistence> getAllUsers(){
         return userRepository.findAll();
     }

     @PostMapping(path = "")
     public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserPersistence userPersistence, BindingResult bindingResult){
         if (bindingResult.hasErrors()) {
             // If there are validation errors, construct a response with error messages
             StringBuilder errorMessage = new StringBuilder();
             for (FieldError error : bindingResult.getFieldErrors()) {

                 errorMessage.append(error.getField()+ " : " + error.getDefaultMessage()).append("; ");
             }
             Message message = new Message(errorMessage.toString());
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
         }
         UserPersistence userPersistence1 = userRepository.save(userPersistence);
         return new ResponseEntity<>(userPersistence1, HttpStatus.CREATED);
     }

     @GetMapping(path = "/{uuid}")
     public ResponseEntity<Object> getUser(@PathVariable("uuid") UUID uuid){
         UserPersistence userPersistence = userRepository.findById(uuid).orElse(null);
         if (userPersistence == null){
             String message_str = "User with id " + uuid + "is not exists.";
             Message message = new Message(message_str);
             return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
         }
         return new ResponseEntity<>(userPersistence, HttpStatus.OK);
     }

     @PutMapping(path = "/{uuid}")
    public ResponseEntity<Object>
        putUser(@Valid @RequestBody UserPersistence user, @PathVariable("uuid") UUID uuid, BindingResult bindingResult){
         if (bindingResult.hasErrors()) {
             // If there are validation errors, construct a response with error messages
             StringBuilder errorMessage = new StringBuilder();
             for (FieldError error : bindingResult.getFieldErrors()) {

                 errorMessage.append(error.getField()+ " : " + error.getDefaultMessage()).append("; ");
             }
             Message message = new Message(errorMessage.toString());
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
         }

         UserPersistence userPersistence = userRepository.findById(uuid)
                .orElse(null);
        if (userPersistence == null){
            String message_str = "User with id " + uuid + "is not exists.";
            Message message = new Message(message_str);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        userPersistence.setName(user.getName());
        userPersistence.setEmail(user.getEmail());
        userPersistence.setPassword(user.getPassword());
        UserPersistence newUserPersistence = userRepository.save(userPersistence);
        return new ResponseEntity<>(newUserPersistence, HttpStatus.OK);
     }

     @PatchMapping(path = "/{uuid}")
     public ResponseEntity<Object> patchUser(@Valid @RequestBody UserPersistence user, @PathVariable("uuid") UUID uuid, BindingResult bindingResult){
         if (bindingResult.hasErrors()) {
             // If there are validation errors, construct a response with error messages
             StringBuilder errorMessage = new StringBuilder();
             for (FieldError error : bindingResult.getFieldErrors()) {

                 errorMessage.append(error.getField()+ " : " + error.getDefaultMessage()).append("; ");
             }
             Message message = new Message(errorMessage.toString());
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
         }
         UserPersistence userPersistence = userRepository.findById(uuid)
                 .orElse(null);
         if (userPersistence == null){
             String message_str = "User with id " + uuid + "is not exists.";
             Message message = new Message(message_str);
             return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
         };
         userPersistence.setName(user.getName());
         userPersistence.setEmail(user.getEmail());
         userPersistence.setPassword(user.getPassword());
         UserPersistence newUserPersistence = userRepository.save(userPersistence);
         return new ResponseEntity<>(newUserPersistence, HttpStatus.OK);
     }
     @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<Object> deleteUser(@PathVariable("uuid") UUID uuid){
         UserPersistence userPersistence = userRepository.findById(uuid)
                 .orElse(null);
         if (userPersistence == null){
             String message_str = "User with id " + uuid + "is not exists.";
             Message message = new Message(message_str);
             return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
         }
         userRepository.deleteById(uuid);
         String message_str = "User with id " + uuid + "is deleted.";
         Message message = new Message(message_str);
         return new ResponseEntity<>(message, HttpStatus.OK);
     }

}
