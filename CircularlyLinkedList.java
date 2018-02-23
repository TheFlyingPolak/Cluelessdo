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

}
