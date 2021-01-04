package com.example.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimenTool2 {

    //S801    8寸  1920X1440，  1.333333333333333   300ppi    系统设定 300dpi
    //S102 10.3寸  1404*1872 ;  1.333333333333333    227ppi  系统设定 240dpi
    //     13.3寸  1650 x 2200  /1200x1600  (可能会有) 1.333333333333333  207ppi
    //686   6.8寸  1440 X 1080   1.333333333333333  265ppi  系统设定 240dpi
    //rc602 6寸    1024 X 758   213ppi   1.350923482849604  系统设定 160dpi
    //rc601 6寸    1448 X 1072  301ppi     1.350746268656716  系统设定 240dpi
    /**
     * 设备：rc601；分辨率：1072*1448；ppi：301ppi；系统设定：240dpi。 -->  竖屏：1072 * 160 / 240 = sw714约等于sw710；横屏：1448 * 160 / 240 = sw965约等于sw960
     * 设备：rc602；分辨率：758*1024；ppi：213ppi；系统设定：160dpi。  -->  竖屏：758 * 160 / 160 = sw758约等于sw750；横屏：1024 * 160 / 160 = sw1024等于sw1020
     * 设备：s801 ；分辨率：1440*1920；ppi：300ppi；系统设定：300dpi。 -->  竖屏：1440 * 160 / 300 = sw768约等于sw760；横屏：1920 * 160 / 300 = sw1024约等于sw1020
     * 设备：s102 ；分辨率：1404*1872；ppi：227ppi；系统设定：240dpi。 -->  竖屏：1404 * 160 / 240 = sw936约等于sw930；横屏：1872 * 160 / 240 = sw1248约等于sw1240
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 注意：原文件中的dip统一替换成dp，
         * ***********
         * *****************
         * *****************
         * *****************
         * ***************
         */
        start();
    }

    // 过滤重复资源集合
    private static final List<String> repeatNameFilter = new ArrayList<>();

    // 现在重新适配602，就以601为标准
    private static float defaultDp = 710f;
    // 是否保留原来的文件名（true：多个文件可以可以保留之前的多个文件名；false：统一放在dimens.xml文件里面，不保留之前的文件名）
    // 适用说明：整合文件个数少，没什么重复资源，可以设置为true；整合的文件太多，重复资源多，可以设置为false，好合并资源；
    private static final boolean isRetainFileName = false;

    private static final String outFilePath = "./reslib/src/%s/res/values-sw%ddp/%s";
    // <integer name="page_count_related_book_list">@integer/page_count_store_book_list</integer>
    public static void start() {
        // 设备rc601：（竖屏：sw710；横屏：sw960）
        // 设备rc602：（竖屏：sw750；横屏：sw1020）
        // 设备s801： （竖屏：sw750；横屏：sw1020）
        // 设备s102： （竖屏：sw930；横屏：sw1240）

        // 是否是横屏资源
        final boolean isLand = false;

        List<File> fileList = new ArrayList<>();
        List<Integer> swList = new ArrayList<>();
        handleResource(fileList, swList, isLand);

        // 配置在这上面的代码
        repeatNameFilter.clear();
        start(fileList, swList);
    }

    private static void handleResource(List<File> fileList, List<Integer> swList, boolean isLand) {
        if (isLand) {
            // 横屏资源
            // 现在重新适配602，就以601为标准
            defaultDp = 960f;
            swList.add(960);
            swList.add(1020);
            swList.add(1240);
            String filePath = "./app/src/main/res/values-land-v26"; // 602
            fileList.add(new File(filePath));
            filePath = "./app/src/main/res/values-land-v27"; // main
            fileList.add(new File(filePath));
        }else {
            // 竖屏资源
            // 现在重新适配602，就以601为标准
            defaultDp = 710f;
            String filePath = "./app/src/main/res/values-v26"; // 602
            fileList.add(new File(filePath));
            filePath = "./app/src/main/res/values-v27"; // main
            fileList.add(new File(filePath));
            swList.add(710);
            swList.add(750);
            swList.add(930);
        }
    }

    private static String getDevicePath(int key) {
        String path = "rc602";
        /* 现在在602上验证可行性
        switch (key){
            case 710:
                path = "rc601";
                break;
            case 750:
                path = "rc602";
                break;
            case 930:
                path = "s102";
                break;
            default:
                break;
        }
         */
        return path;
    }

    private static void start(List<File> fileList, List<Integer> swList) {
        HashMap<Integer,StringBuilder> hashMap = new HashMap<>();
        for (int i = 0; i < swList.size(); i++) {
            StringBuilder builder = new StringBuilder();
            hashMap.put(swList.get(i), builder);
        }

        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (file == null){
                continue;
            }

            if (file.isFile()){
                readAndWriteSingleFile(hashMap, file);
            }else if (file.isDirectory()){
                File[] files = file.listFiles();
                for (int j = 0; j < files.length; j++) {
                    File f = files[j];
                    readAndWriteSingleFile(hashMap, f);
                }
            }
        }
        if (!isRetainFileName){
            // 不保留文件之前文件名，统一StringBuilder里面的数据，一起写进文件dimens.xml
            stringWriteFile(hashMap, "dimens.xml");
        }
    }

    private static void readAndWriteSingleFile(HashMap<Integer,StringBuilder> hashMap, File file) {
        if (file == null || !file.isFile() || !file.getName().contains("dimens")){
            return;
        }
        BufferedReader reader = null;
        try {
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);

                    if (!tempString.contains("-->") && !tempString.contains("<!--")) {
                        String startTemp = start.substring(start.indexOf("name=\""),start.lastIndexOf("\">"));
                        // 过滤非注释的
                        if (repeatNameFilter.contains(startTemp)) {
                            // 防止重复
                            continue;
                        } else {
                            repeatNameFilter.add(startTemp);
                        }
                    }
                    String end = tempString.substring(tempString.lastIndexOf("</") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    double num = -1000;
                    String substring = tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2);
                    if (tempString.contains("dp<") || tempString.contains("sp<")){
                        num = Double.parseDouble
                                (substring);
                    }
                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
                    for (Map.Entry<Integer,StringBuilder> entry: hashMap.entrySet()){
                        int key = entry.getKey();
                        StringBuilder builder = entry.getValue();

                        float scale = (float) key / defaultDp;
                        if (930 == key || 1240 == key){
                            // 缩小102设备的字体大小
                            scale = 1.15f;
                        }
                        String str = -1000 == num ? substring
                                : String.format("%.2f", num * scale);
                        builder.append(start).append(str).append(end).append("\r\n");
                    }
                } else {
                    /*
                    if (repeatNameFilter.contains(tempString)) {
                        // 防止重复
                        continue;
                    }else {
                        repeatNameFilter.add(tempString);
                    }
                     */
                    if (!isRetainFileName){
                        // 文件多时，生成一个到dimens.xml文件时，头部文件会有很多，做下过滤
                        String filterStr = "</resources>";
//                        String filterStr2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".toLowerCase();
                        String filterStr2 = "encoding=".toLowerCase(); // 减少字符串量，减少错误
                        String filterStr3 = "<resources>";
                        String tempLowerCase = tempString.toLowerCase();
                        if (tempLowerCase.contains(filterStr) || tempLowerCase.contains(filterStr2)
                                || tempLowerCase.contains(filterStr3)){
                            if (repeatNameFilter.contains(tempString)) {
                                // 防止头部重复多
                                continue;
                            }else {
                                repeatNameFilter.add(tempString);
                            }
                        }
                    }
                    for (Map.Entry<Integer,StringBuilder> entry: hashMap.entrySet()){
                        StringBuilder builder = entry.getValue();
                        builder.append(tempString).append("\r\n");
                    }
                }
            }
            reader.close();

            if (isRetainFileName) {
                //直接指定文件夹路径，以及文件名及格式。
                String fileName = file.getName();
                stringWriteFile(hashMap, fileName);
            }
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

    private static void stringWriteFile(HashMap<Integer, StringBuilder> hashMap, String fileName) {
        for (Map.Entry<Integer,StringBuilder> entry: hashMap.entrySet()){
            int key = entry.getKey();
            StringBuilder builder = entry.getValue();
            String text = builder.toString();
            String devicePath = getDevicePath(key);
            if (text == null || text.trim().length() < 1 ||devicePath == null || devicePath.length() < 1) {
                continue;
            }

            File resultFile = new File(String.format(outFilePath, devicePath, key, fileName));
            Make(resultFile);
            //将新的内容，写入到指定的文件中去
            writeFile(resultFile, text);
            builder.setLength(0);
        }
        repeatNameFilter.clear();
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
