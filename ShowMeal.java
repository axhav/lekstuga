import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;

import java.util.Scanner; 

import javax.script.*;

import java.lang.Thread;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException; 
   
import java.nio.charset.Charset;
import java.nio.ByteBuffer;   
import java.nio.charset.StandardCharsets;   
 
class ShowMeal 
{
    private static String meal;
    
    public ShowMeal(String s)
    {
        meal = s;
    }
    
    
    public static Image showMeal() throws Exception
    {
        String format = meal.replaceAll(" ","+");
        format = format.toLowerCase().replaceAll("å","%C3%A5");
        format = format.replaceAll("ä","%C3%A4");
        format = format.replaceAll("ö","%C3%B6");
        String search = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+format+"+-site:butlercatering.se+-site:youtube.*";
        Document doc = Jsoup.connect(search).get();
        String JSON = doc.body().text();
        
        //String scriptFile = new Scanner(new File("./jsonhelper.js")).useDelimiter("\\Z").next();
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        engine.eval(JS());
        
        Invocable inv = (Invocable) engine;
        
        String resu = (String) inv.invokeFunction("getImageUrl", JSON);
        return getMealImage(resu);
    }
    
    private static Image getMealImage(String path)
    {
        Image image = null;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);
            image = image.getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
        
    }

    
    
    private static String JS()
    {
        //print(JSON.stringify(myArr,null,2));
        return "function getImageUrl(json){var myArr = JSON.parse(json);return myArr[\"responseData\"][\"results\"][0][\"unescapedUrl\"];}";
    }
}