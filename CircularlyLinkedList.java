import java.util.Random;
import java.util.Iterator;

/*
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 *
 * Implementation of a circular linked list used to store human players in the game
 */
public class CircularlyLinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    //constructs a circularly linked list
    public CircularlyLinkedList(){
        head = null;
        tail = null;
        size = 0;
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
    public E getFirst(){
        if (isEmpty()){
            return null;
        }
        return head.getElement();
    }

    //returns the last player in the circularly linked list
    public E getLast(){
        if (isEmpty())
            return null;
        return tail.getElement();
    }

    //add a player to the start of the list
    public void addFirst(Player p){
        Node n = new Node(p, null);
        n.setNext(head);
        if (head == null){
            head = n;
            n.setNext(head);
            tail = head;
        }
        else{
            tail.setNext(n);
            head = n;
        }
        size++;
    }

    //add player to tail of list
    public void addLast(Player p){
        Node n = new Node(p, null);
        n.setNext(head);
        if (head == null){
            head = n;
            n.setNext(head);
            tail = head;
        }
        else{
            tail.setNext(n);
            tail = n;
        }
        size++;
    }

    @Override
    public Iterator<E> iterator(){
        return new ListIterator();
    }

    private static class Node<E>{
        private E element;  //element of the position in the linked list
        private Node next;      //reference to next node in the list

        //creates a given element before a certain node
        public Node(E e, Node n){
            element = e;
            next = n;
        }

        public E getElement(){
            return element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node n) {
            next = n;
        }
    }

    private class ListIterator implements Iterator<E>{
        Node<E> current;
        int nextNode;

        public ListIterator(){
            current = head;
            nextNode = 0;
        }

        public boolean hasNext(){
            return nextNode < size;
        }

        public E next(){
            E res = current.getElement();
            current = current.getNext();
            nextNode++;
            return res;
        }
    }
    
    //Circle through the players in the list
    /*public void playerTurns(UI ui,TokenController tokenPanel) {
        //A node to walk through each player
        Node walk = tail.getNext();
        
        //do while loop loops through list of players while all the players are still playing
        do {
            ui.getInfo().addText(walk.getElement().getPlayerName() + " it's your turn! Type roll, to roll the dice");
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
    }*/

}
