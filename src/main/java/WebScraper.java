import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class WebScraper {

    public static String getArtUrl (String query){
        String src ="";
        try {
            String url = "https://www.discogs.com/search/q=" + query + "&type=all";
            Document doc = Jsoup.connect(url).get();
            Elements albumArt = doc.select(".thumbnail_center");
            Element span = albumArt.get(0);
            Element img = span.child(0);
            src = img.attr("data-src");

        } catch (IOException e) {
            e.printStackTrace();

        }

        return src;
    }

    public static void main(String[] args) {
        getArtUrl("string");
    }

}
