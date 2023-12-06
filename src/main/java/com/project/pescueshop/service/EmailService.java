package com.project.pescueshop.service;

import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.util.constant.EnumResponseCode;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${MAIL_USERNAME}")
    private String sender;

    public void sendMail(String receiverEmail, String emailBody, String subject) throws FriendlyException {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sender);
            helper.setTo(receiverEmail);
            helper.setSubject(subject);

            boolean html = true;

            helper.setText(
                    "<body style='width: 100%;'>" +
                            "  <div class='header'>" +
                            "          <img src='https://firebasestorage.googleapis.com/v0/b/advance-totem-350103.appspot.com/o/Logo%2FMail_Banner.png?alt=media&token=657ab2e2-addb-4545-9476-1b4e730c79ff' style='border:0; display:block; outline:none; text-decoration:none; height:auto; min-width:60%; width:60%; max-width:60%; font-size:13px; margin:auto;'>" +
                            "      </div>" +
                            "      <div style='padding: 20px; width: 100%;'>" +
                            "         <div style='margin: auto; padding: 20px; min-width:60%; width:60%; max-width:60%; font-size: 16px;'>" +
                            "             <p>" + emailBody + "</p>" +
                            "         </div>" +
                            "      </div>" +
                            "      <div style='width: 100%; padding: 20px;' class='footer'>" +
                            "         <div style='margin: auto; padding: 20px; min-width:60%; width:60%; max-width:60%; font-weight: 700; font-size: 16px; color: #7A3D26; border-top: 1px solid black;'>" +
                            "             <p>Address: Ho Chi Minh city, Viet Nam</p>" +
                            "             <p>Phone Number: +84 902-309-287</p>" +
                            "         </div>" +
                            "      </div>" +
                            "  </body>", html);

            javaMailSender.send(message);

            log.trace("Mail has been sent to: " + receiverEmail);

        }
        catch (Exception e){
            throw new FriendlyException(EnumResponseCode.MAIL_SENT_FAIL);
        }
    }
}
