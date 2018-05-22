package com.andriodtest;

import android.app.DownloadManager;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;

import android.database.Cursor;

import android.net.Uri;

import android.os.Environment;

import android.util.Log;

import android.widget.Toast;

import java.io.File;

/**

* Created by audaque on 2016/9/6.

*/

public class DownLoadBroadcastReceiver extends BroadcastReceiver {

@Override

public void onReceive(Context context, Intent intent) {

long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

SharedPreferences sPreferences = context.getSharedPreferences("ggfw_download", 0);

long refernece = sPreferences.getLong("ggfw_download_apk", 0);

if (refernece == myDwonloadID) {

DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

Intent install = new Intent(Intent.ACTION_VIEW);

//            Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);

DownloadManager.Query querybyId = new DownloadManager.Query();

querybyId.setFilterById(myDwonloadID);

Cursor myDownload = dManager.query(querybyId);

String dolownname=null;

if(myDownload.moveToFirst()){

int status = myDownload.getInt(myDownload.getColumnIndex(DownloadManager.COLUMN_STATUS));

if (status == DownloadManager.STATUS_SUCCESSFUL) {

// process download

int fileNameIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);

//此处取得的是完整路径+文件名称

dolownname=myDownload.getString(fileNameIdx);

}else{

Toast.makeText(context,"下载失败，删除残留文件",Toast.LENGTH_LONG).show();

dManager.remove(myDwonloadID);

myDownload.close();

return;

}

myDownload.close();

}

if(dolownname==null){

return;

}

File file = new File(dolownname);

install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

context.getApplicationContext().startActivity(install);

}

}

}
