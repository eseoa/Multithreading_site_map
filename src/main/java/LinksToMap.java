import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class LinksToMap {
    static ArrayList<String> test = new ArrayList<>();

    public static ArrayList<String> listToMap(String url, Vector<String> links) throws IOException {
        url = url.charAt(url.length()-1) == '/' ? url.substring(0,url.length()-1) : url;
        ArrayList<String> linksList = new ArrayList<>(links);
        ArrayList<String> newLinksList = new ArrayList<>();
        deleteLastSlash(linksList);
        int maxSize = maximumLinkLength(linksList);
        newLinksList.add(url);
        for(int i = 4; i <= maxSize; i++){
            for(String link : linksList){
                if(!newLinksList.contains(link)) {
                    addNewLink(link, i, newLinksList);
                }
            }
        }
        setTabulation(newLinksList);
        newLinksList.add("");
        newLinksList.addAll(test);
        return newLinksList;
    }

    private static void addNewLink(String link, int i, ArrayList<String> newLinksList){
        if (link.split("/").length == i) {
            String parent = getParent(link);
            if(!newLinksList.contains(parent)){
                if(!newLinksList.contains(getParent(parent))){
                    if(!test.contains(link) && !newLinksList.contains(link)) {
                        test.add(link);
                    }
                }
                else {
                    newLinksList.add(newLinksList.indexOf(getParent(parent)) + 1, parent);
                    newLinksList.add(newLinksList.indexOf(parent) + 1, link);
                }
            }
            else {
                newLinksList.add(newLinksList.indexOf(parent) + 1, link);
            }
        }

    }

    private static void deleteLastSlash(ArrayList<String> linksList){
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < linksList.size(); i++) {
                if (linksList.get(i).charAt(linksList.get(i).length() - 1) == '/') {
                    linksList.set(i, linksList.get(i).substring(0, linksList.get(i).length() - 1));
                }
            }
        }
    }

    private static String getParent(String link){
        StringBuilder parent = new StringBuilder();
        String[] splitLink = link.split("/");
        for (int i = 0; i < splitLink.length-1; i++){
            parent.append(splitLink[i]).append("/");
        }


        return parent.substring(0, parent.length()-1);
    }

    private static String getTabulation(int tabulationsCount){
        StringBuilder tabulation = new StringBuilder();
        for(int i = 0; i < tabulationsCount; i++){
            tabulation.append("\t");
        }
        return tabulation.toString();
    }

    private static void setTabulation(ArrayList<String> linksList){
        int tabulationsCount = 0;
        String tabulations = "";

        for (int i = 0; i < linksList.size(); i++){
            tabulationsCount = linksList.get(i).replaceAll("[^/]","").length()-2;
            tabulations = getTabulation(tabulationsCount);
            linksList.set(i,tabulations+linksList.get(i));
        }

    }

    private static int maximumLinkLength(ArrayList<String> linksList){
        int maxSize = 0;
        for(String link : linksList){
            if(maxSize < link.split("/").length){
                maxSize = link.split("/").length;
            }
        }
        return maxSize;
    }
}
