package com.alenbeyond.korean;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alenbeyond.korean.adapter.HanjuAdapter;
import com.alenbeyond.korean.bean.Hanju;
import com.alenbeyond.korean.crawler.Hanjucc;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvEpisodes;
    private Handler handler;
    private List<Hanju> episodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        lvEpisodes = (ListView) findViewById(R.id.lv_episodes);
        lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra("url",episodes.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 111) {
                    HanjuAdapter adapter = new HanjuAdapter(episodes, MainActivity.this);
                    lvEpisodes.setAdapter(adapter);
                }
            }
        };
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
