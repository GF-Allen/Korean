package com.alenbeyond.korean.crawler;

import android.util.Log;

import com.alenbeyond.korean.bean.Hanju;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alen on 16/12/19.
 */

public class Hanjucc {
    private static final String TAG = "Hanjucc";
    public static final String RootUrl = "http://www.hanjucc.com/hanju/134579.html#";

    public static List<Hanju> getEpisodes() {
        List<Hanju> data = null;
        try {
            data = new ArrayList<>();
            Document doc = Jsoup.connect(RootUrl).timeout(10000).get();
            Elements abc = doc.getElementsByClass("abc");
            Element url = abc.get(1);
            Elements dt = url.getElementsByTag("dt");
            for (int i = 0; i < dt.size(); i++) {
                Elements a = dt.get(i).getElementsByTag("a");
                String href = a.attr("abs:href");
                String text = a.text();
                Hanju hanju = new Hanju();
                hanju.setEpisodes(text);
                hanju.setLink(href);
                data.add(hanju);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
