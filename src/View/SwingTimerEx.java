package View;

import Model.Match.Match;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SwingTimerEx extends JFrame {

    public SwingTimerEx(Match match) {

        initUI(match);
    }

    private void initUI(Match match) {

        add(new Field(match));

        setResizable(false);
        pack();

        setTitle("Football Field");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void displayField(Match match) {

        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx(match);
            ex.setVisible(true);
        });
    }
}