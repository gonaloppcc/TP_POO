package View;

import Model.Match.Match;
import Model.Match.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DisplayGraphics extends Canvas {
    List<Point> home;
    List<Point> away;
    Point ball;
    public DisplayGraphics(List<Point> homeI, List<Point> awayI, Point ballI) {
        home = homeI;
        away = awayI;
        ball = ballI;
    }
    public void paint(Graphics g) {
        setBackground(Color.YELLOW);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString("Football field",300,100);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("Home are red, Away are blue",300,400);


        g.setColor(Color.GREEN);

        g.fillRect(300,200,624,423);
        g.setColor(Color.BLACK);
//Balizas
        g.fillRect(270, 350, 30, 120);
        g.fillRect(920, 350, 30, 120);
        //Áreas
        g.drawRect(300, 280, 180, 240);
        g.drawRect(744, 280, 180, 240);
        g.fillOval(612,390,30, 30);
        Graphics2D g2d = (Graphics2D) g.create();

        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(300+5*30, 200, 300+5*30,  (int)(200 +  90*4.5));
        g2d.drawLine(300+5*90, 200, 300+5*90,  (int)(200 +  90*4.5));

        //gets rid of the copy
        g2d.dispose();
        g.setColor(Color.RED);
        //g.drawOval((int) (5*120) + 300, (int) (4.5*90) +200, 20, 20);
        //Imprime os de casa

        for(Point x: home){
            g.fillOval((5*(int)x.getX()) + 300, (int) (4.5* x.getY()) + 200, 10, 10);
        }
        //Imprime os de fora

        g.setColor(Color.BLUE);
        for(Point x: away){
            g.fillOval((5*(int)x.getX()) + 300, (int) (4.5 * x.getY()) + 200, 10, 10);
        }
        //setForeground(Color.GREEN);
    }
    public void campo(List<Point> home, List<Point> away, Point ball){
        DisplayGraphics m=new DisplayGraphics(home, away, ball);
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(1000,1000);
        //f.setLayout(null);
        f.setVisible(true);

    }

}
