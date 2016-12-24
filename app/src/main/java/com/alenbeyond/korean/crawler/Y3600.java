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
 * Created by alen on 16/12/24.
 */

public class Y3600 {

    public final static String RootUrl = "http://www.y3600.com/hanju/2016/850.html#play";
    //http://p.y3600.com/yk/eq_C4894C3B8B27CA2C449F170B9D28EE1A&m=1&1.html
    public final static String vedioUrl = "http://p.y3600.com/yk/";
    private static final String TAG = "Y3600";


    public static List<Hanju> getEpisodes() {
        List<Hanju> data = null;
        try {
            data = new ArrayList<>();
            Document doc = Jsoup.connect(RootUrl).timeout(10000).get();
            Element pl_lettyun = doc.getElementById("pl_lettyun");
            Elements lis = pl_lettyun.getElementsByTag("li");
            for (int i = 0; i < lis.size(); i++) {
                Element a = lis.get(i).getElementsByTag("a").get(0);
                String text = a.text();
                String onclick = a.attr("onclick");
                String[] split = onclick.split("\'");
                String tag = split[1];
                Hanju hanju = new Hanju();
                hanju.setEpisodes(text);
                hanju.setLink(vedioUrl + tag + "&m=1&1.html");
                data.add(hanju);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}
