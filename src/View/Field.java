package View;

import Model.Match.Match;
import Model.Match.Point;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Field is object that is used to represent graphically a given match.
 */
public class Field extends JPanel implements ActionListener {
    // Screen size
    private final int B_WIDTH = 1200;
    private final int B_HEIGHT = 1400;

    // Refresh delay
    private final int DELAY = 250;

    // The shown match
    Match match;

    // The timer used
    private Timer timer;


    /**
     * Constructors a Field given a match.
     * @param match a match
     */
    public Field(Match match) {
        this.match = match;
        initField();
    }

    /**
     * Returns true if a given match has a valid time.
     * @param time a time.
     * @return true if the time is valid.
     */
    public static boolean playTime(double time) {
        return (time >= 0 && time < 45) || (time > 45 && time < 90);
    }

    /**
     * Initializes this Field object.
     */
    private void initField() {
        setBackground(Color.YELLOW);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Paints the Field.
     * @param g a graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawField(g);
    }

    /**
     * Draws the football field.
     * @param g a graphics.
     */
    private void drawField(Graphics g) {
        setBackground(Color.YELLOW);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
        g.drawString("Football field", 300, 100);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("Home are red, Away are blue", 300, -200);

        // Drawing the score
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.drawString(this.match.getScoreHome() + "-" + this.match.getScoreAway(), 300 + 5 * 60, 200);

        g.setColor(Color.GREEN);

        g.fillRect(300, 200, 624, 423);
        g.setColor(Color.BLACK);

        // Balizas
        g.fillRect(270, 350, 30, 120);
        g.fillRect(920, 350, 30, 120);

        // Áreas
        g.drawRect(300, 280, 180, 240);
        g.drawRect(744, 280, 180, 240);
        g.drawOval(612, 390, 30, 30);
        Graphics2D g2d = (Graphics2D) g.create();

        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(300 + 5 * 30, 200, 300 + 5 * 30, (int) (200 + 90 * 4.5));
        g2d.drawLine(300 + 5 * 90, 200, 300 + 5 * 90, (int) (200 + 90 * 4.5));

        // Gets rid of the copy
        g2d.dispose();

        // Time of the game
        g.drawString(String.valueOf(Math.round(this.match.getTime())), 300, 200);

        g.setColor(Color.BLACK);
        g.fillOval((5 * (int) match.getBall_tracker().getX()) + 300, (int) (4.5 * this.match.getBall_tracker().getY()) + 200, 12, 12);

        g.setColor(Color.RED);
        for (Point x : this.match.getHomePl().playersPosition()) {
            //System.out.println(x);
            g.fillOval((5 * (int) x.getX()) + 300, (int) (4.5 * x.getY()) + 200, 10, 10);
        }
        //Imprime os de fora

        g.setColor(Color.BLUE);
        for (Point x : this.match.getAwayPl().playersPosition()) {
            //System.out.println(x);
            g.fillOval((5 * (int) x.getX()) + 300, (int) (4.5 * x.getY()) + 200, 10, 10);
        }
        //setForeground(Color.GREEN);


        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Given a event, this method does something.
     * @param e a actionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (playTime(match.getTime())) {
            match.run(0.25); // Runs the game if the game time is not over.
            repaint();
        }
    }
}
