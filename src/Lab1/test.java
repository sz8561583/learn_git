package Lab1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class test {

    public static void main(String[] args) throws IOException {
//        int a = -68;
//        String s = Integer.toBinaryString(a);
//        System.out.println(s.substring(s.length() - 8));
//
////        -28 -67 -96 -27 -91 -67 -17 -68
////        1 1 1 0 0 1 0 0
////        1 0 1 1 1 1 0 1
////        1 0 1 0 0 0 0 0
////        1 1 1 0 0 1 0 1
////        1 0 1 0 0 1 0 1
////        1 0 1 1 1 1 0 1
////        1 1 1 0 1 1 1 1
////        1 0 1 1 1 1 0 0
//
//        byte[] M = {1, 2, 3, 4, 5};
//        t(M);
//        System.out.println(M[0]);
//
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\Users\\handsome boy\\IdeaProjects\\computer_security\\初始文件.txt"));
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\handsome boy\\IdeaProjects\\computer_security\\加密文件.txt"));
//        int len;
//        byte[] b = new byte[1024];
//
//        while ((len = bis.read(b)) != -1) {
//            bos.write(b, 0, len);
//        }
//        bos.flush();
//
//        byte[] a = {1, 2, 3, 4};
//        byte[] b = {5, 6, 7, 8};
//        byte[] c = new byte[a.length + b.length];
//        System.arraycopy(a, 0, c, 0, a.length);
//        System.arraycopy(b, 0, c, a.length, b.length);
//
//
//        for (int i = 0; i < c.length; i++) {
//            System.out.println(c[i]);
//        }

//        String str= "你好";
//        byte[] bytes = str.getBytes("UTF-8");
//        for (int i = 0; i < bytes.length; i++) {
//            System.out.println(Integer.toBinaryString(bytes[i]));
//        }

//        String s1="\u0014���\"Ŵf";
//        String s2 = "6xw9Jy7I7RO0WfaS87eQ4Q==";
//
//        DES D =new DES();
//        D.encryptString(s1,"SOFTWARE");
//        D.decryptString(s2,"SOFTWARE");

        byte[] a = {20, -96, -28, -33, 34, -59, -76, 102}; //a.length = 8
        String str = new String(a, StandardCharsets.ISO_8859_1);
        System.out.println(str + " " + str.length() + (int) str.charAt(str.length() - 1));
        System.out.println(Arrays.toString(str.getBytes(StandardCharsets.ISO_8859_1)));

        byte[] b = {1, 2, 3, 4, 5, 0, 0};
        while (b[b.length - 1] == 0) {
            b = Arrays.copyOf(b, b.length - 1);
            System.out.println(Arrays.toString(b));
        }
    }

    public static byte[] r() {
        byte[] r = {100, 101, 102, 103};
        return r;
    }

    public static byte[] t(byte[] M) {
        byte[] C = {1, 2};
        M[0] = 100;
        return C;
    }
}
