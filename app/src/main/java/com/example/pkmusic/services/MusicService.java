package com.example.pkmusic.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.pkmusic.R;
import com.example.pkmusic.activities.WelcomeActivity;
import com.example.pkmusic.helps.MediaPlayerHelp;
import com.example.pkmusic.models.MusicModel;


/*
* 1、通过Service连接PlayMusicView和MediaPlayHelp
* 2、PlayMusicView -- Service
*       1、播放音乐，暂停音乐
*       2、启动Service，绑定Service，解除绑定Service
* 3、MediaPlayHelp -- Service
*       1、播放音乐，暂停音乐
*       2、监听音乐播放完成，停止Service
*/

public class MusicService extends Service {

    public static final int NOTIFICATION_ID = 1;

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;

    public MusicService() {
    }

    public class MusicBind extends Binder{
        /*
        * 1、设置音乐（接收 MusicModel）
        * 2、播放音乐
        * 3、暂停播放
        */
        public void setMusic(MusicModel musicModel){
            mMusicModel = musicModel;
            startForeround();
        }

        public void playMusic(){
            //判断当前音乐是否是已经在播放的音乐
            //如果是则直接执行start方法
            //如果不是则需要重置状态，调用setPath方法
            if(mMediaPlayerHelp.getPath()!=null
                    && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())){
                mMediaPlayerHelp.start();
            }else {
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        public void stopMusic(){
            mMediaPlayerHelp.pause();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);
    }

    //设置服务在前台可见
    private void startForeround(){

        //通知栏点击跳转的intent
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this,0,new Intent(this, WelcomeActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);

        //创建Notification
        Notification notification = new Notification.Builder(this)
                .setContentTitle(mMusicModel.getName())
                .setContentText(mMusicModel.getAuthor())
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent)
                .build();

        //设置notification在前台展示
        startForeground(NOTIFICATION_ID,notification);
    }
}