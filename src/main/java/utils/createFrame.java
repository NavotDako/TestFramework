package utils;

import javax.swing.*;
import java.awt.*;

public class createFrame {
    public static void main(String[] args) {
        //1. Create the frame.
        JFrame frame = new JFrame("FrameDemo");

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//3. Create components and put them in the frame.
//...create emptyLabel...
        frame.getContentPane().add(new Label("ddddd"), BorderLayout.CENTER);

//4. Size the frame.
        frame.pack();
        frame.setResizable(false);
        frame.setSize(new Dimension((int)(77.4)*4 ,((int)157.5)*4));
//5. Show it.
        frame.setVisible(true);
    }
}
