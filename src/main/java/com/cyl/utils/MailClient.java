package com.cyl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author CYL
 */

@Component
public class MailClient {

  private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

  /**
   * 发送邮件的
   */
  @Autowired
  private JavaMailSender mailSender;

  /**
   * 发件人 配置文件中注入
   */
  @Value("${spring.mail.username}")
  private String from;


  /**
   * 封装发邮件方法
   *
   * @param to 收件人
   * @param subject 发件标题
   * @param content 发件内容
   */
  public void sendMail(String to, String subject, String content) {
    try {
      //创建模板 空的
      MimeMessage message = mailSender.createMimeMessage();
      //帮助构建MineMessage内容
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      //支持发送html文件
      helper.setText(content, true);
      mailSender.send(helper.getMimeMessage());
    } catch (MessagingException e) {
      logger.error("发送邮件失败:" + e.getMessage());
    }
  }
}
