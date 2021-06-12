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
        this.windowContents.add(this.label);
        this.window.add(this.windowContents);
        this.window.pack();
        this.window.setLocationRelativeTo(null);
        final int desiredWidth = this.window.getWidth();
        this.window.getContentPane().setLayout(null);
        this.window.setSize(0, this.window.getHeight());
        this.window.setVisible(true);
        Timer timer = new Timer(20, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int newWidth = Math.min(window.getWidth() + 1, desiredWidth);
                window.setSize(newWidth, window.getHeight());
                windowContents.setLocation(newWidth - desiredWidth, 0);
                if (newWidth >= desiredWidth) {
                    ((Timer) e.getSource()).stop();
                    label.setForeground(Color.red);
                    System.exit(0);
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
