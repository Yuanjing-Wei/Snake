import java.awt.*;
import java.util.Random;

public class Hunter extends Egg {
    // The random generator, which will be used to randomly generate the coordinates of hunter
    private static final Random RANDOMGEN = new Random();
    // The image used to represent the hunter.
    private Image hunterImage = Toolkit.getDefaultToolkit().getImage("img/hunter.jpg");
    // Direction
    private int dire;
    // Reference variable to the frame of the snake game
    private SnakeFrame sf;
    // Default constructor
    public Hunter(SnakeFrame sf){
        super();
        row=RANDOMGEN.nextInt(SnakeFrame.ROW - 4) + 4;
        col=RANDOMGEN.nextInt(SnakeFrame.COL);
        dire=RANDOMGEN.nextInt(4);
        this.sf = sf;
    }

    // Accessor
    public int getRow(){ return row; }
    public int getCol(){ return col; }


    // The method to draw the hunter on screen.
    public void draw(Graphics g) {
        g.drawImage(hunterImage,
                getCol()*SnakeFrame.BLOCK_WIDTH+1,
                getRow()*SnakeFrame.BLOCK_HEIGHT+1,
                SnakeFrame.BLOCK_WIDTH-1,
                SnakeFrame.BLOCK_HEIGHT-1,
                null);
    }


    public void move(int dire){
        switch (dire){
            case 0:
                row--;
                break;
            case 1:
                col++;
                break;
            case 2:
                col--;
                break;
            case 3:
                row++;
                break;
        }
    }


    // The method used to move the hunter
    public void reGenerate(SnakeFrame sf,int n) {
        if(n%8==0) {
            dire = RANDOMGEN.nextInt(4);
        }
        if(n%2==0)
            move(dire);
        boolean coincide = false;
        for (int i = 0; i < sf.wall.size(); i++) {
            if (getRect().intersects(sf.wall.get(i).getRect()))
                coincide = true;
        }
        if(row < 4 || row > SnakeFrame.ROW-1 || col < 0 || col > SnakeFrame.COL-1||coincide==true){
            dire=3-dire;
            move(dire);
        }
    }


}
