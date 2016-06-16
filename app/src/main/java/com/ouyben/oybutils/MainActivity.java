package com.ouyben.oybutils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ouyben.androidutils.recyclerview.AutoSwipeRefreshLayout;
import com.ouyben.androidutils.utils.SPUtils;
import com.ouyben.androidutils.utils.TimeUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    TextView mTv;
    Button mBtnSave;
    Button mBtnQuery, mBtnRefresh;
    EditText mEt;
    AutoSwipeRefreshLayout mRefreshLayout;
    TextView tvRefresh;
    private MyHandler mHandler = new MyHandler(this);

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
        mBtnRefresh = (Button) findViewById(R.id.btn_refresh);
        mEt = (EditText) findViewById(R.id.et);
        mRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.refresh);
        tvRefresh = (TextView) findViewById(R.id.tv_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mBtnRefresh.setOnClickListener(this);
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
            case R.id.btn_refresh:
                mRefreshLayout.autoRefresh();

                break;
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(mRunnable, 5000);
        tvRefresh.setText("开始:" + TimeUtils.getTime2());
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.obtainMessage(0).sendToTarget();
        }
    };

    /**
     * TODO: hander的弱引用, 防止内存溢出
     */
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mMainActivity;

        public MyHandler(MainActivity activity) {
            mMainActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mMainActivity.get();
            if (activity != null) {
                activity.mRefreshLayout.setRefreshing(false);
                activity.tvRefresh.setText(activity.tvRefresh.getText().toString() + "\n结束:" + TimeUtils.getTime2());
            }
        }

    }

}
