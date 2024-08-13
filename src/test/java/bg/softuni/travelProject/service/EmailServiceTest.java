package bg.softuni.travelProject.service;

import static org.junit.jupiter.api.Assertions.*;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    private static final String RECEIVER_EMAIL = "admin@example.com";

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private Integer mailPort;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(mailPort, mailHost, "smtp"));
        greenMail.start();
        greenMail.setUser(mailUsername, mailPassword);
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    public void testSendSimpleMessage() throws MessagingException, IOException {

        String subject = "Test Subject";
        String text = "Test Message";

        emailService.sendSimpleMessage(RECEIVER_EMAIL, subject, text);

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);

        MimeMessage message = messages[0];
        assertEquals(RECEIVER_EMAIL, message.getAllRecipients()[0].toString());
        assertEquals(subject, message.getSubject());
        assertEquals(text, message.getContent());
    }
}