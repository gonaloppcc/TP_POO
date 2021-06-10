package View;

import Model.Match.Match;
import Model.Match.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DisplayGraphics extends Canvas {
    List<Point> home;
    List<Point> away;
    public DisplayGraphics(List<Point> homeI, List<Point> awayI) {
        home = homeI;
        away = awayI;
    }
    public void paint(Graphics g) {
        setBackground(Color.YELLOW);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString("Football field",300,100);


        g.setColor(Color.GREEN);

        g.fillRect(300,200,624,423);
        g.setColor(Color.BLACK);
//Balizas
        g.fillRect(285, 350, 30, 120);
        g.fillRect(920, 350, 30, 120);
        //Áreas
        g.drawRect(300, 280, 180, 240);
        g.drawRect(744, 280, 180, 240);
        g.fillOval(612,390,30, 30);
        //Imprime os de casa
        g.setColor(Color.RED);
        for(Point x: home){
            g.drawOval((5*(int)x.getX()) + 300, (5* (int) x.getY()) + 200, 20, 20);
        }
        g.setColor(Color.BLUE);
        for(Point x: away){
            g.drawOval((5*(int)x.getX()) + 300, (5* (int) x.getY()) + 200, 20, 20);
        }
        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("pooField.png");
        g.drawImage(i, 120,100,this);

        //setForeground(Color.GREEN);
    }
    public void campo(List<Point> home, List<Point> away){
        DisplayGraphics m=new DisplayGraphics(home, away);
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(1000,1000);
        //f.setLayout(null);
        f.setVisible(true);
    }

}
