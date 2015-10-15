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
    private static Meal[] Menu;    
    private static App instance = null;
    
    protected App()
    {
        Menu = new Meal[5];
        try
        {
            Document doc = Jsoup.connect("http://butlercatering.se/einstein").get();
            getMenu(doc);
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
    
    public static App getInstance()
    {
        if(instance == null)
        {
            instance = new App();
        }
        return instance;
    }
    
    public static Meal[] getMenu()
    {
        return Menu;
    }
    
    public static Meal getTodaysMenu()
    {
        return Menu[getDay()];
    }
    
    public static void printTodaysMenu()
    {

        Meal men = Menu[getDay()];
        System.out.print(men.getMenu());
            
    }
    
    public static void printWeekMenu()
    {
        for(int i = 0; i < Menu.length; i++)
        {
            if(i == getDay())
            {
                System.out.print("=>");
            }
            System.out.println(Menu[i].getMenu());
            
            System.out.println("---------------------------------\n\n");
        }
    }
    
    private static void getMenu(Document d)
    {
        Elements es = d.getElementsByClass("field-day");
        String dayM = "";
        for(int i = 0; i < es.size() ; i++)
        { 
                
                Element e2 = es.get(i).getElementsByClass("field-label").get(0);
                Element fish = e2.nextElementSibling();
                Element meat = fish.nextElementSibling();
                
                Menu[i] = new Meal(e2.text(),fish.text(),meat.text());
                
        }
    }
    
    private static int getDay()
    {
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.HOUR_OF_DAY) >= 13)
        {
            return c.get(Calendar.DAY_OF_WEEK)-1;
        }
        else
        {
            return c.get(Calendar.DAY_OF_WEEK)-2;
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
        s = s.replaceAll("[^a-zäöåA-ZÄÖÅ0-9\\s]", " ");
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

    public static class Meal
    {
        public String Day;
        public String Meat;
        public String Fish;
        
        public Meal(String d, String m, String f )
        {
            Day = d;
            Meat = m;
            Fish = f; 
        }
        
        public String getMenu()
        {
            return Day +"\n\nFisk:\n" + Fish +"\n\nKött:\n"+ Meat + "\n\n";
        }
        
        public String getShortFish()
        {
            return getShortMeal(Fish);
        }        
        
        public String getShortMeat()
        {
            return getShortMeal(Meat);
        }
        
        
    }
}
