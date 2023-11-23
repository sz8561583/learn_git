package Lab2;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class RSA {
    // Base64编码和解码
    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();

    // 两个质数P、 Q
    final BigInteger P;
    final BigInteger Q;

    // T = (P - 1) * (Q - 1)
    final BigInteger T;

    // N = P * Q
    final BigInteger N;

    // 公钥 (N, E)
    // E < T && gcd(E, T) = 1 即E和T的最大公约数为1（互质）
    BigInteger E;

    // 私钥(N, D)
    // D * E mod T = 1 即DE对T取模等于1
    // Java中%是取余，取余和取模略有不同，但两个数均为正数时结果一致
    BigInteger D;

    //一次处理多少字节
    int group;

    // C = M ^ E mod N
    // M = C ^ D mod N
    public RSA(BigInteger p, BigInteger q) {
        P = p;
        Q = q;
        N = P.multiply(Q);
        T = (P.subtract(BigInteger.ONE)).multiply((Q.subtract(BigInteger.ONE)));
        group = N.bitLength() / 8;


        E = getE();
        System.out.println(getPublicKey());
        D = getD();
        System.out.println(getPrivateKey());

    }

    // 欧几里得算法求余数（辗转相除法）
    // 两个整数的最大公约数等于其中较小的数和两数相除余数的最大公约数
    // 任何数和1的最大公约数是1，任何数取模1都是0
    public boolean isCoprime(BigInteger x, BigInteger y) {
        BigInteger max = x.max(y);
        BigInteger min = x.min(y);
        BigInteger remainder;
        while (!max.mod(min).equals(BigInteger.ZERO)) {
            remainder = max.mod(min);
            max = min;
            min = remainder;
        }
        return min.equals(BigInteger.ONE);
    }

    // E < T || gcd(E, T) = 1 即E和T的最大公约数为1（互质）
    public BigInteger getE() {
        E = BigInteger.TWO;
        for (BigInteger i = BigInteger.TWO; i.compareTo(T) < 0; i = i.add(BigInteger.ONE)) {
            if (i.gcd(T).compareTo(BigInteger.ONE) == 0) {
                E = i;
                return E;
            }
        }
        return E;
    }

    // D * E mod T = 1 即DE对T取模等于1
    public BigInteger getD() {
//        D = BigInteger.ONE;
//        for (BigInteger i = BigInteger.ONE; i.compareTo(T) < 0; i = i.add(BigInteger.ONE)) {
//            if ((i.multiply(E).mod(T).compareTo(BigInteger.ONE)) == 0) {
//                D = i;
//                return D;
//            }
//        }
//        return D;
        BigInteger[] ret = ex_gcd(E, T);
        if (ret[1].compareTo(BigInteger.ZERO) < 0)
            return T.add(ret[1]);
        return ret[1];
    }

    public BigInteger[] ex_gcd(BigInteger a, BigInteger b, BigInteger[] x, BigInteger[] y) {
        BigInteger[] ret = new BigInteger[3];

        if (b.compareTo(BigInteger.ZERO) == 0) {
            ret[0] = a;
            ret[1] = x[0];
            ret[2] = y[0];
            return ret;
        }
        BigInteger q = a.divide(b);
        BigInteger tx1 = x[0].subtract(q.multiply(x[1]));
        BigInteger ty1 = y[0].subtract(q.multiply(y[1]));
        BigInteger[] tx = {x[1], tx1};
        BigInteger[] ty = {y[1], ty1};
        return ex_gcd(b, a.mod(b), tx, ty);
    }

    public BigInteger[] ex_gcd(BigInteger a, BigInteger b) {
        BigInteger[] x = {BigInteger.ONE, BigInteger.ZERO};
        BigInteger[] y = {BigInteger.ZERO, BigInteger.ONE};
        return ex_gcd(a, b, x, y);
    }

    public BigInteger getCipher(BigInteger M) {
        // C = M ^ E mod N
        if (M.compareTo(BigInteger.ZERO) < 0)
            return M.negate().modPow(E, N).negate();
        return M.modPow(E, N);
    }

    public BigInteger getPlain(BigInteger C) {
        // M = C ^ D mod N
        if (C.compareTo(BigInteger.ZERO) < 0)
            return C.negate().modPow(D, N).negate();
        return C.modPow(D, N);
    }

    public String getCipherText(String plainText) {
        BigInteger cipher = getCipher(new BigInteger(plainText.getBytes(StandardCharsets.UTF_8)));
        return encoder.encodeToString(cipher.toByteArray());
    }

    public String getPlainText(String cipherText) {
        BigInteger plain = getPlain(new BigInteger(decoder.decode(cipherText)));
        return new String(plain.toByteArray());

    }

    public String getPublicKey() {
        return "(" + N + ", " + E + ")";
    }

    public String getPrivateKey() {
        return "(" + N + ", " + D + ")";
    }

    public void encryptFile(File M) {
        try {
            //字节缓冲输入流读取明文（待加密文件）
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(M));
            //获取文件的父级路径
            String parentPath = M.getParent();
            //获取文件名（不带扩展）
            String fileName = M.getName().substring(0, M.getName().lastIndexOf("."));
            //获取文件的扩展名
            String extendName = M.getName().substring(M.getName().lastIndexOf("."));
            //加密后密文的地址
            File C = new File(parentPath + "\\" + fileName + "_RSA" + extendName);

            //字节缓冲输出流打印密文（加密后文件）
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(C));
            //字符缓冲输出流打印密文（加密后文件）
            BufferedWriter bw = new BufferedWriter(new FileWriter(C));

            int b;
            while ((b = bis.read()) != -1) {
                BigInteger ciphertext = getCipher(new BigInteger(String.valueOf(b)));
//                System.out.println(b + " " + ciphertext);

                String text = ciphertext.toString();
                byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
                String encodedText = encoder.encodeToString(textByte);

                bw.write(encodedText, 0, encodedText.length());
                bw.newLine();
            }
            //清空缓冲区
            bw.flush();

            //关闭缓冲流
            bw.close();
            bis.close();
        } catch (IOException e) {
            System.out.println("指定路径文件不存在");
        }
    }

    public void decryptFile(File C) {
        try {
            //字节缓冲输入流读取密文（加密文件）
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(C));
            //字符缓冲输入流读取密文（加密文件）
            BufferedReader br = new BufferedReader(new FileReader(C));
            //获取文件的父级路径
            String parentPath = C.getParent();
            //获取文件名（不带扩展）
            String fileName = C.getName().substring(0, C.getName().lastIndexOf("."));
            //获取文件的扩展名
            String extendName = C.getName().substring(C.getName().lastIndexOf("."));
            //解密后明文的地址
            File M = new File(parentPath + "\\" + fileName + "_~RSA" + extendName);

            //字节缓冲输出流打印明文（解密文件）
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(M));

            String line;
            while ((line = br.readLine()) != null) {
                String decodedText = new String(decoder.decode(line), StandardCharsets.UTF_8);
                BigInteger plaintext = getPlain(new BigInteger(decodedText));
                byte[] b = plaintext.toByteArray();
                //Java没有无符号整数
                if (b.length > 1)
                    b = Arrays.copyOfRange(b, b.length - 1, b.length);
//                System.out.println(Arrays.toString(b));
                bos.write(b, 0, b.length);
            }
            //清空缓冲区
            bos.flush();

            //关闭缓冲流
            bos.close();
            br.close();
        } catch (IOException e) {
            System.out.println("指定路径文件不存在");
        }
    }

    public String signature(File file) {
        String digest = GetFileSHA256.getFileSHA1(file);
        System.out.println("文件的消息摘要为（SHA_256）: " + digest);
        return getCipherText(digest);
    }

    public boolean confirm(File file, String signature) {
        String digest_1 = GetFileSHA256.getFileSHA1(file);
        System.out.println("文件的消息摘要为（SHA_256）: " + digest_1);
        String digest_2 = getPlainText(signature);
        System.out.println("解密数字签名结果为：" + digest_2);
        return digest_1.equals(digest_2);
    }
}
