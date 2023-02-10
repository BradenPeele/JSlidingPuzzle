import java.awt.image.*;
import javax.swing.*;
import java.awt.*;


class PausePanel extends JPanel
{
    BufferedImage img;
    
    PausePanel(BufferedImage img)
    {
        this.img = img;
    }
    
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}
