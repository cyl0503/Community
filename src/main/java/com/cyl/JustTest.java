package com.cyl;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author CYL
 */
public class JustTest {

  private static class TestClass{

  }

  public static void main(String[] args) {
    TestClass t = new TestClass();
    //Object o = new Object();

    System.out.println(ClassLayout.parseInstance(t).toPrintable());

    synchronized (t){
      System.out.println(ClassLayout.parseInstance(t).toPrintable());
    }
    System.out.println(ClassLayout.parseInstance(t).toPrintable());
  }
}
