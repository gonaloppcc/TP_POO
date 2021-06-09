package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlideTextSwing {

    private JWindow window = new JWindow();
    private JLabel label = new JLabel("Créditos:  Gonçalo e Tomás fizeram o jogo \n Diogo fez a interação com o jogador");
    private JPanel windowContents = new JPanel();

    public SlideTextSwing() {
        windowContents.add(label);
        window.add(windowContents);
        window.pack();
        window.setLocationRelativeTo(null);
        final int desiredWidth = window.getWidth();
        window.getContentPane().setLayout(null);
        window.setSize(0, window.getHeight());
        window.setVisible(true);
        Timer timer = new Timer(20, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int newWidth = Math.min(window.getWidth() + 1, desiredWidth);
                window.setSize(newWidth, window.getHeight());
                windowContents.setLocation(newWidth - desiredWidth, 0);
                if (newWidth >= desiredWidth) {
                    ((Timer) e.getSource()).stop();
                    label.setForeground(Color.red);
                    return;
                }
            }
        });
        timer.start();
    }

    public void mainKill() {
        Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        timer.start();
    }

}
