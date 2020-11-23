package com.example.pkmusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pkmusic.R;
import com.example.pkmusic.adapter.MusicGridAdapter;
import com.example.pkmusic.adapter.MusicListAdapter;
import com.example.pkmusic.helps.RealmHelp;
import com.example.pkmusic.models.MusicSourceModel;
import com.example.pkmusic.views.GridSpaceItem;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid;
    private RecyclerView mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;
    private RealmHelp mRealmHelp;
    private MusicSourceModel mMusicSourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView() {
        initNavBar(false,"胖葵音乐",true);

        mRvGrid = findViewById(R.id.rv_grid);
        //同一行有三个歌单
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        mRvGrid.addItemDecoration(new GridSpaceItem(getResources().getDimensionPixelSize(R.dimen.albumMarginSize),mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter = new MusicGridAdapter(this,mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mGridAdapter);

        mRvList = findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvGrid.setNestedScrollingEnabled(false);
        mListAdapter = new MusicListAdapter(this,mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);
    }

    private void initData(){
        mRealmHelp = new RealmHelp();
        mMusicSourceModel = mRealmHelp.getMusicSource();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelp.close();
    }
}