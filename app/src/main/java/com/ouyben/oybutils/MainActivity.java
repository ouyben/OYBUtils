package com.ouyben.oybutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ouyben.androidutils.utils.SPUtils;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTv;
    Button mBtnSave;
    Button mBtnQuery;
    EditText mEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // TODO:得到sd卡路径
        //        StringBuilder builder = new StringBuilder();
        //        builder.append("\n" + SDCardUtils.getSDCardPath());
        //        builder.append("\n系统储存路径:" + SDCardUtils.getRootDirectoryPath());
        //        tv.setText(builder.toString());
        initView();

    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mEt = (EditText) findViewById(R.id.et);
        mBtnSave.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                SPUtils.put(this, "et", mEt.getText().toString());
                mEt.setText("");
                break;
            case R.id.btn_query:
                String s = (String) SPUtils.get(this, "et", "无");
                mTv.setText(s);
                break;
        }
    }
}
