import java.util.Random;

/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * Class used to create a circularly linked list of human players
 * playing the game
 */
public class CircularlyLinkedList {

    private static class Node{
        private Player player;  //element of the position in the linked list
        private Node next;      //reference to next node in the list

        //creates a given element before a certain node
        public Node(Player p, Node n){
            player = p;
            next = n;
        }

        //accessor methods
        public Player getPlayer() {
            return player;
        }

        public Node getNext() {
            return next;
        }

        //mutator methods
        public void setPlayer(Player p) {
            player = p;
        }

        public void setNext(Node n) {
            next = n;
        }

    }

    //private Node head = null;
    private Node tail = null;
    private int size = 0;

    //constructs a circularly linked list
    public CircularlyLinkedList(){

    }

    //function returns the size of the circularly linked list
    public int getSize() {
        return size;
    }

    //function returns true if the circularly linked list is empty
    public boolean isEmpty(){
        return size == 0;
    }

    //returns the first player in the circularly linked list
    public Player first(){
        if (isEmpty()){
            return null;
        }
        return tail.getNext().getPlayer();
    }

    //returns the last player in the circularly linked list
    public Player last(){
        if(isEmpty()){
            return null;
        }
        return tail.getPlayer();
    }

    //add a player to the start of the list
    public void addFirst(Player p){
        if(isEmpty()){
            tail = new Node(p,null);
            tail.setNext(tail);
        }
        else{
            Node n = new Node(p,tail.getNext());
            tail.setNext(n);
        }
        size++;
    }

    //add player to tail of list
    public void addLast(Player p){
        addFirst(p);
        tail = tail.getNext();
    }
    
    //Circle through the players in the list
    public void playerTurns(UI ui,TokenController tokenPanel) {
        //A node to walk through each player
        Node walk = tail.getNext();
        
        //do while loop loops through list of players while all the players are still playing
        do {
            ui.getInfo().addText(walk.getPlayer().getPlayerName() + " it's your turn! Type roll, to roll the dice");
            String command = ui.getCmd().getCommand().toLowerCase();
            
            if(command.contentEquals("roll")){
                //generate random number between 1-6
                Random rn = new Random();
                int roll = rn.nextInt(6) + 1;
                ui.getInfo().addText("You rolled a " + roll);
                ui.getInfo().addText("You can move " + roll + " places on the board\nEnter up,down,left or right to control your token");
                
                //loop allows the player to move the number of times there dice roll has given them
                while(roll>0){
                    
                    String direction = ui.getCmd().getCommand().toLowerCase();
                    
                    if(direction.contentEquals("up")){
                        walk.getPlayer().getPlayerToken().moveToken(Direction.UP,ui.getBoard());
                        ui.getInfo().addText("up");
                    }
                    else if(direction.contentEquals("down")){
                        walk.getPlayer().getPlayerToken().moveToken(Direction.DOWN,ui.getBoard());
                        ui.getInfo().addText("down");
                    }
                    else if(direction.contentEquals("left")){
                        walk.getPlayer().getPlayerToken().moveToken(Direction.LEFT,ui.getBoard());
                        ui.getInfo().addText("left");
                    }
                    else if(direction.contentEquals("right")){
                        walk.getPlayer().getPlayerToken().moveToken(Direction.RIGHT,ui.getBoard());
                        ui.getInfo().addText("right");
                    }
                    else{
                        ui.getInfo().addText("invalid");
                    }
                    tokenPanel.repaint();
                    roll--;
                }
            }
            walk = walk.getNext();
            
        } while (!isEmpty());
    }

}
