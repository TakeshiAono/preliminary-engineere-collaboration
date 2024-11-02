package com.api.EngineerCollabo.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
  // 入力文字列をハッシュ化し、63文字以内のバケット名に適した形式に変換
  public static String hashAndAdjustForBucketName(String input) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

    // ハッシュ値を16進数文字列に変換
    StringBuilder hexString = new StringBuilder();
    for (byte b : hashBytes) {
        hexString.append(String.format("%02x", b));
    }

    // バケット名の制約を満たすように調整
    String bucketName = hexString.toString();
    bucketName = "project-" + bucketName.substring(0, Math.min(bucketName.length(), 63 - 8)); // 63文字以内に制限
    return bucketName;
  }
}