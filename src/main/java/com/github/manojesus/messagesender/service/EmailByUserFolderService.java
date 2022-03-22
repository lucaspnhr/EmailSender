package com.github.manojesus.messagesender.service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.github.manojesus.messagesender.model.EmailByUserFolder;
import com.github.manojesus.messagesender.model.Message;
import com.github.manojesus.messagesender.model.primarykey.EmailByUserFolderPrimaryKey;
import com.github.manojesus.messagesender.repository.EmailByUserFolderRepository;
import lombok.AllArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmailByUserFolderService {

    private final EmailByUserFolderRepository emailByUserFolderRepository;


    public List<EmailByUserFolder> findAllByUserAndLabelName(String userId, String labelName){
        return emailByUserFolderRepository.findAllByKey_UserIdAndKey_LabelName(userId,labelName)
                .stream()
                .peek(email -> {
                    PrettyTime prettyTime = new PrettyTime();
                    UUID messageId = email.getKey().getMessageId();
                    Date date = new Date(Uuids.unixTimestamp(messageId));
                    email.setEmailSentTime(prettyTime.format(date));
                }).collect(Collectors.toList());
    }

    public void saveMessageInFolderList(String label, Message message){
        EmailByUserFolder emailToBeSavedOnList = new EmailByUserFolder();
        final EmailByUserFolderPrimaryKey emailByUserFolderPrimaryKey = new EmailByUserFolderPrimaryKey(message.getFrom(), label, message.getMessageId());

        emailToBeSavedOnList.setKey(emailByUserFolderPrimaryKey);
        emailToBeSavedOnList.setSubject(message.getSubject());
        emailToBeSavedOnList.setTo(message.getTo());
        emailToBeSavedOnList.setRead(false);
        emailToBeSavedOnList.setEmailSentTime("");

        emailByUserFolderRepository.save(emailToBeSavedOnList);
    }
}
