package com.cyl.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author CYL
 */
public class CommunityUtil {

  /**
   * 生成随机字符串
   *
   * @return
   */
  public static String generateUUID(){
    return UUID.randomUUID().toString().replaceAll("-","");
  }

  /**
   * MD5加密 只能加密 不能解密 密码+随机字符串->再加密
   *
   * @param key
   * @return
   */
  public static String md5(String key){
    //null的情况： null  空字符串 空格
    if(StringUtils.isBlank(key)){
      return null;
    }
    //md5DigestAsHex 结果加密然后转化成16进制
    return DigestUtils.md5DigestAsHex(key.getBytes());
  }

  public static String getJSONString(int code, String msg, Map<String, Object> map) {
    JSONObject json = new JSONObject();
    json.put("code", code);
    json.put("msg", msg);
    if (map != null) {
      for (String key : map.keySet()) {
        json.put(key, map.get(key));
      }
    }
    return json.toJSONString();
  }

  public static String getJSONString(int code, String msg) {
    return getJSONString(code, msg, null);
  }

  public static String getJSONString(int code) {
    return getJSONString(code, null, null);
  }

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "zhangsan");
    map.put("age", 25);
    System.out.println(getJSONString(0, "ok", map));
  }

}
