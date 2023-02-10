import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class BoardFrame extends JFrame implements ActionListener
{
    Button[][] buttonArr;
    Board board;
    JPanel gamePanel, buttonPanel, labelPanel;
    PausePanel pausePanel;
    JButton exitButton, newImageButton, playPauseButton;
    JLabel timeLabel, numMovesLabel, numIncorrectPiecesLabel;
    javax.swing.Timer timer;
    int pauseCounter;
    int moveCounter;
    int elapsedTime;
    
    
    BoardFrame()
    {
        gamePanel = new JPanel();
        add(gamePanel, BorderLayout.CENTER);
        setupButtonPanel();
        setupLabelPanel();
        setupFrame();
        
        newImageButton.doClick();
    }
    
    
    void setupButtonPanel()
    {
        buttonPanel = new JPanel();
        
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
        
        newImageButton = new JButton("New Image");
        newImageButton.addActionListener(this);
        buttonPanel.add(newImageButton);
        
        playPauseButton = new JButton("Pause");
        playPauseButton.addActionListener(this);
        buttonPanel.add(playPauseButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    
    void setupLabelPanel()
    {
        labelPanel = new JPanel();
        
        pauseCounter = 0;
        moveCounter = 0;
        elapsedTime = 0;
        
        timeLabel = new JLabel("00:00:00");
        labelPanel.add(timeLabel);
        
        // time taken to solve
        timer = new javax.swing.Timer(1000 ,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                elapsedTime = elapsedTime + 1000;
                timeLabel.setText(String.format("%02d:%02d:%02d  ", elapsedTime/3600000, (elapsedTime/60000) % 60, (elapsedTime/1000) % 60));
            }
        });
       
        JLabel label = new JLabel("Moves: ");
        labelPanel.add(label);
        
        numMovesLabel = new JLabel("0");
        labelPanel.add(numMovesLabel);
        
        label = new JLabel("Number Incorrect: ");
        labelPanel.add(label);
        
        numIncorrectPiecesLabel = new JLabel();
        labelPanel.add(numIncorrectPiecesLabel);
        
        add(labelPanel, BorderLayout.NORTH);
    }
    
    
    void setupFrame()
    {
        Toolkit tk;
        Dimension d;
            
        setTitle("Sliding Puzzle");
        
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        
        double width = d.width / 2.4;
        double height = d.height / 1.5;
        
        setSize((int)width, (int)height);
        setLocation(d.width / 4, d.height / 4);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        setVisible(true);
        setResizable(false);
    }
    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == exitButton)
        {
            System.exit(0);
        }
        
        
        if(e.getSource() == newImageButton)
        {
            JFileChooser fileChooser = new JFileChooser();
            
            if(fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
                return;
            
            Cropper cropper = new Cropper();
            cropper.loadPicture(fileChooser.getSelectedFile());
            board = new Board(cropper.getButtonArr(), cropper.getBlankLocation());
            buttonArr = cropper.getButtonArr();
            pausePanel = new PausePanel(cropper.getImage());
            
            for(int row = 0; row < Board.numRows; row++)
            {
                for(int col = 0; col < Board.numRows; col++)
                {
                   buttonArr[row][col].addActionListener(this);
                }
            }
            
            // reset timer
            timer.stop();
            elapsedTime = 0;
            timeLabel.setText(String.format("%02d:%02d:%02d  ", elapsedTime/3600000, elapsedTime/60000, elapsedTime/1000));
            
            // swap panels
            remove(gamePanel);
            gamePanel = cropper.getPanel();
            add(gamePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
            
            // shuffle pieces
            board.shuffle();
            revalidate();
            repaint();
            
            moveCounter = 0;
            numMovesLabel.setText(moveCounter + "");
        }
        
        // play / pause button
        if(e.getSource() == playPauseButton)
        {
            pauseCounter++;
            
            if(pauseCounter % 2 == 1)
            {
                playPauseButton.setText("Play");
                remove(gamePanel);
                add(pausePanel, BorderLayout.CENTER);
                newImageButton.setEnabled(false);
                timer.stop();
            }
            
            else
            {
                playPauseButton.setText("Pause");
                remove(pausePanel);
                add(gamePanel, BorderLayout.CENTER);
                newImageButton.setEnabled(true);
                timer.start();
            }
            
            revalidate();
            repaint();
        }
        
        // button clicked
        for(int row = 0; row < Board.numRows; row++)
        {
            for(int col = 0; col < Board.numRows; col++)
            {
                if(e.getSource() == buttonArr[row][col])
                {
                    // if valid swap
                    if(board.swap(row, col))
                    {
                        timer.start();
                        moveCounter++;
                        numMovesLabel.setText(moveCounter + "");
                    }
                    
                    gamePanel.revalidate();
                    revalidate();
                }
            }
        }
        
        numIncorrectPiecesLabel.setText(board.numberIncorrect() + "");
        
        // puzzle completed
        if(board.numberIncorrect() == 0)
        {
            timer.stop();
            JOptionPane.showMessageDialog(null, "Puzzle Completed", "Finished", 1);
        }
    }
}
