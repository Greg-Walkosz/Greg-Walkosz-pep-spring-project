package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;



@Service
public class MessageService {
    
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getallMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getallMessagesByAccount(Integer postedBy){
        return messageRepository.findByPostedBy(postedBy);
    }

    public Optional<Message> getMessageById(Integer messageId){
        return messageRepository.findById(messageId);
    }

    public Optional<Message> dltMessageById(Integer messageId) {
        Optional<Message> temp = messageRepository.findById(messageId);
        if (temp.isPresent()) {
            messageRepository.deleteById(messageId);
        }
        return temp;
    }

    public Message postMessage(Message message){
        if(message.getMessageText().length() < 255 && message.getMessageText() != ""){
            return messageRepository.save(message);
        }else{
            return null;
        }
    }

    public Integer updateMessage(Message newMessage, int message_id) {
        if (newMessage == null || newMessage.getMessageText().isEmpty() || newMessage.getMessageText().length() > 255) {
            return 0;
        }
    
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newMessage.getMessageText());
            messageRepository.save(message);
            return 1;
        } else {
            return 0;
        }
    }
    
    
    
}
