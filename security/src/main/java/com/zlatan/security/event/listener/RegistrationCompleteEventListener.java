package com.zlatan.security.event.listener;

import com.zlatan.security.entity.User;
import com.zlatan.security.event.RegistrationCompleteEvent;
import com.zlatan.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j //in nhật ký
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create mã thông báo xác mình cho người dùng token
        //token đk tạo sẽ đính kèm vào url
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        // khi ng dùng ấn vào url đó sẽ chuyển đến xác minhs
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        log.info("Nhấn vào liên kết để xác minh tài khoản: {}",url);
    }
}
