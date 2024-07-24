public class GameData
{
    private char[][] grid = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };
    public char[][] getGrid()
    {
        return grid;
    }
    public void reset()
    {
        System.out.println("clear");
        //grid = new char[6][7];
        for(int r=0;r<grid.length; r++)
            for(int c=0; c<grid[0].length; c++)
                grid[r][c]=' ';
    }


    public boolean isCat()
    {
        for(int r = 0; r < 6; r++)
        {
            for(int c = 0; c < 7; c++)
            {
                if (grid[r][c] == ' ')
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWinner(char letter)
    {
        for(int r = 0; r < 6; r++)
        {
            for(int c = 0; c <= 3; c++)
            {
                if (grid[r][c] == letter && grid[r][c + 1] == letter && grid[r][c + 2] == letter && grid[r][c + 3] == letter)
                {
                    return true;
                }
            }
        }

        for(int r = 0; r < 3; r++)
        {
            for(int c = 0; c <= 3; c++)
            {
                if (grid[r][c] == letter && grid[r + 1][c] == letter && grid[r + 2][c] == letter && grid[r + 3][c] == letter)
                {
                    return true;
                }
            }
        }

        for(int r = 0; r < 3; r++)
        {
            for(int c = 0; c <= 3; c++)
            {
                if (grid[r][c] == letter && grid[r + 1][c + 1] == letter && grid[r + 2][c + 2] == letter && grid[r + 3][c + 3] == letter)
                {
                    return true;
                }
            }
        }

        for(int r = 0; r < 3; r++)
        {
            for(int c = 3; c <= 6; c++)
            {
                if (grid[r + 3][c - 3] == letter && grid[r + 2][c - 2] == letter && grid[r + 1][c - 1] == letter && grid[r][c] == letter)
                {
                    return true;
                }
            }
        }

        for(int r = 3; r < 6; r++)
        {
            for(int c = 3; c <= 6; c++)
            {
                if (grid[r - 3][c - 3] == letter && grid[r - 2][c - 2] == letter && grid[r - 1][c - 1] == letter && grid[r][c] == letter)
                {
                    return true;
                }
            }
        }

        return false;
    }

}