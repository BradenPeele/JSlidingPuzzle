import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;


class Cropper
{
    int length;
    BufferedImage ogImg;
    Button[][] buttonArr;
    Point[][] pointArr;
    Point emptySquare;
    JPanel panel;
    
    
    Cropper()
    {
        buttonArr = new Button[Board.numRows][Board.numRows];
        pointArr = new Point[Board.numRows][Board.numRows];
        panel = new JPanel();
        panel.setLayout(new GridLayout(Board.numRows, Board.numRows));
    }
    
    // loads picture and crops it to a square
    void loadPicture(File file)
    {
        try
        {
            ogImg = ImageIO.read(file);
        }
            catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "IO exception", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        if(ogImg.getHeight() > ogImg.getWidth())
        {
            length = ogImg.getWidth();
            ogImg = ogImg.getSubimage(0, (ogImg.getHeight() - length) / 2, length, length);
        }
            
        else if(ogImg.getWidth() > ogImg.getHeight())
        {
            length = ogImg.getHeight();
            ogImg = ogImg.getSubimage((ogImg.getWidth() - length) / 2, 0, length, length);
        }
        
        length = ogImg.getWidth() / Board.numRows;
        
        populateArray();
    }
    
    // creates Button array and sets empty square
    void populateArray()
    {
        int row;
        
        for(row = 0; row < Board.numRows; row++)
        {
            for(int col = 0; col < Board.numRows; col++)
            {
                if(row == Board.numRows - 1 && col == Board.numRows - 1)
                {
                    BufferedImage bi = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = bi.createGraphics();
                    JPanel iconPanel = new JPanel();
                    iconPanel.paint(g);
                    g.dispose();
                    buttonArr[row][row] = new Button(bi, row, row);
                }
                
                else
                    buttonArr[row][col] = new Button(ogImg.getSubimage(col * length, row * length, length, length), row, col);
                
                panel.add(buttonArr[row][col]);
            }
        }
        
        emptySquare = new Point(row-1, row-1);
    }

    
    JPanel getPanel()
    {
        return panel;
    }
    
    
    Button[][] getButtonArr()
    {
        return buttonArr;
    }
    
    
    Point getBlankLocation()
    {
        return emptySquare;
    }
    
    // returns original image to PausePanel for bakground of pause screen
    BufferedImage getImage()
    {
        Image original = ogImg.getScaledInstance(600, 510, Image.SCALE_DEFAULT);
        ogImg = new BufferedImage(ogImg.getWidth(), ogImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ogImg.getGraphics().drawImage(original, 0, 0, null);
        
        return ogImg;
    }
}
