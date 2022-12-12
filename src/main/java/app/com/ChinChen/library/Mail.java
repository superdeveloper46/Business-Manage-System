package app.com.ChinChen.library;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.List;

public class Mail {
    /**
     * 寄信
     *
     * @param title      寄件人名稱
     * @param subject    標題
     * @param text       信件內容
     * @param mailList   收信人
     * @param mailSender JavaMailSender
     */
    public void SendMail(String title, String subject, String text, List<String> mailList, JavaMailSender mailSender) {
        try {
            MimeMessagePreparator mailMessage = mimeMessage -> {
                MimeMessageHelper message = new MimeMessageHelper(
                        mimeMessage, true);
                for (String mail : mailList) {
                    message.addTo(mail);
                }
                message.setFrom("coldchain021@gmail.com", title); // Here comes your name
                message.setSubject(subject);
                message.setText(text);
            };
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("寄信失敗：" + e.getMessage());
        }
    }
}
