import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;
import java.util.concurrent.RecursiveTask;

public class LinksParser extends RecursiveTask<Vector<String>> {

    String url;
    private Vector<String> linksList = new Vector<>();
    private String startUrl;

    public LinksParser(String url, String startUrl) {
        this.url = url;
        this.startUrl = startUrl;
    }
    public LinksParser(String url, String startUrl, Vector<String> linksList) {
        this(url, startUrl);
        this.linksList = linksList;
    }


    @Override
    protected Vector<String> compute() {
        List<LinksParser> taskList = new ArrayList<>();
        try {
            parsePage(taskList);
        } catch (InterruptedException | IOException ignored) {}
        for(LinksParser task : taskList){
            task.join();
        }
        return linksList;
    }

    private void addLinkToList(Elements elements, List<LinksParser> taskList){
        for (Element element : elements){
            String link = element.attr("abs:href");
            if(!linksList.contains(link) && !link.isEmpty() && !link.contains("#") && link.startsWith(startUrl)){
                LinksParser linkParser= new LinksParser(element.attr("abs:href"), startUrl, linksList);
                linkParser.fork();
                taskList.add(linkParser);
                linksList.add(element.attr("abs:href"));
                System.out.println(element.attr("abs:href") + linksList.size());
            }
        }

    }

    private void parsePage(List<LinksParser> taskList) throws InterruptedException, IOException {
        Thread.sleep(300);
        Document doc = Jsoup.connect(this.url).userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com").get();
        Elements elements = doc.select("a");
        addLinkToList(elements,taskList);
    }
}
