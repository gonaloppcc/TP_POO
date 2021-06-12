package View;

import Model.Match.Match;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * SwingTimerEx is a timer that is used to refresh the screen.
 */
public class SwingTimerEx extends JFrame {

    /**
     * Constructs a SwingTimerEx given a match.
     * @param match a match.
     */
    public SwingTimerEx(Match match) {

        initUI(match);
    }

    /**
     * Initializes this SwingTimerEx object given a match.
     * @param match a match.
     */
    private void initUI(Match match) {
        add(new Field(match));

        setResizable(false);
        pack();

        setTitle("Football Field");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Displays the given match.
     * @param match a match
     */
    public static void displayField(Match match) {
        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx(match);
            ex.setVisible(true);
        });
    }
}