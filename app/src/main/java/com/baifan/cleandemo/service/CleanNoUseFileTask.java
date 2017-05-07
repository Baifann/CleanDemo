package com.baifan.cleandemo.service;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.baifan.cleandemo.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 删除无用文件任务
 * Created by baifan on 2017/5/7.
 */

public class CleanNoUseFileTask extends AsyncTask<Void, Void, Void> {
    private final static String TAG = "CleanNoUseFileTask";
    /**
     * 日志文件
     */
    private List<File> mLogFileList;

    /**
     * 大型文件
     */
    private List<File> mBigFileList;

    /**
     * 临时文件
     */
    private List<File> mTempFileList;

    /**
     * apk文件
     */
    private List<File> mApkFileList;

    private OnCallBack mOnCallBack;

    public CleanNoUseFileTask(OnCallBack onCallBack) {
        mBigFileList = new ArrayList<>();
        mLogFileList = new ArrayList<>();
        mTempFileList = new ArrayList<>();
        mApkFileList = new ArrayList<>();
        mOnCallBack = onCallBack;
    }


    public interface OnCallBack {
        void onFinished(List<File> logFileList, List<File> bigFileList, List<File> tempList, List<File> apkFileList);

        void onProgress(File file);

        void onBegin();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnCallBack.onBegin();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground");
        File externalDir = Environment.getExternalStorageDirectory();
        Log.d(TAG, "doInBackground externalDir:" + externalDir.getAbsolutePath());
        if (externalDir != null) {
            travelPath(externalDir);
        }else{
            Log.d(TAG, "doInBackground externalDir is null");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mOnCallBack.onFinished(mLogFileList, mBigFileList, mTempFileList, mApkFileList);
    }

    private void travelPath(File root) {
        Log.d(TAG, "travelPath");
        if (root == null || !root.exists() || isCancelled()) {
            Log.e(TAG, "root is root == null || !root.exists() || isCancelled()");
            return;
        }

        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isCancelled()) {
                    return;
                }
                if (file.isFile()) {
                    Log.d(TAG, "travelPath file:" + file.getAbsolutePath());

                    if (FileUtil.isLog(file)) {
                        mLogFileList.add(file);

                        mOnCallBack.onProgress(file);
                    } else if (FileUtil.isTempFile(file)) {
                        mTempFileList.add(file);

                        mOnCallBack.onProgress(file);
                    } else if (FileUtil.isApk(file)) {
                        mApkFileList.add(file);

                        mOnCallBack.onProgress(file);
                    } else if (FileUtil.isBigFile(file)) {
                        mBigFileList.add(file);

                        mOnCallBack.onProgress(file);
                    }
                } else {
                    travelPath(file);
                }
            }
        }else{
            Log.e(TAG, "travelPath files is null");
        }
    }
}
