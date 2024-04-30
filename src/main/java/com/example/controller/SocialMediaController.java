package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;


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

    @Autowired
    SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAll(){
        List<Message> msgs = messageService.getallMessages();
        return ResponseEntity.status(HttpStatus.OK).body(msgs);
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account regAct = accountService.register(account);
        if(regAct != null){
        return ResponseEntity.status(HttpStatus.OK).body(regAct);
        }
        else{
            return ResponseEntity.status(409).body(null);
        }
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        Account logAct = accountService.login(account);
        if(logAct !=null){
            return ResponseEntity.status(HttpStatus.OK).body(logAct);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Optional<Message>> getMessageByMessageId(@PathVariable("message_id") Integer messageId){
        Optional<Message> message = messageService.getMessageById(messageId);
        if(message.isEmpty()){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(message);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> create(@RequestBody Message msg){
        if(accountService.test(msg.getPostedBy())){
            Message newMessage = messageService.postMessage(msg);
            if(newMessage != null){
                return new ResponseEntity<>(newMessage, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(400).body(null);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMsgById(@PathVariable("message_id") Integer messageId){
        Optional<Message> message = messageService.dltMessageById(messageId);
        if(message.isEmpty()){
            return ResponseEntity.status(200).body(null);
        }else{
            return ResponseEntity.status(200).body(1);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMsg(@RequestBody Message message, @PathVariable int message_id) {
        Integer rows_updated = messageService.updateMessage(message, message_id);
        HttpStatus httpstatus = rows_updated == 0 ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return ResponseEntity.status(httpstatus).body(rows_updated);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMsgsByAct(@PathVariable("account_id") Integer id){
        List<Message> messageList = messageService.getallMessagesByAccount(id);
        if(messageList == null){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(messageList);
    }
}