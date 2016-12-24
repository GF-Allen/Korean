package com.alenbeyond.korean.crawler;

import android.util.Log;

import com.alenbeyond.korean.bean.Hanju;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alen on 16/12/24.
 */

public class Github {

    public final static String RootUrl = "https://raw.githubusercontent.com/XiqingLiu/Korean/master/app/data.json";
    private static final String TAG = "Github";

    public static List<Hanju> getEpisodes() {
        List<Hanju> data = null;
        try {
            data = new ArrayList<>();
            Document doc = Jsoup.connect(RootUrl).timeout(10000).get();
            Log.d(TAG, doc.text());
            String result = doc.text();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String episodes = jsonObject.getString("Episodes");
                String links = jsonObject.getString("links");
                Hanju hanju = new Hanju();
                hanju.setEpisodes(episodes);
                hanju.setLink(links);
                data.add(hanju);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
