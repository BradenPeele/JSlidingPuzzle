import java.awt.image.*;
import javax.swing.*;
import java.awt.*;


class Button extends JButton
{
    int row;
    int col;
    
    
    Button(BufferedImage img, int row, int col)
    {
        setIcon(new ImageIcon(img.getScaledInstance(200, 170, Image.SCALE_DEFAULT)));
        this.row = row;
        this.col = col;
    }
    
    
    void swap(Button empty)
    {
        Icon tempIcon = this.getIcon();
        setIcon(empty.getIcon());
        empty.setIcon(tempIcon);
        
        int tempRow =  row;
        int tempCol = col;
        setRow(empty.getRow());
        setCol(empty.getCol());
        empty.setRow(tempRow);
        empty.setCol(tempCol);
    }
    
    
    int getRow()
    {
        return row;
    }
    
    
    int getCol()
    {
        return col;
    }
    
    
    void setRow(int row)
    {
        this.row = row;
    }
    
    
    void setCol(int col)
    {
        this.col = col;
    }
}
