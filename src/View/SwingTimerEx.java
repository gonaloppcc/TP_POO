package View;

import Model.Match.Match;
import Model.Match.Point;

import java.awt.EventQueue;
import java.util.List;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void displayField(Match match) {

        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx(match);
            ex.setVisible(true);
        });
    }
}