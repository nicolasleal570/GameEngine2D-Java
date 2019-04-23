package game.engine;

import javax.swing.*;
import java.awt.*;

public class Window  extends JFrame {

    private Toolkit tk = Toolkit.getDefaultToolkit();
    private Dimension screenSize = tk.getScreenSize();

    public Window(){
        setTitle("Game Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(6);
        setResizable(false);
        setContentPane(new GamePanel((int) screenSize.getWidth(), (int) screenSize.getHeight()));
        this.setIgnoreRepaint(true);
        this.pack(); // Compacta todos los elementos en la ventana
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
