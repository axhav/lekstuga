import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;


public class App
{
    
    public static void main(String[] args) {
        
        try{
        Document doc = Jsoup.connect("http://butlercatering.se/einstein").get();
        
        System.out.println(getTodaysMenu(doc));
        }
        catch (java.net.UnknownHostException e) 
        {
            System.out.println("Error: No network connection available");
            System.exit(0);
        }
        catch (Exception e)
        {
            if (e instanceof java.net.UnknownHostException) {
                System.out.println("Error: Unknown host "+e.getMessage());
            } else {
                System.out.println(e.toString());
            }
            System.exit(0); 
        }
    }
    
    private static String getTodaysMenu(Document d)
    {
        
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) -1;
        
        Elements es = d.getElementsByClass("field-day");
        for(Element e: es)
        {
            Element e2 = e.getElementsByClass("field-label").get(0);
            if(weekdayToNr(e2.text())== day_of_week )
            {
                Element fish = e2.nextElementSibling();
                Element meat = fish.nextElementSibling();
                
                System.out.println(e2.text()+ "\n");
                System.out.println("Fisk:");
                System.out.println(fish.text()+ "\n");
                System.out.println("Kött:");
                System.out.println(meat.text());
                
            }
            
        }
        return "";
    }
    
    private static int weekdayToNr(String s)
    {
        switch (s.toLowerCase())
        {
            case "måndag":
                return 1;
            case "tisdag":
                return 2;
            case "onsdag":
                return 3;
            case "torsdag":
                return 4;
            case "fredag":
                return 5;
            default:
                return -1;
        }
        
    }
}
