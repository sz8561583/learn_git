package Lab1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //3个种子密钥
        String[] seed_key = new String[3];
        DES D = new DES();
        tripleEncryptFile(seed_key, D);
        tripleDecryptFile(seed_key, D);
    }

    private static void tripleEncryptFile(String[] seed_key, DES D) throws IOException {
        System.out.println("--------3DES加密界面--------");
        System.out.println("请输入要加密的文件的绝对路径");
        System.out.println("形如：\"C:\\\\Users\\\\IdeaProjects\\\\computer_security\\\\****.jpg\"");
        Scanner sc = new Scanner(System.in);
        File otext = new File(sc.nextLine());
        if (!otext.exists()) {
            System.out.println("指定路径的文件不存在");
        } else {
            System.out.println("请输入3个种子密钥");
            seed_key[0] = sc.nextLine();
            seed_key[1] = sc.nextLine();
            seed_key[2] = sc.nextLine();
            String file_path = otext.toString();
            String fileName = otext.getName().substring(0, otext.getName().lastIndexOf("."));
            String file_extension_name = file_path.substring(file_path.lastIndexOf("."));
            String file_directory = file_path.substring(0, file_path.lastIndexOf("\\"));
            File ctext = new File(file_directory + "\\" + fileName + "_3DES加密" + file_extension_name);
            System.out.println("正在加密中……请不要输入");
            D.tripleEncryptFile(otext, ctext, seed_key);
            System.out.println("产生缓存文件 temp_0" + file_extension_name + ", temp_1" + file_extension_name);
            System.out.println("加密文件已保存到当前文件夹中");
            System.out.println("加密文件路径为：" + ctext.getAbsolutePath());
            System.out.println("--------3DES加密成功--------");
        }
    }

    private static void tripleDecryptFile(String[] seed_key, DES D) throws IOException {
        System.out.println("--------3DES解密界面--------");
        System.out.println("请输入要解密的文件的绝对路径");
        System.out.println("形如：\"C:\\\\Users\\\\IdeaProjects\\\\computer_security\\\\****.jpg\"");
        Scanner sc = new Scanner(System.in);
        File ctext = new File(sc.nextLine());
        if (!ctext.exists()) {
            System.out.println("指定路径的文件不存在");
        } else {
            System.out.println("请输入3个种子密钥");
            System.out.println("输入顺序要与加密文件时相同");
            seed_key[0] = sc.nextLine();
            seed_key[1] = sc.nextLine();
            seed_key[2] = sc.nextLine();
            String file_path = ctext.toString();
            String fileName = ctext.getName().substring(0, ctext.getName().lastIndexOf("."));
            String file_extension_name = file_path.substring(file_path.lastIndexOf("."));
            String file_directory = file_path.substring(0, file_path.lastIndexOf("\\"));
            File ptext = new File(file_directory + "\\"+fileName+"_3DES解密" + file_extension_name);
            System.out.println("正在解密中……请不要输入");
            D.tripleDecryptFile(ctext, ptext, seed_key);
            System.out.println("产生缓存文件 temp_1" + file_extension_name + ", temp_2" + file_extension_name);
            System.out.println("解密文件已保存到当前文件夹中");
            System.out.println("解密文件路径为：" + ptext.getAbsolutePath());
            System.out.println("--------3DES解密成功--------");
        }
    }
}
