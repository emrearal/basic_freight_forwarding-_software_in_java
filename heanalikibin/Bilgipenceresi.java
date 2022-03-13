
package heanalikibin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Bilgipenceresi
{


    public static void anons(String onons)
    {
    	JTextArea area;
    	JFrame f;
        f = new JFrame();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        area = new JTextArea();
        area.setEditable(false);
        area.setSize(200, 200);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(180, 90));
        f.add(sp, BorderLayout.PAGE_START);
        area.setText(onons);
        f.setLocation(500, 50);
        f.pack();
        f.setVisible(true);
    }
}
