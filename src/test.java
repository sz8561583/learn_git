import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.logging.*;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) throws IOException {

        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = "228";
        final byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
//编码

        final String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);
//解码
        System.out.println(new String(decoder.decode(encodedText), "UTF-8"));

//        int[] a = {1,2,3,4};
//        int[] b =Arrays.copyOfRange(a,0,6);
//        System.out.println(Arrays.toString(b));

//        String str = "abcdddddddddddddddddddddd";
//        while (str.endsWith("d")) {
//            str = str.substring(0, str.length() - 1);
//            System.out.println(str);
//        }


//        String tmp = new String(new byte[]{0});
//        System.out.println(tmp);
//
//        Logger log = Logger.getLogger("com");
//        log.setLevel(Level.FINE);
//        FileHandler fileHandler = new FileHandler("testlog.log");
//        fileHandler.setLevel(Level.FINE);
//        fileHandler.setFormatter(new SimpleFormatter());
//        log.addHandler(fileHandler);
//        log.fine("第一条日志");
//
//        System.out.println("hello");
//
//        String str = "ABC";
//        byte[] a = str.getBytes();
//        System.out.println(Arrays.toString(a));
////
////        String str2 =new String(a);
////        System.out.println(str2);
//        System.out.println(new BigInteger(a));
//
//        BigInteger temp = new BigInteger("-4");
//        System.out.println(temp.modPow(new BigInteger("3"), new BigInteger("7")));
//
//        System.out.println(new BigInteger("-64").mod(new BigInteger("7")));
//
////        System.out.println(new BigInteger("2").pow(1024));
//
//        System.out.println(BigInteger.probablePrime(500, new Random()));
//
////        System.out.println(new BigInteger("859391035316510057922701709567709187062328466713643235311192").gcd(new BigInteger("7")));
//
////        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("src\\ABC.txt"));
////        BufferedOutputStream bos =new BufferedOutputStream(new FileOutputStream("src\\ABC_RSA.txt"));
////        int len;
////        byte[] b = new byte[1024];
////        while ((len = bis.read(b)) != -1) {
////            bos.write(b,0,len);
////        }
////        bos.flush();
//
//        int[] arr = {1, 2, 3, 4};
//        int[] arr2 = new int[10];
//        System.arraycopy(arr, 0, arr2, 3, arr.length);
//        System.out.println(Arrays.toString(arr2));

    }
}
