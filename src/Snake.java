import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

// Snake - A class to represent the snake in the game
public class Snake {
    // Static variables used to represent the width and height of the frame
    private static final int BLOCK_WIDTH = SnakeFrame.BLOCK_WIDTH;
    private static final int BLOCK_HEIGHT = SnakeFrame.BLOCK_HEIGHT;

    // Static array used to represent the color of the snake
    private static final Color SNAKECOLOR[] = { Color.BLACK, Color.magenta, Color.GREEN };

    // Reference variable to the head node of the snake
    private Node head = null;
    // Reference variable to the tail node of the snake
    private Node tail = null;
    // Reference variable to the frame of the snake game
    private SnakeFrame sf;
    // A boolean variable used to represent whether the snake is alive
    private boolean alive;
    // Initial position of the snake
    private Node node = new Node(3,4,Direction.D);
    // Length of the snake
    private int length = 0;
    // Life of the snake
    private int life = 1;
    //for hunter
    public int buffer=1;

    // Constructor
    public Snake(SnakeFrame sf) {
        head = node;
        tail = node;
        head.pre = null;
        tail.next = null;
        length++;
        this.sf = sf;
        alive = true;
    }

    // The method used to draw the snake on screen
    public void draw(Graphics g) {
        if(head == null)
            return ;
        if(alive) {
            move();
            for (Node node = head; node != null; node = node.next)
                node.draw(g);
        }
    }

    // The method used to move the snake
    public void move() {
        addNodeInHead();
        // Check alive
        if (!checkDead())
            deleteNodeInTail();
    }

    // Accessor of instance variable alive
    public boolean ifAlive() {
        return alive;
    }

    // Accessor of instance variable length
    public int getLength() {
        return length;
    }

    // Accessor of instance variable life
    public int getLife() {
        return life;
    }

    /* TODO: reverse method (OPTIONAL) */ //check
    private void reverse() {
        // When the snake has spare lives and it runs into a barrier,
        // move the snake reversely so that the player is able to keep playing the game.
        /* YOUR CODE HERE */
        switch(head.dir){
            case L:
                head.dir=Direction.R;
                break;
            case U:
                head.dir=Direction.D;
                break;
            case R:
                head.dir=Direction.L;
                break;
            case D:
                head.dir=Direction.U;
                break;
        }
        Node prev=null;
        Node curr=head;
        Node nex=null;
        while(curr!=null){
            nex=curr.next;
            curr.next=prev;
            prev=curr;
            curr=nex;
        }
        head.pre=null;
        tail.next=null;

    }

    /* TODO: checkDead method */ //done
    private boolean checkDead() {
        // Check head with boundary
        if(head.row < 4 || head.row > SnakeFrame.ROW-1 || head.col < 0 || head.col > SnakeFrame.COL-1) {
            life--;
            if(life < 0) {
                this.alive=false;
                return true;
            }
            else {
                reverse();
                return false;
            }
        }

        // Check whether the snake bites itself
        for(Node node = head.next; node != null; node = node.next) {
            if(head.row == node.row && head.col == node.col) {
                life--;
                if(life < 0) {
                    this.alive = false;
                    return true;
                }
                else {
                    reverse();
                    return false;
                }
            }
        }

        // Check whether the snake hits the wall or not.
        // If so,
        //   Decreases the life by one, and checks if life is less than 0 or not.
        //   If life is less than 0,
        //     Sets alive to false and
        //     Returns true.
        //   Otherwise,
        //     Calls reverse method and
        //     Returns false.

        /* YOUR CODE HERE */ //check
        for(int i=0;i<sf.wall.size();i++){
            if(getRect().intersects(sf.wall.get(i).getRect())){
                life--;
                if(life< 0){
                    this.alive=false;
                    return true;
                }
                else{
                    reverse();
                    return false;
                }
            }
        }

        if(getRect().intersects(sf.hunter.getRect())){
            life--;
            if(life< 0){
                this.alive=false;
                return true;
            }
            else{
                reverse();
                return false;
            }
        }


        return false;
    }

    // Remove the last node
    private void deleteNodeInTail() {
        Node node = tail.pre;
        tail = null;
        node.next = null;
        tail = node;
    }

    // Add a new node to the head
    private void addNodeInHead() {
        Node node = null;
        switch(head.dir){
            case L:
                node = new Node(head.row,head.col-1, head.dir);
                break;
            case U:
                node = new Node(head.row-1, head.col, head.dir);
                break;
            case R:
                node = new Node(head.row,head.col+1, head.dir);
                break;
            case D:
                node = new Node(head.row+1, head.col, head.dir);
                break;
        }

        node.next = head;
        head.pre = node;
        head = node;
        head.pre = null;
    }

    // A method to handle keypressed events
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_LEFT :
                if(head.dir != Direction.R)
                   head.dir = Direction.L;
                break;
            case KeyEvent.VK_UP :
                if(head.dir != Direction.D)
                   head.dir = Direction.U;
                break;
            case KeyEvent.VK_RIGHT :
                if(head.dir != Direction.L)
                   head.dir = Direction.R;
                break;
            case KeyEvent.VK_DOWN :
                if(head.dir != Direction.U)
                   head.dir = Direction.D;
                break;
        }
    }

    // This is for collision detection
    public Rectangle getRect() {
        return new Rectangle(head.col*BLOCK_WIDTH,
                             head.row*BLOCK_HEIGHT,
                              BLOCK_WIDTH,
                              BLOCK_HEIGHT);
    }

    /* TODO: eatEgg method */
    public boolean eatEgg(EvilFarmer farmer) {
        // Check if the snake meets a farmer.
        // If so,
        //   Shows the farmer, and checks if the farmer is an evil farmer.
        //   If the farmer is an evil farmer,
        //     Decreases the length of the snake by half and
        //     returns true.
        //     Hint: the method deleteNodeInTail() may be useful for decreasing the length of snake.
        //   If the farmer is a kind farmer,
        //     Increase the life of snake by 1, and
        //     returns true.
        // If the snake does not meet a farmer,
        //   returns false.

        /* YOUR CODE HERE */ //check decrease length
        if(getRect().intersects(farmer.getRect())) {
            if(farmer.getEvil()) {
                int lengthnow=length;
                for(int i=0;i<lengthnow/2;i++){
                    deleteNodeInTail();
                    length--;
                }
            }
            else {
                life++;
            }
            farmer.reGenerate(sf);
            return true;
        }
        else
            return false;
    }

    // Method to check whether the snake eats an egg. If so, update the states of snake and egg
    public boolean eatEgg(Egg egg) {
        if(getRect().intersects(egg.getRect())) {
            addNodeInHead();
            egg.reGenerate(sf);
            length++;
            return true;
        }
        else
            return false;
    }

    // Method to make the hunter move
    public void eatEgg(Hunter hunter) {
        buffer++;
        hunter.reGenerate(sf,buffer);
    }


    // Inner class for Node
    public class Node {
        // The coordinates of node.
        private int row;
        private int col;

        // Direction
        private Direction dir;

        // Previous and next Node references
        private Node pre;
        private Node next;

        // Constructor
        public Node(int row, int col, Direction dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        // The method used to draw the node on screen
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(SNAKECOLOR[sf.getLevel()]);
            g.fillRect(col*BLOCK_WIDTH, row*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
            g.setColor(c);
        }
    }
    public void destroyer(){
        deleteNodeInTail();
        deleteNodeInTail();
        length-=2;
    }
}
