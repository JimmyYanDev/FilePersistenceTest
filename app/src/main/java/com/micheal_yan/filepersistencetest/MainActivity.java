package com.micheal_yan.filepersistencetest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 本程序是用来演示使用File进行文件存储，实现EditText数据持久保留的功能
 *
 * @author Micheal_Yan
 */
public class MainActivity extends Activity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化EditText组件
        editText = (EditText) findViewById(R.id.editText);
        //从文件中读取之前输入的内容
        String inputText = load();
        //TextUtils.isEmpty用于判断字符串是否是空字符串或者null
        if (!TextUtils.isEmpty(inputText)) {
            //如果字符串不为空，则将读取到的内容显示在EditText中
            editText.setText(inputText);
            //将光标移至最后
            editText.setSelection(inputText.length());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //获取EditText内容
        String inputText = editText.getText().toString();
        //存储到文件中去
        save(inputText);
    }

    /**
     * 加载文件中的内容
     *
     * @return 返回加载的结果
     */
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            //获取文件的字节输入流
            in = openFileInput("data");
            //通过InputStreamReader将文件字节输入流转换为字符输入流，
            // 再将其包装成BufferedReader缓冲流
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            //用wile循环逐行读取文件中的内容，并添加到StringBuilder中
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    //关闭流
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 将用户输入的内容保存至文件中
     *
     * @param inputText 输入的文字
     */
    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //获取文件的字节输出流
            out = openFileOutput("data", MODE_PRIVATE);
            //通过OutputStreamWriter()将文件字节输出流转换为字符输出流，
            // 再将其包装成BufferedWriter缓冲流
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //将输入的文字写入文件中
            writer.write(inputText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    //关闭流
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
