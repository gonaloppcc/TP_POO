package View;

import Model.Match.Match;
import Model.Match.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * In this class we show a representation of the field, with the players included.
 * We don't distinguise (TOMÀS; ACHO QUE ESTÀS MAL ESCRITO) the players, so we only need their positions.
 */
public class DisplayGraphics extends Canvas implements ActionListener {
    List<Point> home;
    List<Point> away;
    Point ball;
/*----------------------- Constructor ---------------------------*/
    public DisplayGraphics(List<Point> homeI, List<Point> awayI, Point ballI) {
        home = homeI;
        away = awayI;
        ball = ballI;
    }
    /*----------------------- Field representation ---------------------------*/

    /**
     * Here we show the field, with BALIZAS and players.
     * Some of that is described in code.
     * @param g
     */
    public void paint(Graphics g) {
        setBackground(Color.YELLOW);
        //System.out.println(this.home);
        g.setColor(Color.BLACK);
        // Title
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString("Football field",300,100);


        g.setColor(Color.GREEN);

        g.fillRect(300,200,624,423);
        g.setColor(Color.BLACK);
//BALIZAS EM INGLÊS
        g.fillRect(270, 350, 30, 120);
        g.fillRect(920, 350, 30, 120);
        //Áreas
        g.drawRect(300, 280, 180, 240);
        g.drawRect(744, 280, 180, 240);
        //Midfield
        g.drawOval(612,390,30, 30);
        Graphics2D g2d = (Graphics2D) g.create();
        //Delimiters of defense and attack area.
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(300+5*30, 200, 300+5*30,  (int)(200 +  90*4.5));
        g2d.drawLine(300+5*90, 200, 300+5*90,  (int)(200 +  90*4.5));

        g2d.dispose();
        g.setColor(Color.RED);

        g.setColor(Color.BLACK);

        //Home players
        for(Point x: home){
            g.fillOval((5*(int)x.getX()) + 300, (int) (4.5* x.getY()) + 200, 10, 10);
        }
        //Outside players

        g.setColor(Color.BLUE);
        for(Point x: away){
            g.fillOval((5*(int)x.getX()) + 300, (int) (4.5 * x.getY()) + 200, 10, 10);
        }
        //setForeground(Color.GREEN);
    }

    /**
     * Inicializes the window where the game occurs.
     * @param home Position of home players
     * @param away Position of away players
     * @param ball Position of the ball
     */
    public void campo(List<Point> home, List<Point> away, Point ball){
        DisplayGraphics m=new DisplayGraphics(home, away, ball);
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(1000,1000);
        //f.setLayout(null);
        f.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        EventQueue.invokeLater(() -> {

        });
    }
}
