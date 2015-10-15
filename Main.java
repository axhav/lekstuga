import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.*;

class Main
{
    public static void main(String[] args) 
    {
        App menu = App.getInstance();
        int done = 0;
        for(String s : args)
        {
            if(s.equals("-w") && done == 0)
            {
                menu.printWeekMenu();
                done = 1;
            }
            if(s.equals("-i") && done == 0)
            {
                try
                {
                    Image f = new ShowMeal(menu.getTodaysMenu().getShortFish()).showMeal();
                    Image m = new ShowMeal(menu.getTodaysMenu().getShortMeat()).showMeal();
                    menu.printTodaysMenu();
                    displayMeal(f,m,menu.getTodaysMenu().getShortFish(),menu.getTodaysMenu().getShortMeat());
    
                }
                catch (Exception e ) {}
                done = 1;
            }
        }
        if(done == 0)
        {
            menu.printTodaysMenu();
        }
    }
    
    private static void displayMeal(Image f, Image m,String fish,String meat)
    {
        BorderLayout layout = new BorderLayout();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setTitle("Einstein");
        
        JPanel ic = new JPanel();
        ic.setLayout(new GridLayout(1,2));
        ic.add(new JLabel(new ImageIcon(f)));
        ic.add(new JLabel(new ImageIcon(m)));
        frame.add(ic,BorderLayout.CENTER);
        
        
        JPanel tc = new JPanel();
        tc.setLayout(new GridLayout(1,2));
        JLabel fl = new JLabel(fish);
        fl.setPreferredSize(new Dimension(200, 50));
        tc.add(fl,BorderLayout.SOUTH);
        
        JLabel fm = new JLabel(meat);
        fm.setPreferredSize(new Dimension(200, 50));
        tc.add(fm,BorderLayout.SOUTH);
        frame.add(tc,BorderLayout.SOUTH);
        
        //frame.pack();
        frame.setVisible(true);
    }
}