package hey.io.hey.domain.fcm.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import hey.io.hey.domain.fcm.dto.MessageRequestDTO;
import hey.io.hey.domain.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/message/fcm/topic")
    public ResponseEntity sendMessageTopic(@RequestBody MessageRequestDTO requestDTO) throws IOException, FirebaseMessagingException{
        fcmService.sendMessageByTopic(requestDTO.getTitle(), requestDTO.getBody(), requestDTO.getTopic());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/message/fcm/token")
    public ResponseEntity sendMessageToken(@RequestBody MessageRequestDTO requestDTO) throws IOException, FirebaseMessagingException{
        fcmService.sendMessageByToken(requestDTO.getTitle(), requestDTO.getBody(), requestDTO.getTargetToken());
        return ResponseEntity.ok().build();
    }
}
