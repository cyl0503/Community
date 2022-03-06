package com.cyl;

import com.cyl.utils.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Author CYL
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

  @Autowired
  private MailClient mailClient;

  @Autowired
  private TemplateEngine templateEngine;

  @Test
  public void testTextMail(){
    mailClient.sendMail("1505386118@qq.com","day day up","happy everyday!");
  }

  @Test
  public void testHtmlMail(){
    //thymeleaf下
    Context context = new Context();
    context.setVariable("username","rachel");

    //生成html字符串
    String content = templateEngine.process("/mail/demo",context);
    System.out.println(content);

    //发送
    mailClient.sendMail("1505386118@qq.com","Html",content);
  }
}
