package com.baifan.cleandemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baifan.cleandemo.service.CleanNoUseFileTask;
import com.baifan.cleandemo.util.MainHandler;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CleanNoUseFileTask.OnCallBack {
    @BindView(R.id.btn_clean)
    Button mBtnClean;

    @BindView(R.id.tv_file_des)
    TextView mTvFileDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mBtnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCleanTask();
            }
        });
    }

    private void startCleanTask() {
        CleanNoUseFileTask cleanNoUseFileTask = new CleanNoUseFileTask(this);
        cleanNoUseFileTask.execute();
    }

    @Override
    public void onFinished(List<File> logFileList, List<File> bigFileList, List<File> tempList, List<File> apkFileList) {

    }

    @Override
    public void onProgress(final File file) {

        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                mTvFileDes.setText("正在扫描..." + file.getAbsolutePath());
            }
        });
    }

    @Override
    public void onBegin() {
        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                mTvFileDes.setText("开始扫描");
            }
        });
    }
}
