import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

import java.util.Scanner; 

import javax.script.*;

import java.lang.Thread;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException; 
   
class ShowMeal extends Thread 
{
    private String meal;

    public ShowMeal(String s)
    {
        meal = s;
    }
    
    public void run()
    {
        try{
        displayMeal(showMeal(meal));
            
        }
        catch(Exception e){}
    }
    
    private static String showMeal(String s) throws Exception
    {
        String format = s.replaceAll(" ","+");
        //format = format.replaceAll("å","%C3%A5");
        //format = format.replaceAll("ä","%C3%A4");
        //format = format.replaceAll("ö","%C3%B6");
        String search = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+format;
        
        Document doc = Jsoup.connect(search).get();
        String JSON = doc.body().text();
        //System.out.println(JSON);
        
        String scriptFile = new Scanner(new File("./jsonhelper.js")).useDelimiter("\\Z").next();
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        engine.eval(scriptFile);
        
        Invocable inv = (Invocable) engine;
        
        String resu = (String) inv.invokeFunction("getImageUrl", JSON);
        
        return resu;
    }
    
    private static void displayMeal(String path)
    {
        Image image = null;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }
}