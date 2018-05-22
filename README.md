# andriodTest
1、创建功能类DownloadApk继承自ReactContextBaseJavaModule，将方法暴露给js调用

2、创建类DownloadApkPackage 继承自ReactPackage接口，用于注册Native Modules，使得js上能够引入调用。

3、创建类DownLoadBroadcastReceiver继承BroadcastReceiver，用于接收处理下载的文件。

4、在AndroidManifest.xml中注册广播监听配置

<receiver   android:name="com.andriodTest.DownLoadBroadcastReceiver">
<intent-filter>

<action android:name="android.intent.action.DOWNLOAD_COMPLETE"/>
</intent-filter>
</receiver>

5、在MainApplication.java文件的getPackges()中注册DownloadApkPackage

new DownloadApkPackage();

6、调用

import {NativeModules} from 'react-native';

NativeModules.DownloadApk.downloading("http://bos.pgzs.com/sjapp91/msoft/20180507456/23/official_website6.1.0.370.apk","91zhushou.apk");
