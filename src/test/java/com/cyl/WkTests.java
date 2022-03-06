package com.cyl;

import java.io.IOException;

/**
 * @Author CYL
 */
public class WkTests {

  public static void main(String[] args) {
    String cmd = "D:/software/wkhtmltopdf/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com d:/1.png";
    try {
      Runtime.getRuntime().exec(cmd);
      System.out.println("ok.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
