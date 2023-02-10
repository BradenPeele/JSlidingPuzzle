import java.awt.*;
import java.util.*;


class Board
{
    public static final int numRows = 3;
    
    Button[][] buttonArr;
    int emptyRow;
    int emptyCol;
    int numIncorrect;
    
    
    Board(Button[][] buttonArr, Point point)
    {
        this.buttonArr = buttonArr;
        emptyRow = (int)point.getX();
        emptyCol = (int)point.getY();
    }
    
    
    boolean swap(int row, int col)
    {
        if(row >= numRows || row < 0 || col >= numRows || col < 0)
            return false;
        
        if(row == emptyRow && col == emptyCol)
           return false;
        
        if(row != emptyRow && col != emptyCol)
            return false;
        
        if(row == emptyRow)
        {
            if(col > emptyCol + 1 || col < emptyCol - 1)
                return false;
        }
        
        if(col == emptyCol)
        {
            if(row > emptyRow + 1 || row < emptyRow - 1)
                return false;
        }
        
        buttonArr[row][col].swap(buttonArr[emptyRow][emptyCol]);
    
        emptyRow = row;
        emptyCol = col;
    
        return true;
    }
    
    
    void shuffle()
    {
        Random random = new Random();
        
        int n = 0;
        
        while(n < 40)
        {
            if(random.nextInt(4) == 0)
            {
                if(swap(emptyRow - 1, emptyCol));
                    n++;
            }
            
            if(random.nextInt(4) == 1)
            {
                if(swap(emptyRow, emptyCol - 1));
                    n++;
            }
            
            if(random.nextInt(4) == 2)
            {
                if(swap(emptyRow + 1, emptyCol));
                    n++;
            }
            
            if(random.nextInt(4) == 3)
            {
                if(swap(emptyRow, emptyCol + 1));
                    n++;
            }
        }
    }
    
    // called after every swap
    int numberIncorrect()
    {
        numIncorrect = 0;
        
        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numRows; col++)
            {
                if(buttonArr[row][col].getRow() != row || buttonArr[row][col].getCol() != col)
                    numIncorrect++;
            }
        }
        
        return numIncorrect;
    }
}
