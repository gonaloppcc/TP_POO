package View;

import Model.Match.Match;
import Model.Match.Point;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel implements ActionListener {

    private final int B_WIDTH = 1200;
    private final int B_HEIGHT = 1400;
    private final int DELAY = 500;
    Match match;
    private Timer timer;


    public Field(Match match) {
        this.match = match;
        initField();
    }

    private void initField() {

        setBackground(Color.YELLOW);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawField(g);
    }

    private void drawField(Graphics g) {
            setBackground(Color.YELLOW);
            //System.out.println(this.home);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g.drawString("Football field", 300, 100);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("Home are red, Away are blue", 300, -200);

            // Drawing the score
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString(match.getScoreHome() + "-" + match.getScoreAway(), 300 + 5 * 60, 200);

            g.setColor(Color.GREEN);

            g.fillRect(300, 200, 624, 423);
            g.setColor(Color.BLACK);
//Balizas
            g.fillRect(270, 350, 30, 120);
            g.fillRect(920, 350, 30, 120);
            //Áreas
            g.drawRect(300, 280, 180, 240);
            g.drawRect(744, 280, 180, 240);
            g.drawOval(612, 390, 30, 30);
            Graphics2D g2d = (Graphics2D) g.create();

            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(300 + 5 * 30, 200, 300 + 5 * 30, (int) (200 + 90 * 4.5));
            g2d.drawLine(300 + 5 * 90, 200, 300 + 5 * 90, (int) (200 + 90 * 4.5));

            //gets rid of the copy
            g2d.dispose();

            // Time of the game
            g.drawString(String.valueOf(match.getTime()), 300, 200);

            g.setColor(Color.BLACK);
            g.fillOval((5 * (int) match.getBall_tracker().getX()) + 300, (int) (4.5 * match.getBall_tracker().getY()) + 200, 12, 12);

            g.setColor(Color.RED);
            for (Point x : match.getHomePl().playersPosition()) {
                //System.out.println(x);
                g.fillOval((5 * (int) x.getX()) + 300, (int) (4.5 * x.getY()) + 200, 10, 10);
            }
            //Imprime os de fora

            g.setColor(Color.BLUE);
            for (Point x : match.getAwayPl().playersPosition()) {
                //System.out.println(x);
                g.fillOval((5 * (int) x.getX()) + 300, (int) (4.5 * x.getY()) + 200, 10, 10);
            }
            //setForeground(Color.GREEN);


            Toolkit.getDefaultToolkit().sync();
        if (match.getTime() >= 90); // Game over
            //gameOver(g);
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (match.getTime() > 90) {
            //System.exit(0);
        } else {
            match.run(1);
        }
        repaint();
    }
}
