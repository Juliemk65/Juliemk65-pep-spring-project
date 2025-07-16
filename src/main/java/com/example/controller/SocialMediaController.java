package com.example.controller;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    //1. Our API should be able to process new User registrations.
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
       try{
        Account savedAccount = accountService.registerAccount(account);
        return ResponseEntity.ok(savedAccount);
       } catch (IllegalArgumentException e) {
        String message = e.getMessage();

            if ("Username already taken".equals(message)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message); 
            }

            return ResponseEntity.badRequest().body(message);
       }
    }

    //2. Our API should be able to process User logins.
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account) {
        try{
            Account existingAccount = accountService.loginAccount(account);
            return ResponseEntity.ok(existingAccount);
        } catch (IllegalArgumentException e){
             String message = e.getMessage();

            if ("Incorrect username or password".equals(message)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message); 
            }

            return ResponseEntity.badRequest().body(message);
        }
    }

//     //3. Our API should be able to process the creation of new messages.
    @PostMapping("/messages")
    public ResponseEntity<?> postNewMessage(@RequestBody Message message) {
        try{
            Message savedMessage = messageService.createMessage(message);
            return ResponseEntity.ok(savedMessage);
        } catch (IllegalArgumentException e){
              return ResponseEntity.badRequest().body(message);
        }
        
    }
 
//     //4. Our API should be able to retrieve all messages.
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.retrieveAllMessages();
        return ResponseEntity.ok(messages);
    }

//     //5. Our API should be able to retrieve a message by its ID.
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageUsingId(@PathVariable Integer messageId){
      
    Optional<Message> messageUsingId = messageService.retrieveAMessageUsingId(messageId);
      if(messageUsingId.isEmpty()){
        return ResponseEntity.ok().build();
      }  
    return ResponseEntity.ok(messageUsingId);        
       
    }

//     //6. Our API should be able to delete a message identified by a message ID.
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageUsingId(@PathVariable Integer messageId){
        Optional<Message> deletedMessageOptional = messageService.deleteMessage(messageId);
        if(deletedMessageOptional.isPresent()){
        return ResponseEntity.ok("1");            

      } else {
         
         return ResponseEntity.ok().build();   
      } 
   
    }

    //7. Our API should be able to update a message text identified by a message ID.
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageUsingId(@PathVariable Integer messageId, @RequestBody Message message){
        

        try{
        messageService.updateMessage(messageId, message.getMessageText());            
        return ResponseEntity.ok("1");            

      } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().build();   
      } 
    }

    //8. Our API should be able to retrieve all messages written by a particular user.
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesUsingAccountId(@PathVariable Integer accountId){
       List<Message> messagesUsingAccountId = messageService.retrieveAllMessagesForUser(accountId);
            return ResponseEntity.ok(messagesUsingAccountId);         
    }

 }
