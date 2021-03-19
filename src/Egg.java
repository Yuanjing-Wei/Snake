import java.awt.*;
import java.util.Random;

// Egg - A class to represent an egg in the game
public class Egg {
    // The coordinates of the egg
    protected int row;
    protected int col;

    // The random generator, which will be used to randomly generate the coordinates of egg
    private static final Random RANDOMGEN = new Random();

    private Color color = Color.RED;

    // Constructor that takes two parameters used to initialize instance variables row and col
    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Default constructor
    public Egg() {
        this(RANDOMGEN.nextInt(SnakeFrame.ROW - 4) + 4,
             RANDOMGEN.nextInt(SnakeFrame.COL));
    }

    // TODO: reGenerate method
    public void reGenerate(SnakeFrame sf) {
        // Randomly generate the coordinates of egg such that the egg is
        // not coincided with any wall.

        // The following statements demonstrate how to randomly generate the
        // coordinates of egg.
        // row = (RANDOMGEN.nextInt(SnakeFrame.ROW-4))+4;
        // col = (RANDOMGEN.nextInt(SnakeFrame.COL));

        /* YOUR CODE HERE */
        boolean coincide=false;
        do {
            row = (RANDOMGEN.nextInt(SnakeFrame.ROW - 4)) + 4;
            col = (RANDOMGEN.nextInt(SnakeFrame.COL));
            for(int i=0;i<sf.wall.size();i++) {
                if (getRect().intersects(sf.wall.get(i).getRect()))
                    coincide=true;
            }
        }while(coincide);
    }

    // The method used to draw the Egg on screen.
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(color);
        g.fillOval(col*SnakeFrame.BLOCK_WIDTH,
                   row*SnakeFrame.BLOCK_HEIGHT,
                    SnakeFrame.BLOCK_WIDTH,
                    SnakeFrame.BLOCK_HEIGHT);
        g.setColor(c);
        if(color == Color.RED)
            color = Color.BLUE;
        else
            color = Color.RED;
    }

    // Accessor of instance variable row
    public int getRow() {
        return row;
    }

    // Accessor of instance variable col
    public int getCol() {
        return col;
    }

    // This is for collision detection
    public Rectangle getRect() {
        return new Rectangle(col*SnakeFrame.BLOCK_WIDTH,
                             row*SnakeFrame.BLOCK_HEIGHT,
                              SnakeFrame.BLOCK_WIDTH,
                              SnakeFrame.BLOCK_HEIGHT);
    }
}
