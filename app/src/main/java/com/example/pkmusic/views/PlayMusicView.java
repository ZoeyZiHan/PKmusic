package com.example.pkmusic.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.pkmusic.R;
import com.example.pkmusic.helps.MediaPlayerHelp;
import com.example.pkmusic.models.MusicModel;
import com.example.pkmusic.services.MusicService;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private Intent mServiceIntent;
    private MusicService.MusicBind mMusicBind;
    private MusicModel mMusicModel;
    private boolean isPlaying,isBindService;
    private View mView;
    private FrameLayout mFlPlayMuic;
    private ImageView mIvIcon,mIvNeedle,mIvPlay;
    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;
    private MediaPlayerHelp mMediaPlayerHelp;


    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    private void init(Context context){
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.playmusic,this,false);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mFlPlayMuic = mView.findViewById(R.id.fv_play_music);
        mFlPlayMuic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);

        /*
        * 1、光盘转动的动画
        * 2、指针指向光盘的动画
        * 3、指针离开光盘的动画
        *
        * startAnimation执行动画
        */

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.stop_needle_anim);

        addView(mView);

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(mContext);
    }

    //设置光盘中显示的音乐封面图片
    public void setMusicIcon(){
        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }


    public void setMusic(MusicModel music){
        mMusicModel = music;
        setMusicIcon();
    }

    //播放音乐
    public void playMusic(){
        isPlaying = true;
        mFlPlayMuic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);
        mIvPlay.setVisibility(View.GONE);
       /*
        //判断当前音乐是否是已经在播放的音乐
        //如果是则直接执行start方法
        //如果不是则需要重置状态，调用setPath方法
        if(mMediaPlayerHelp.getPath()!=null
                && mMediaPlayerHelp.getPath().equals(path)){
            mMediaPlayerHelp.start();
        }else {
            mMediaPlayerHelp.setPath(path);
            mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayerHelp.start();
                }
            });
        }*/
        startMusicService();
    }

    //停止播放音乐
    public void stopMusic(){
        isPlaying = false;
        mFlPlayMuic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);
        mIvPlay.setVisibility(View.VISIBLE);
        //mMediaPlayerHelp.pause();
        if(mMusicBind != null){
            mMusicBind.stopMusic();
        }

    }

    //切换播放状态
    private void trigger(){
        if(isPlaying){
            stopMusic();
        }else {
            playMusic();
        }
    }

    //启动音乐服务
    private void startMusicService(){
        //启动Service
        if(mServiceIntent == null){
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        }else {
            mMusicBind.playMusic();
        }
        //若未绑定，则绑定Service
        if(!isBindService){
            isBindService = true;
            mContext.bindService(mServiceIntent,conn,Context.BIND_AUTO_CREATE);
        }
    }

    //解除绑定
    public void destory(){
        //如果已经绑定，解除绑定
        if(isBindService){
            isBindService = false;
            mContext.unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBind = (MusicService.MusicBind) service;
            mMusicBind.setMusic(mMusicModel);
            mMusicBind.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
