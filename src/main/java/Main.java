import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static final String URL = "";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("Начало работы");
        LinksParser linksParser = new LinksParser(URL,URL);
        try {
            writeFiles(LinksToMap.listToMap(URL, new ForkJoinPool().invoke(linksParser)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Время работы " + ((System.currentTimeMillis() - start) / 1000) + " сек.");
    }

    private static void writeFiles(ArrayList<String> linksList) throws FileNotFoundException {
        String filePath = "map.txt";
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        for (String link : linksList){
            writer.write(link + "\n");
        }
    }
}
