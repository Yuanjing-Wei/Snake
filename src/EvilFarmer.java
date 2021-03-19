import java.awt.*;
import java.util.Random;

/*
  Create a class called EvilFarmer, which inherits Egg, and has the following
  additional private instance variables and public methods.

  Instance variables:
  - An Image type variable farmerImage, which is defined as follows:
    private Image farmerIMG = Toolkit.getDefaultToolkit().getImage("img/farmer.jpg");
  - A static constant variable RANDOMGEN of Random type, which references to a Random type object.
  - A static constant variable P_EVIL of double type with value 0.5.
  - A boolean variable called evil which indicates if it is an evil farmer or not.

  Methods:
  - Default constructor of EvilFarmer
  - Another constructor of EvilFarmer that takes two int parameters representing (row, col)
    coordinates of EvilFarmer and use them to initialize the inherited row and col from Egg.
    It also initializes evil according to the returned value of isEvil method.
  - reGenerate method which takes a SnakeFrame object sf and calls reGenerate method of the
    super class and set evil according to the returned value of isEvil method.
    Note: reGenerate method returns nothing.
  - An accessor method, getEvil, which returns the value of evil.
  - isEvil method which randomly generate a number using constant variable R.
    If the random number is greater P_EVIL, return true. Otherwise, return false.
    Note: isEvil does not take any input.
  - draw method, which is defined as follows:
    public void draw(Graphics g) {
        g.drawImage(farmerIMG,
                 col*SnakeFrame.BLOCK_WIDTH+1,
                 row*SnakeFrame.BLOCK_HEIGHT+1,
                 SnakeFrame.BLOCK_WIDTH-1,
                 SnakeFrame.BLOCK_HEIGHT-1,
                 null);
    }
*/

/* TODO: EvilFarmer class */
// The followings are some given code for the EvilFarmer class.

public class EvilFarmer extends Egg {
    private boolean evil;
    // The image used to represent the farmer.
    private Image farmerImage = Toolkit.getDefaultToolkit().getImage("img/farmer.jpg");
    private static final double P_EVIL=0.5;
    private static final Random RANDOMGEN=new Random();
    //default constructor
    public EvilFarmer(){
        super();
    }
    //another constructor
    public EvilFarmer(int a,int b){
        super(a,b);
        evil=isEvil();
    }
    // The method to draw the farmer on screen.
    public void draw(Graphics g) {
        g.drawImage(farmerImage,
                 col*SnakeFrame.BLOCK_WIDTH+1,
                 row*SnakeFrame.BLOCK_HEIGHT+1,
                 SnakeFrame.BLOCK_WIDTH-1,
                 SnakeFrame.BLOCK_HEIGHT-1,
                 null);
    }
    //method an accessor
    public boolean getEvil(){
        return evil;
    }
    //method isEvil
    public boolean isEvil(){
        double R=RANDOMGEN.nextDouble();
        if(R>P_EVIL)
            return true;
        else
            return false;
    }
    //method reGenerate
    public void reGenerate(SnakeFrame sf){
        super.reGenerate(sf);
        evil=isEvil();
    }
}
