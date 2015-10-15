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
    
    public static void main(String[] args) {
        Menu = new Meal[5];
        try{
            Document doc = Jsoup.connect("http://butlercatering.se/einstein").get();
            getMenu(doc);
            int done = 0;
            
            for(String s : args)
            {
                if(s.equals("-w") && done == 0)
                {
                    printWeekMenu();
                    done = 1;
                }
                if(s.equals("-i") && done == 0)
                {
                    printTodaysMenu(1);
                    done = 1;
                }
            }
            if(done == 0)
            {
                printTodaysMenu(0);;
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
    
    private static void printTodaysMenu(int image)
    {

        Meal men = Menu[getDay()];
        System.out.print(men.getMenu());
        if(image == 1)
        {
            ShowMeal f = new ShowMeal(getShortMeal(men.Fish));
            ShowMeal m = new ShowMeal(getShortMeal(men.Meat));
            f.start();
            m.start();
        }
            
    }
    
    private static void printWeekMenu()
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
                Menu[i] = new Meal();
                Element e2 = es.get(i).getElementsByClass("field-label").get(0);
                Element fish = e2.nextElementSibling();
                Element meat = fish.nextElementSibling();
                                
                Menu[i].Day = e2.text();
                Menu[i].Fish = fish.text();
                Menu[i].Meat= meat.text();
                
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

    private static class Meal
    {
        public String Day;
        public String Meat;
        public String Fish;
        
        
        public String getMenu()
        {
            return Day +"\n\nFisk:\n" + Fish +"\n\nKött:\n"+ Meat + "\n\n";
        }
    }
}
