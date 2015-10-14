import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException; 
import java.util.Calendar;


import java.lang.Thread;


public class App
{
    
    public static void main(String[] args) {
        
        try{
            Document doc = Jsoup.connect("http://butlercatering.se/einstein").get();
            
            int done = 0;
            for(String s : args)
            {
                if(s.equals("-w") || done != 0)
                {
                    System.out.println(getWeekMenu(doc));
                    done = 1;
                }
                if(s.equals("-i") || done != 0)
                {
                    System.out.println(getTodaysMenu(doc,1));
                    done = 1;
                }
            }
            if(done == 0)
            {
                System.out.println(getTodaysMenu(doc,0));
            }
            
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
    
    private static String getTodaysMenu(Document d,int image)
    {
        
        Elements es = d.getElementsByClass("field-day");
        for(Element e: es)
        {
            Element e2 = e.getElementsByClass("field-label").get(0);
            if(weekdayToNr(e2.text())== getDay() )
            {
                Element fish = e2.nextElementSibling();
                Element meat = fish.nextElementSibling();
                
                System.out.println(e2.text()+ "\n");
                System.out.println("Fisk:");
                System.out.println(fish.text()+ "\n");
                System.out.println("Kött:");
                System.out.println(meat.text() + "\n");
                
                if(image == 1)
                {
                    ShowMeal f = new ShowMeal(getShortMeal(fish.text()));
                    ShowMeal m = new ShowMeal(getShortMeal(meat.text()));
                    f.start();
                    m.start();
                }
            }
            
        }
        return "";
    }
    
    private static String getWeekMenu(Document d)
    {
        Elements es = d.getElementsByClass("field-day");
        for(Element e: es)
        { 
                Element e2 = e.getElementsByClass("field-label").get(0);
                Element fish = e2.nextElementSibling();
                Element meat = fish.nextElementSibling();
                if(weekdayToNr(e2.text())== getDay() )
                {
                    System.out.print("=>");
                }
                System.out.println(e2.text()+ "\n");
                System.out.println("Fisk:");
                System.out.println(fish.text()+ "\n");
                System.out.println("Kött:");
                System.out.println(meat.text() + "\n");
                System.out.println("---------------------------------\n\n");
                
                
        }
        return "";
    }
    
    private static int getDay()
    {
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.HOUR_OF_DAY) >= 13)
        {
            return c.get(Calendar.DAY_OF_WEEK);
        }
        else
        {
            return c.get(Calendar.DAY_OF_WEEK)-1;
        }
        
        
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
    
    private static String getShortMeal(String s)
    {
        String[] ss = s.split(" ");
        String res = "";
        int i = 0;
        while(i < ss.length-1 )
        {
            if (ss[i].equals("med") || ss[i].equals("serveras"))
            {
                i = ss.length; 
            }
            else
            {
                res = res + ss[i] + " " ;
                i++;
            }
            
        }
        return res;
    }

}
