package Lab1;


import java.io.*;
import java.util.Arrays;

public class DES {
    //置换IP表 64bits
    final static int[] Ip_Table = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    //PC1选位表 56bits
    final static int[] PC1_Table = {
            57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4
    };
    //左移位数表 16bits
    final static int[] Loop_Table = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    //PC2选位表 48bits
    final static int[] PC2_Table = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    //E扩展置换表 48bits
    final static int[] Extend_Table = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    //S盒 8个4*16的表格
    final static int[][][] S_Box = {
            //S1
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            //S2
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },

            //S3
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            //S4
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },


            //S5
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },

            //S6
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            //S7
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },


            //S8
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }


    };

    //P换位表 32bits
    final static int[] P_Table = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };
    //逆置换IP^-1表 64bits
    final static int[] Ip_Reverse_Table = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    private String seed_key;
    private byte[][] encrypt_keys = new byte[16][48];
    private byte[][] decrypt_keys = new byte[16][48];
    private File srcFile;
    private File desFile;
    private byte[] ciphertext;
    private byte[] plaintext;

    private long group;


    DES() {

    }

    public byte[] encryptString(byte[] plaintext, String seed_key) {
        System.out.println("--------encryptString--------");
        //初始化
        this.plaintext = plaintext;
        this.seed_key = seed_key;
        //生成16个不同的48bits的子密钥
        encrypt_keys = generateKey(seed_key, 1);
        //转换成字节的明文
        byte[] M = plaintext;
        System.out.println("M: " + Arrays.toString(M));
        //分组的个数，要特别注意plaintext.length != plaintext.getBytes().length != M.length三者的不同
        group = M.length / 8;
        if (M.length % 8 > 0) {
            group++;
        }
        //不同分组的字节形式的密文的集合
        byte[] C = new byte[8 * (int) group];
        for (int i = 0; i < group; i++) {
            //不足8位会填充‘0’
            byte[] M_8 = Arrays.copyOfRange(M, 8 * i, 8 * i + 8);
            System.out.println("M_8: " + Arrays.toString(M_8));
            byte[] M_64 = getBits(M_8);
            byte[] C_64 = iteration(M_64, encrypt_keys);
            byte[] C_8 = getBytes(C_64);
            System.arraycopy(C_8, 0, C, 8 * i, C_8.length);
            System.out.println("C_8: " + Arrays.toString(C_8));
        }
        ciphertext = C;
        System.out.println("ciphertext: " + Arrays.toString(ciphertext));
        System.out.println("--------encryptString--------");
        System.out.println();
        return ciphertext;
    }

    public byte[] decryptString(byte[] ciphertext, String seed_key) {
        System.out.println("--------decryptString--------");
        //初始化
        this.ciphertext = ciphertext;
        this.seed_key = seed_key;
        //生成16个不同的48bits的子密钥
        decrypt_keys = generateKey(seed_key, 0);
        //密文转换为字节密文
        byte[] C = ciphertext;
        System.out.println("C: " + Arrays.toString(C));
        //分组的个数，要特别注意 C.length != ciphertext.getBytes().length
        group = C.length / 8;
        //不同分组的字节形式的明文的集合
        byte[] M = new byte[8 * (int) group];
        for (int i = 0; i < group; i++) {
            byte[] C_8 = Arrays.copyOfRange(C, 8 * i, 8 * i + 8);
            System.out.println("C_8: " + Arrays.toString(C_8));
            byte[] C_64 = getBits(C_8);
            byte[] M_64 = iteration(C_64, decrypt_keys);
            byte[] M_8 = getBytes(M_64);
            System.out.println("M_8: " + Arrays.toString(M_8));
            System.arraycopy(M_8, 0, M, 8 * i, M_8.length);
        }


        //去掉填充的0字节
        while (M[M.length - 1] == 0) {
            M = Arrays.copyOf(M, M.length - 1);
            System.out.println(Arrays.toString(M));
        }
        plaintext = M;

        System.out.println("plaintext: " + Arrays.toString(plaintext));
        System.out.println("--------decryptString--------");
        System.out.println();
        return plaintext;
    }

    public byte[] tripleEncryptString(byte[] original_text, String[] seed_key) {
        ciphertext = encryptString(original_text, seed_key[0]);
        plaintext = decryptString(ciphertext, seed_key[1]);
        ciphertext = encryptString(plaintext, seed_key[2]);
        return ciphertext;
    }

    public byte[] tripleDecryptString(byte[] original_text, String[] seed_key) {
        plaintext = decryptString(original_text, seed_key[2]);
        ciphertext = encryptString(plaintext, seed_key[1]);
        plaintext = decryptString(ciphertext, seed_key[0]);
        return plaintext;
    }

    public void encryptFile(File srcFile, File desFile, String seed_key) throws IOException {
        //初始化
        this.srcFile = srcFile;
        this.desFile = desFile;
        this.seed_key = seed_key;
        //生成16个不同的48bits的子密钥
        encrypt_keys = generateKey(seed_key, 1);
        //读写文件
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        //获取分组数
        group = srcFile.length() / 8;
        if ((srcFile.length() % 8) > 0) {
            group++;
        }
        //8字节一组的明文
        byte[] M_8 = new byte[8];
        for (long i = 0; i < group; i++) {
            bis.read(M_8);
            final byte[] M_64 = getBits(M_8);
            //如果不重新初始化，数组会有上次读取的残留数据
            Arrays.fill(M_8, (byte) 0);
            byte[] C_64 = iteration(M_64, encrypt_keys);
            byte[] C_8 = getBytes(C_64);
            bos.write(C_8, 0, C_8.length);
        }

        //一定要清空缓冲区
        bos.flush();
    }

    public void decryptFile(File srcFile, File desFile, String seed_key) throws IOException {
        //初始化
        this.srcFile = srcFile;
        this.desFile = desFile;
        this.seed_key = seed_key;
        //生成16个不同的48bits的子密钥
        decrypt_keys = generateKey(seed_key, 0);
        //读写文件
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        //8字节一组的密文
        byte[] C_8 = new byte[8];
        //获取分组数
        group = srcFile.length() / 8;
        for (long i = 0; i < group; i++) {
            bis.read(C_8);
            final byte[] C_64 = getBits(C_8);
            //如果不重新初始化，数组会有上次读取的残留数据
            Arrays.fill(C_8, (byte) 0);
            byte[] M_64 = iteration(C_64, decrypt_keys);
            byte[] M_8 = getBytes(M_64);
            //去掉填充的0字节
            while (i == (group - 1) && M_8[M_8.length - 1] == 0) {
                M_8 = Arrays.copyOf(M_8, M_8.length - 1);
            }
            bos.write(M_8, 0, M_8.length);
        }
        //一定要清空缓冲区
        bos.flush();
    }

    public void tripleEncryptFile(File srcFile, File desFile, String[] seed_key) throws IOException {
        String filePath = srcFile.getAbsolutePath();
        String fileExName = filePath.substring(filePath.lastIndexOf("."));
        filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
        File temp_0 = new File(filePath + "\\temp_0" + fileExName);
        File temp_1 = new File(filePath + "\\temp_1" + fileExName);
        encryptFile(srcFile, temp_0, seed_key[0]);
        decryptFile(temp_0, temp_1, seed_key[1]);
        encryptFile(temp_1, desFile, seed_key[2]);
    }

    public void tripleDecryptFile(File srcFile, File desFile, String[] seed_key) throws IOException {
        String filePath = srcFile.getAbsolutePath();
        String fileExName = filePath.substring(filePath.lastIndexOf("."));
        filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
        File temp_2 = new File(filePath + "\\temp_2" + fileExName);
        File temp_3 = new File(filePath + "\\temp_3" + fileExName);

        decryptFile(srcFile, temp_2, seed_key[2]);
        encryptFile(temp_2, temp_3, seed_key[1]);
        decryptFile(temp_3, desFile, seed_key[0]);
    }


    //16轮迭代
    private byte[] iteration(byte[] M_64, byte[][] K_48) {
        //IP置换
        byte[] ip_64 = initialPermutation(M_64);
        //将数组分为两半
        byte[] L_32 = Arrays.copyOfRange(ip_64, 0, ip_64.length / 2);
        byte[] R_32 = Arrays.copyOfRange(ip_64, ip_64.length / 2, ip_64.length);
        //16轮迭代
        for (int i = 0; i < 16; i++) {
            byte[] tempL_32 = Arrays.copyOfRange(L_32, 0, R_32.length);

            L_32 = Arrays.copyOfRange(R_32, 0, R_32.length);  //L1 = R0
            R_32 = xor(tempL_32, feistel(R_32, K_48[i]));          //R1 =  L0 xor f
        }
        //循环结束后  左右交换合并!
        byte[] temp_64 = new byte[L_32.length + R_32.length];
        System.arraycopy(R_32, 0, temp_64, 0, R_32.length);
        System.arraycopy(L_32, 0, temp_64, R_32.length, L_32.length);
        //IP逆置换
        return finalPermutation(temp_64);
    }

    //初始置换IP
    private byte[] initialPermutation(byte[] M_64) {
        byte[] ip_64 = new byte[64];
        for (int i = 0; i < M_64.length; i++) {
            ip_64[i] = M_64[Ip_Table[i] - 1];    //注意所给的IP置换表是从1开始，不是0
        }
        return ip_64;
    }

    //E盒拓展
    private byte[] extend(byte[] R_32) {
        byte[] R_48 = new byte[48];
        for (int i = 0; i < R_48.length; i++) {
            R_48[i] = R_32[Extend_Table[i] - 1];
        }
        return R_48;
    }

    //异或
    private byte[] xor(byte[] L, byte[] R) {
        byte[] ret = new byte[L.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) (L[i] ^ R[i]);
        }
        return ret;
    }

    //s盒压缩
    private byte[] sBoxZip(byte[] R_48) {
        byte[] ret_32 = new byte[32];
        for (int i = 0; i < 8; i++) {
            //以6个bits为一组，共8个组，每组寻找对应的sBox
            byte[] temp_6 = Arrays.copyOfRange(R_48, i * 6, i * 6 + 6);
            int line = temp_6[0] * 2 + temp_6[5];
            int row = temp_6[1] * 8 + temp_6[2] * 4 + temp_6[3] * 2 + temp_6[4];
            //将一组中的6bits压缩成4bits
            String binary = String.format("%4s", Integer.toBinaryString(S_Box[i][line][row])).replace(" ", "0");
            for (int j = 0; j < 4; j++) {
                ret_32[i * 4 + j] = (byte) (binary.charAt(j) - '0');
            }
        }
        return ret_32;
    }

    //P盒置换
    private byte[] pBoxPermutation(byte[] M_32) {
        byte[] p_32 = new byte[32];
        for (int i = 0; i < p_32.length; i++) {
            p_32[i] = M_32[P_Table[i] - 1];
        }
        return p_32;
    }

    private byte[] feistel(byte[] R0_32, byte[] K_48) {
        //E扩展
        byte[] R1_48 = extend(R0_32);
        //与密钥异或
        byte[] R2_48 = xor(R1_48, K_48);
        //sBox压缩
        byte[] R3_32 = sBoxZip(R2_48);
        //p盒置换
        return pBoxPermutation(R3_32);
    }

    //终止置换IP
    private byte[] finalPermutation(byte[] M_64) {
        byte[] ip_64 = new byte[64];
        for (int i = 0; i < M_64.length; i++) {
            ip_64[i] = M_64[Ip_Reverse_Table[i] - 1];
        }
        return ip_64;
    }

    //16个46bits的不同密钥的生成：
    private byte[][] generateKey(String seed_key, int mode) {
        //mode = 1 加密，mode = 0 解密
        //密钥长度不能大于8位，不足补0
        seed_key = String.format("%-8s", seed_key).replace(" ", "0");
        //将8字节密钥转换为64位二进制形式
        byte[] K_64 = getBits(seed_key.getBytes());
        //迭代产生16个48bits的不同密钥
        byte[][] K_48 = new byte[16][48];
        //置换选择PC-1
        byte[] C_56 = permutation_pc1(K_64);
        //分成两组
        byte[] E_28 = Arrays.copyOfRange(C_56, 0, C_56.length / 2);
        byte[] F_28 = Arrays.copyOfRange(C_56, C_56.length / 2, C_56.length);
        //16次迭代
        for (int i = 0; i < 16; i++) {
            byte[] E1_28 = rol(E_28, i);//循环左移
            byte[] F1_28 = rol(F_28, i);
            //合
            byte[] temp_56 = new byte[E1_28.length + F1_28.length];
            System.arraycopy(E1_28, 0, temp_56, 0, E1_28.length);
            System.arraycopy(F1_28, 0, temp_56, E1_28.length, F1_28.length);
            //置换选择PC-2
            K_48[i] = permutation_pc2(temp_56);
            //令改变后的E和F参与迭代
            E_28 = E1_28;
            F_28 = F1_28;
        }

        //如果mode = 0,生成的是解密的密钥
        if (mode == 0) {
            byte[][] tmp = Arrays.copyOf(K_48, K_48.length);
            for (int i = 0; i < 16; i++) {
                K_48[i] = tmp[15 - i];
            }
        }
        return K_48;
    }

    //pc1置换
    private byte[] permutation_pc1(byte[] K_64) {
        byte[] K_56 = new byte[56];  //去除8位校验码
        for (int i = 0; i < K_56.length; i++) {
            K_56[i] = K_64[PC1_Table[i] - 1];
        }
        return K_56;
    }

    //pc2置换
    private byte[] permutation_pc2(byte[] K_56) {
        byte[] K_48 = new byte[48];
        for (int i = 0; i < K_48.length; i++) {
            K_48[i] = K_56[PC2_Table[i] - 1];
        }
        return K_48;
    }

    //循环左移
    private byte[] rol(byte[] M_28, int index) {
        byte[] ret = Arrays.copyOf(M_28, M_28.length);
        for (int i = 0; i < Loop_Table[index]; i++) {
            byte temp = ret[0];
            for (int j = 0; j < ret.length - 1; j++) {
                ret[j] = ret[j + 1];
            }
            ret[ret.length - 1] = temp;
        }
        return ret;
    }


    private byte[] getBits(byte[] bytes) {
        byte[] M_64 = new byte[64]; //二进制存储形式的明文
        for (int i = 0; i < 8; i++) {
            String binary = Integer.toBinaryString(bytes[i]);
            String s = null;
            if (bytes[i] < 0)
                s = binary.substring(binary.length() - 8);
            else
                s = String.format("%8s", binary).replace(" ", "0");
            for (int j = 0; j < 8; j++) {
                M_64[i * 8 + j] = (byte) (s.charAt(j) - '0');
            }
        }
        return M_64;
    }

    private byte[] getBytes(byte[] bits) {
        byte[] M = new byte[8];
        for (int i = 0; i < M.length; i++) {
            //byte取值是-128~127
            M[i] = (byte) (bits[i * 8 + 7] + bits[i * 8 + 6] * 2 + bits[i * 8 + 5] * 4 + bits[i * 8 + 4] * 8 + bits[i * 8 + 3] * 16 + bits[i * 8 + 2] * 32 + bits[i * 8 + 1] * 64 + bits[i * 8] * 128);
        }
        return M;
    }

    private String getString(byte[] bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length; i++) {
            sb.append(bits[i]);
        }
        return sb.toString();
    }

    private void printBytes(byte[] bytes) {
        for (int i = 0; i < 8; i++) {
            System.out.print(bytes[i] + " ");
        }
        System.out.println();
    }

    private void printBits(byte[] M) {
        for (int i = 0; i < M.length; i++) {
            System.out.print(M[i]);
//            if ((i + 1) % 8 != 0)
//                System.out.print(" ");
//            else
//                System.out.println();
        }
        System.out.println();
    }

}
