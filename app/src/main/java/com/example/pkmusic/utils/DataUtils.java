package com.example.pkmusic.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtils {

    //读取资源文件中的数据
    public static String getJsonFromAssets(Context context,String fileName){

        /*
        * 1、StringBuilder 存放读取出的数据
        * 2、AssetManager 资源管理器，Open方法打开指定的资源文件，返回InputStream
        * 3、InputStreamReader 字节到字符的桥接器，BufferedReader 存放读取字符的缓冲区
        * 4、循环利用BufferReader的readline方法读取每一行的数据，
        *       并且把读取的数据放入到StringBuilder里
        * 5、返回读取的所有数据*/

        //1、StringBuilder 存放读取出的数据
        StringBuilder stringBuilder = new StringBuilder();
        //2、AssetManager 资源管理器
        AssetManager assetManager = context.getAssets();
        try {
            //，Open方法打开指定的资源文件，返回InputStream
            InputStream inputStream = assetManager.open(fileName);
            //3、InputStreamReader 字节到字符的桥接器，BufferedReader 存放读取字符的缓冲区
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //循环利用BufferReader的readline方法读取每一行的数据
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
