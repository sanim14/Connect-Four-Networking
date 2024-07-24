import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class TTTFrame extends JFrame implements MouseListener, KeyListener
{
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // store the letter of the active player
    private char turn = 'X';
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server


    private int xOfLeft = 50;
    private int yOfLeft = 50;
    ObjectOutputStream os;


    public TTTFrame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("TTT Game");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;


        // adds a MouseListener to the Frame
        addMouseListener(this);

        // adds a KeyListener to the Frame
        addKeyListener(this);

        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        if (player == 'X')
        {
            text = "Waiting for O to Connect";
        }
        setSize(540,540);
        setAlwaysOnTop(true);
        setVisible(true);
    }


    public void paint(Graphics g)
    {
        // draws the background
        g.setColor(new Color(175, 238, 238));
        g.fillRect(0,0,getWidth(),getHeight());


        // draws the display text to the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString(text,20,65);


        g.setColor(Color.WHITE);
        int x = 30;
        int y = 100;
        for(int r=0; r<6; r++)
        {
            for (int c = 0; c < 7; c++)
            {
                if (gameData.getGrid()[r][c] == 'X')
                {
                    g.setColor(Color.red);
                    g.fillOval(x, y, 50, 50);
                    x += 70;
                }
                else if (gameData.getGrid()[r][c] == 'O')
                {
                    g.setColor(Color.black);
                    g.fillOval(x, y, 50, 50);
                    x += 70;
                }
                else {
                    g.setColor(Color.white);
                    g.fillOval(x, y, 50, 50);
                    x += 70;
                }
                g.setColor(Color.white);
            }
            x = 30;
            y += 70;
        }

    }


    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public char getPlayer() {
        return player;
    }


    public void setPlayer(char player) {
        this.player = player;
    }




    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
        if (turn == player)
        {
            text = "Your turn";
        }
        else
        {
            if (turn == 'X')
            {
                text = "Red's turn.";
            }
            else
            {
                text = "Black's turn.";
            }
        }
        repaint();
    }




    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }


    public void reset()
    {
        gameData.reset();
    }


    public char other()
    {
        if(player=='x')
            return 'o';
        else
            return 'x';
    }


    public GameData getGameData()
    {
        return gameData;
    }



    @Override
    public void mouseClicked(MouseEvent e)
    {

    }


    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        char key = e.getKeyChar();

        if (key == 'R')
        {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.RESTART, gameData + ""));
            }
            catch(Exception o)
            {
                o.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        /*char key = e.getKeyChar();

        if (key == 'R')
        {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.RESTART, ""));
                gameData.reset();
            }
            catch(Exception o)
            {
                o.printStackTrace();
            }
        }*/
    }




    @Override
    public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            xOfLeft = x;
            yOfLeft = y;
        }

        int row = 0;
        int col = 0;
        x = 30;
        y = 100;
        for (int r = 0; r < 11; r++) {
            for (int c = 0; c < 11; c++) {
                if (xOfLeft >= x && xOfLeft <= x + 40 && yOfLeft >= y && yOfLeft <= y + 40) {
                    row = r;
                    col = c;
                    break;
                }
                x += 70;
            }
            x = 30;
            y += 70;
        }

        if (dropPiece(col, row))
        {
            try
            {
                os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + col + row + player));
            }
            catch(Exception l)
            {
                l.printStackTrace();
            }
        }
    }




    @Override
    public void mouseReleased(MouseEvent e)
    {


    }


    @Override
    public void mouseEntered(MouseEvent e) {


    }


    @Override
    public void mouseExited(MouseEvent e) {


    }


    public boolean dropPiece(int column, int row)
    {
        if (row == gameData.getGrid().length-1)
        {
            return true;
        }
        else
        {
            if (row + 1 < gameData.getGrid().length)
            {
                if (gameData.getGrid()[row + 1][column] != ' ')
                {
                    return true;
                }
            }
        }
        return false;
    }
}
