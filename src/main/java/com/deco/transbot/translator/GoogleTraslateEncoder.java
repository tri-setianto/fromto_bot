package com.deco.transbot.translator;

import java.util.HashMap;
import java.util.Map;

public class GoogleTraslateEncoder {

  private static String[][] charList = {
    {"!", " xzz21"},
    {"#", " xzz22"},
    {"$", " xzz24"},
    {"%", " xzz25"},
    {"&", " xzz26"},
    {"'", " xzz27"},
    {"(", " xzz28"},
    {")", " xzz29"},
    {"*", " xzz2a"},
    {"+", " xzz2b"},
    {",", " xzz2c"},
    {"-", " xzz2d"},
    {".", " xzz2e"},
    {"/", " xzz2f"},
    {":", " xzz3a"},
    {";", " xzz3b"},
    {"<", " xzz3c"},
    {"=", " xzz3d"},
    {">", " xzz3e"},
    {"?", " xzz3f"},
    {"@", " xzz40"},
    {"^", " xzz5e"},
    {"_", " xzz5f"},
    {"`", " xzz60"},
    {"\n", " xzz0a"},
  };
  private static Map<Character, String> encodeCharMap;
  private static Map<String, Character> decodeCharMap;

  static {
    encodeCharMap = new HashMap<>(charList.length);
    decodeCharMap = new HashMap<>(charList.length);
    for (String[] aCharList : charList) {
      encodeCharMap.put(aCharList[0].charAt(0), aCharList[1]);
      decodeCharMap.put(aCharList[1], aCharList[0].charAt(0));
    }
  }

  public static String encode(String s) {
    boolean needToChange = false;
    StringBuilder out = new StringBuilder(s.length());

    for (int i = 0; i < s.length(); i++) {
      if (encodeCharMap.containsKey(s.charAt(i))) {
        needToChange = true;
        out.append(encodeCharMap.get(s.charAt(i)));
      } else {
        out.append(s.charAt(i));
      }
    }

    return needToChange ? out.toString() : s;
  }

  public static String decode(String s) {
    boolean needToChange = false;
    StringBuilder out = new StringBuilder(s.length());

    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == ' ' && s.length() > i + 5) {
        String subStr = s.substring(i, i + 6);
        // System.out.println("i" + subStr);
        if (decodeCharMap.containsKey(subStr)) {
          needToChange = true;
          out.append(decodeCharMap.get(subStr));
          i += 5;
        } else {
          out.append(s.charAt(i));
        }
      } else {
        out.append(s.charAt(i));
      }
    }

    return needToChange ? out.toString() : s;
  }

  public static void main(String[] args) {
    String text = "bahasa-bahasa lain, ";
    String strEco = encode(text);
    String strDeco = decode(strEco);
    System.out.println(text);
    System.out.println(strEco);
    System.out.println(strDeco);
  }
}
