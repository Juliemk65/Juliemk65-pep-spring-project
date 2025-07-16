package com.example.service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){

        if(message.getMessageText().isBlank()){
            throw new IllegalArgumentException("Message cannot be blank");
        }

          if(message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Message cannot be over 255 chars");
        }

        if(!messageRepository.existsById(message.getPostedBy())){
           throw new IllegalArgumentException("PostedBy does not exist");
        }

        return messageRepository.save(message);
    }

    public List<Message> retrieveAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> retrieveAMessageUsingId(Integer messageId){
        return messageRepository.findByMessageId(messageId);
        
    }

    public List<Message> retrieveAllMessagesForUser(Integer accountId){
        return messageRepository.findAllByPostedBy(accountId);

    }

    public void updateMessage(Integer messageId, String newMsgText){
         if(newMsgText.isBlank()){
            throw new IllegalArgumentException("Message cannot be blank");
        }

          if(newMsgText.length() > 255){
            throw new IllegalArgumentException("Message cannot be over 255 chars");
        }

        Optional<Message> updatedMsg = messageRepository.findById(messageId);
        if(updatedMsg.isEmpty()){
           throw new IllegalArgumentException("MessageId does not exist");
        }
        Message message = updatedMsg.get();
        message.setMessageText(newMsgText);
       messageRepository.save(message);
    }

     public Optional<Message> deleteMessage(Integer messageId){
        Optional<Message> messageToBeDeleted = messageRepository.findByMessageId(messageId);
        if(messageToBeDeleted.isPresent()){
            messageRepository.delete(messageToBeDeleted.get());
        }
        return messageToBeDeleted;
     }
}
