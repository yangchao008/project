package com.example.myapplication;

import java.io.File;

import static com.example.myapplication.DimenTool2.Make;
import static com.example.myapplication.DimenTool2.writeFile;

/**
 * Date: 2021/1/4 15:17
 * Author: hans yang
 * Description: 生成dp_1-dp_2000
 */
class DimenTool2000 {

    public final static int DIMEN_COUNT = 3000;

    public static void main(String[] args) {
        edit();
    }

    private static void edit() {
        StringBuilder builder = new StringBuilder();
        String topStr = "<resources>";
        String topStr2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String bottomStr = "</resources>";

        // 添加头部
        builder.append(topStr).append("\r\n").append(topStr2).append("\r\n");

        // 内容
         // <dimen name="user_property_coupon_divider_padding_ver">8dp</dimen>
        for (int i = 1; i < DIMEN_COUNT + 1; i++) {
            String lineStr = String.format("    <dimen name=\"dp_%d\">%ddp</dimen>", i, i);
            builder.append(lineStr)
                    .append("\r\n");
        }

        // 添加尾部
        builder.append(bottomStr).append("\r\n");

        stringWriteFile(builder, "dimens.xml");
    }

    private static void stringWriteFile(StringBuilder builder, String fileName) {
            String text = builder.toString();
            String devicePath = "rc602";

            File resultFile = new File("./reslib/src/" + devicePath + "/res/values/" + fileName);
            Make(resultFile);
            //将新的内容，写入到指定的文件中去
            writeFile(resultFile, text);
            builder.setLength(0);
    }
}
