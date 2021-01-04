package com.example.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DimenTool {
    public final static int defaultDp = 360;


    public static boolean isPalindrome(int x) {
        int a = x, h = 1;
        if (a < 0) return false;
        while (a / h >= 10) {
            h = h * 10;
        }
        while (a > 0) {
            if (a / h != a % 10) return false;
            a = a % h;
            a = a / 10;
            h = h / 100;
        }
        return true;
    }

    public static void main(String[] args) {
        Davn();
//        isPalindrome(123464321);

        /*String[] arrays= {"1","2","3","5","6", "2", "3", "2"};
        int[] arrays1= {1, 2, 3,2,4, 3, 7, 3, 6, 8};
        int newLength = arrays1.length;
        for (int i = 0; i < newLength; i++) {
            if ((arrays1[i]) == 3) {
                int numMoved = newLength - i - 1;
                if (numMoved >= 0) {
                    System.arraycopy(arrays1, i + 1, arrays1, i, numMoved);
                    arrays1[--newLength] = -1;
                }
            }
        }
        System.out.println("new length : " + arrays1.toString()+", lenght:" + newLength);*/
    }
    public static void Davn() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./app/src/main/res/values/dimens.xml");

        BufferedReader reader = null;
        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw360 = new StringBuilder();
        StringBuilder sw384 = new StringBuilder();
        StringBuilder sw400 = new StringBuilder();
        StringBuilder sw411 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw560 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw640 = new StringBuilder();
/*
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw768 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder sw820 = new StringBuilder();
*/

        try {

            System.out.println("生成不同分辨率：");

            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;

            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {

                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");

                    String start = tempString.substring(0, tempString.indexOf(">") + 1);

                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                                (tempString.substring(tempString.indexOf(">") + 1,
                                            tempString.indexOf("</dimen>") - 2));
                    // TODO: 2017/3/28 更改比例乘积

                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。

                    sw320.append(start).append(num * 320/defaultDp).append(end).append("\r\n");
                    sw360.append(start).append(num * 360/defaultDp).append(end).append("\r\n");
                    sw400.append(start).append(num * 400/defaultDp).append(end).append("\r\n");
                    sw411.append(start).append(num * 432/defaultDp).append(end).append("\r\n");
                    sw480.append(start).append(num * 480/defaultDp).append(end).append("\r\n");
                    sw560.append(start).append(num * 560/defaultDp).append(end).append("\r\n");
                    sw600.append(start).append(num * 600/defaultDp).append(end).append("\r\n");
                    sw640.append(start).append(num * 640/defaultDp).append(end).append("\r\n");


                } else {
                    sw240.append(tempString).append("");
                    sw320.append(tempString).append("");
                    sw360.append(tempString).append("");
                    sw384.append(tempString).append("");
                    sw400.append(tempString).append("");
                    sw411.append(tempString).append("");
                    sw480.append(tempString).append("");
                    sw560.append(tempString).append("");
                    sw600.append(tempString).append("");
                    sw640.append(tempString).append("");
/*                    sw720.append(tempString).append("");
                    sw768.append(tempString).append("");
                    sw800.append(tempString).append("");
                    sw820.append(tempString).append("");*/
                }

                line++;

            }

            reader.close();
            /*System.out.println("<!--  sw240 -->");
            System.out.println(sw240);*/

            System.out.println("<!--  sw320 -->");
            System.out.println(sw320);

            System.out.println("<!--  sw360 -->");
            System.out.println(sw360);

            System.out.println("<!--  SW384 -->");
            System.out.println(sw384);

            System.out.println("<!--  sw400 -->");
            System.out.println(sw400);

            System.out.println("<!--  sw411 -->");
            System.out.println(sw411);

            System.out.println("<!--  sw480 -->");
            System.out.println(sw480);

            System.out.println("<!--  sw560 -->");
            System.out.println(sw560);

            System.out.println("<!--  sw600 -->");
            System.out.println(sw600);

            System.out.println("<!--  sw640 -->");
            System.out.println(sw640);

/*            System.out.println("<!--  sw720 -->");
            System.out.println(sw720);

            System.out.println("<!--  sw768 -->");
            System.out.println(sw768);

            System.out.println("<!--  sw800 -->");
            System.out.println(sw800);

            System.out.println("<!--  sw820 -->");
            System.out.println(sw820);*/
            //直接指定文件夹路径，以及文件名及格式。上来就是干！
//            File w240 = new File("./app/src/main/res/values-w240dp/dimens.xml");
            File w320 = new File("./app/src/main/res/values-sw320dp/dimens.xml");
            File w360 = new File("./app/src/main/res/values-sw360dp/dimens.xml");
            File w400 = new File("./app/src/main/res/values-sw400dp/dimens.xml");
            File w432 = new File("./app/src/main/res/values-sw432dp/dimens.xml");
            File w480 = new File("./app/src/main/res/values-sw480dp/dimens.xml");
            File w560 = new File("./app/src/main/res/values-sw560dp/dimens.xml");
            File w600 = new File("./app/src/main/res/values-sw600dp/dimens.xml");
            File w640 = new File("./app/src/main/res/values-sw640dp/dimens.xml");
/*
            File w720 = new File("./app/src/main/res/values-w720dp/dimens.xml");
            File w768 = new File("./app/src/main/res/values-w768dp/dimens.xml");
*/
//            File w800 = new File("./app/src/main/res/values-w800dp/dimens.xml");
//            File w820 = new File("./app/src/main/res/values-w820dp/dimens.xml");

//            Make(w240);
            Make(w320);
            Make(w360);
            Make(w400);
            Make(w432);
            Make(w480);
            Make(w560);
            Make(w600);
            Make(w640);
          /*  Make(w720);
            Make(w768);
            Make(w800);
            Make(w820);*/

            //将新的内容，写入到指定的文件中去
//            writeFile(w240, sw240.toString());
            writeFile(w320, sw320.toString());
            writeFile(w360, sw360.toString());
            writeFile(w400, sw400.toString());
            writeFile(w432, sw411.toString());
            writeFile(w480, sw480.toString());
            writeFile(w560, sw480.toString());
            writeFile(w600, sw600.toString());
            writeFile(w640, sw640.toString());
/*            writeFile(w720, sw720.toString());
            writeFile(w768, sw768.toString());
            writeFile(w800, sw800.toString());
            writeFile(w820, sw820.toString());*/


        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {

                try {

                    reader.close();

                } catch (IOException e1) {

                    e1.printStackTrace();

                }

            }

        }

    }


    /**
     * 写入方法
     */

    public static void writeFile(File file, String text) {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            out.println(text);

        } catch (IOException e) {

            e.printStackTrace();

        }


        out.close();

    }

    //自定义检测生成指定文件夹下的指定文件
    public static void Make(File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
