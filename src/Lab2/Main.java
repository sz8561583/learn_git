package Lab2;

import java.io.File;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("请输入密钥的长度（推荐1024）：");
//        int N = sc.nextInt();
//        sc.nextLine();
//        BigInteger P = BigInteger.probablePrime(N / 2, new Random());
//        BigInteger Q = BigInteger.probablePrime(N / 2 + 1, new Random());
//        RSA rsa = new RSA(P, Q);
//        System.out.println("生成公钥为：");
//        System.out.println(rsa.getPublicKey());
//        System.out.println("生成私钥为：");
//        System.out.println(rsa.getPrivateKey());
//
//        System.out.println("请输入你要加密的消息：");
//        String s1 = sc.nextLine();
//        System.out.println("加密结果为(Base64)： " + rsa.getCipherText(s1));
//        System.out.println("请输入你要解密的消息：");
//        String s2 = sc.nextLine();
//        System.out.println("解密结果为： " + rsa.getPlainText(s2));
//
//        System.out.println("请输入你要加密的文件的绝对路径：");
//        String f1 = sc.nextLine();
//        System.out.println("正在加密中……请勿输入");
//        rsa.encryptFile(new File(f1));
//        System.out.println("已在当前文件夹中生成加密文件");
//        System.out.println("请输入你要解密的文件的绝对路径：");
//        String f2 = sc.nextLine();
//        rsa.decryptFile(new File(f2));
//        System.out.println("正在解密中……请勿输入");
//        System.out.println("已在当前文件夹中生成解密文件");
//
//        System.out.println("请输入要生成消息摘要的文件的绝对路径：");
//        String f3 = sc.nextLine();
//        System.out.println("生成的数字签名为： " + rsa.signature(new File(f3)));
//        System.out.println("请输入要验证消息摘要的文件的绝对路径：");
//        String f4 = sc.nextLine();
//        System.out.println("请输入要验证的数字签名");
//        String signature = sc.nextLine();
//        System.out.println("数字签名验证： " + rsa.confirm(new File(f4), signature));

        RSA rsa = new RSA(new BigInteger("7"), new BigInteger("11"));
        System.out.println(rsa.getPlain(new BigInteger("10")));

    }
}
