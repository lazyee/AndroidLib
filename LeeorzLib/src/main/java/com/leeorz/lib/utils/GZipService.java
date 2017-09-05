package com.leeorz.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// 将一个字符串按照zip方式压缩和解压缩
public class GZipService {

    // 压缩
    public static String compress(String str) throws IOException {
        return compress(str, "UTF-8");

    }

    public static String compress(String str, String charsetName) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        return compressBytes2String(str.getBytes(), charsetName);
    }

    public static String compressBytes2String(byte[] content) throws IOException {
        return compressBytes2String(content, "UTF-8");
    }

    public static String compressBytes2String(byte[] content, String charsetName) throws IOException {
        ByteArrayOutputStream stream = compressBytes2Stream(content);
        if (stream == null) {
            return "";
        }
        return stream.toString(charsetName);
    }

    public static byte[] compressBytes2Bytes(byte[] content) throws IOException {
        ByteArrayOutputStream stream = compressBytes2Stream(content);
        if (stream == null) {
            return null;
        }
        return stream.toByteArray();
    }

    static ByteArrayOutputStream compressBytes2Stream(byte[] content) throws IOException {
        if (null == content || content.length <= 0) {
            return null;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(content);
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out;

    }

    public static byte[] compress2Bytes(String str) throws IOException {
        return compress2Bytes(str, "UTF-8");
    }

    public static byte[] compress2Bytes(String str, String charsetName) throws IOException {
        if (null == str || str.length() <= 0) {
            return null;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(str.getBytes(charsetName));
        gzip.close();
        return out.toByteArray();
    }

    public static byte[] compress2Bytes(byte[] content) throws IOException {
        if (null == content || content.length <= 0) {
            return null;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(content);
        gzip.close();
        return out.toByteArray();
    }

    public static byte[] uncompressBytes(byte[] content) throws IOException {
        if (null == content || content.length <= 0) {
            return null;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
            out.write(buffer, 0, n);
        }
        return out.toByteArray();

    }

    public static String uncompressString2String(String str) throws IOException {
        return uncompressString2String(str, "UTF-8");
    }

    public static String uncompressString2String(String str, String charsetName) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        return uncompressBytesToString(str.getBytes(charsetName));
    }

    public static byte[] uncompressString2Bytes(String str) throws IOException {
        return uncompressString2Bytes(str, "UTF-8");
    }

    public static byte[] uncompressString2Bytes(String str, String charsetName) throws IOException {
        if (null == str || str.length() <= 0) {
            return null;
        }
        return uncompressBytes(str.getBytes(charsetName));
    }

    public static String uncompressBytesToString(byte[] content) throws IOException {
        return uncompressBytesToString(content, "UTF-8");
    }

    public static String uncompressBytesToString(byte[] content, String charsetName) throws IOException {
        byte[] result = uncompressBytes(content);
        if (result == null) {
            return "";
        }
        return StringHelper.bytesToString(result, charsetName);
    }

    // 测试方法
    public static void main(String[] args) throws IOException {
        String sourceStr = "中国China<适用性信息 标识=\"R1\"><显示文本 /></适用性信息>";
        System.out.println(GZipService.uncompressBytesToString(GZipService.compress2Bytes(sourceStr)));

        String dd = "H4sIAAAAAAAEAOy9B2AcSZYlJi9tynt/SvVK1+B0oQiAYBMk2JBAEOzBiM3mkuwdaUcjKasqgcplVmVdZhZAzO2dvPfee++999577733ujudTif33/8/XGZkAWz2zkrayZ4hgKrIHz9+fB8/Ih7/13/QH/Jf/ml/1X/xB/2V/9k/+Bf/F3/w35j+F3/RH/lf/Y1/xGcfvdr96Ojxf/Fn/QP/5V/29/wXf8Yf+V/8eX9devfo8d1O66P/JwAA//9Yo196PwAAAA==";
        byte[] ere = StringHelper.decodeBase64(dd);
        System.out.println(GZipService.uncompressBytesToString(ere, "utf-8"));

    }

}
