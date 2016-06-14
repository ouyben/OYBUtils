package com.ouyben.oybutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ouyben.androidutils.utils.FileUtil;
import com.ouyben.androidutils.utils.SDCardUtils;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        // TODO:得到sd卡路径
        StringBuilder builder = new StringBuilder();
        builder.append(FileUtil.SDPATH);
        builder.append("\n" + SDCardUtils.getSDCardPath());
        builder.append("\n系统储存路径:" + SDCardUtils.getRootDirectoryPath());
        tv.setText(builder.toString());
    }
}
