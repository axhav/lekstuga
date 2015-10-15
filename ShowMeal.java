import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

import java.util.Scanner; 

import javax.script.*;

import java.lang.Class;
import java.lang.Thread;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
        displayMeal(showMeal(meal),meal);
            
        }
        catch(Exception e){}
    }
    
    private static String showMeal(String s) throws Exception
    {
        String format = s.replaceAll(" ","+");
        String search = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+format+"+-site:butlercatering.se";
        
        Document doc = Jsoup.connect(search).get();
        String JSON = doc.body().text();
        
        //String scriptFile = new Scanner(new File("./jsonhelper.js")).useDelimiter("\\Z").next();
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        engine.eval(JS());
        
        Invocable inv = (Invocable) engine;
        
        String resu = (String) inv.invokeFunction("getImageUrl", JSON);
        return resu;
    }
    
    private static void displayMeal(String path,String Name)
    {
        Image image = null;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);
            image = image.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setTitle(Name);
        JLabel label = new JLabel(new ImageIcon(image));
        JLabel text = new JLabel();
        frame.add(label);
        //frame.pack();
        frame.setVisible(true);
    }
    
    
    private static String JS()
    {
        return "function getImageUrl(json){var myArr = JSON.parse(json);return myArr[\"responseData\"][\"results\"][0][\"unescapedUrl\"];}";
    }
}
