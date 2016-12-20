package com.alenbeyond.korean;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.alenbeyond.korean.adapter.HanjuAdapter;
import com.alenbeyond.korean.bean.Hanju;
import com.alenbeyond.korean.crawler.Hanjucc;
import com.alenbeyond.korean.utils.NetUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvEpisodes;
    private Handler handler;
    private List<Hanju> episodes;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 111) {
                    if (episodes == null) {
                        Snackbar.make(lvEpisodes, "加载失败啦", Snackbar.LENGTH_INDEFINITE)
                                .setAction("雪微点这重试", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loadData();
                                    }
                                }).show();
                    } else {
                        HanjuAdapter adapter = new HanjuAdapter(episodes, MainActivity.this);
                        lvEpisodes.setAdapter(adapter);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    lvEpisodes.setVisibility(View.VISIBLE);
                }
            }
        };
        initView();
        initData();
    }

    private void initView() {
        lvEpisodes = (ListView) findViewById(R.id.lv_episodes);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lvEpisodes.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", episodes.get(position).getLink());
                intent.putExtra("title", episodes.get(position).getEpisodes());
                startActivity(intent);
            }
        });
    }

    private void initData() {

        if (NetUtils.isOnline(this)) {
            loadData();
        } else {
            Snackbar.make(lvEpisodes, "没网额,打开网络试试", Snackbar.LENGTH_INDEFINITE)
                    .setAction("点这", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
        }
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        lvEpisodes.setVisibility(View.INVISIBLE);
        new Thread() {

            @Override
            public void run() {
                episodes = Hanjucc.getEpisodes();
                if (handler != null) {
                    Message msg = Message.obtain();
                    msg.what = 111;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
