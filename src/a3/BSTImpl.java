package a3;

public class BSTImpl implements BST {

    private Node root;
    private int size;

    public BSTImpl() {
        root = null;
        size = 0;
    }

    public BSTImpl(String s) {
        root = new NodeImpl(s);
        size = 0;
    }

    // The implementation of "height" is given to you below
    // it is here to illustrate for you how to set up
    // the method implementation as recursion.
    // You should follow this pattern for implementing the other recursive methods
    // by adding your own private recursive methods

    @Override
    public int height() { // public interface method signature
        // this method is the public interface method
        // it is not recursive, but it calls a recursive
        // private method and passes it access to the tree cells
        return height_recursive(this.root);
    }
    private int height_recursive(Node c) {
        // inner method with different signature
        // this helper method uses recursion to traverse
        // and process the recursive structure of the tree of cells
        if (c==null) return -1;
        int lht = height_recursive(c.getLeft());
        int rht = height_recursive(c.getRight());
        return Math.max(lht,rht) + 1;
    }

    @Override
    public Node getRoot() {
        return this.root;
    }

    @Override
    public String insert(String value) {
        if (this.root == null) { //if the BST is completely empty, create a root to start
            this.root = new NodeImpl(value);
        }
        else { //if it is not empty, we'll compare values
            recurseInsert(value, this.root); //first recursive step beginning at head
        }
        size++;
        return value; //returns value string when all recursion is finished
    }
    private Node recurseInsert(String value, Node node) { //the recursive helper function for insert
        int lexiCounter = value.compareTo(node.getValue()); //finds lexographic difference
        if (lexiCounter < 0) { //if lexi value is less than root
            if (node.getLeft() == null){
                node.setLeft(new NodeImpl(value));
            }
            else {
                recurseInsert(value, node.getLeft());
            }
        }
        if (lexiCounter > 0) { //if lexi value is more than root
            if (node.getRight() == null){
                node.setRight(new NodeImpl(value));
            }
            else {
                recurseInsert(value, node.getRight());
            }
        }
        return null;
    }

    // remove implementation given to you, do NOT change
    @Override
    public void remove(String value) {
        remove_r(value,this.root);
        size--;
    }
    private Node remove_r(String k, Node c) {
        if (c==null) return null; // item not found, nothing to do

        // now we know we have a real node to examine
        int cflag = k.compareTo(c.getValue());

        if (cflag<0) { // k is smaller than node
            c.setLeft(remove_r(k,c.getLeft()));
            return c;
        }
        else
        if (cflag>0) { // k is larger than node
            c.setRight(remove_r(k,c.getRight()));
            return c;
        }
        else //cflag==0
        { // found it... now figure out how to rearrange

            // cases
            if (c.getLeft()==null && c.getRight()==null) { c = null; } // leaf
            else if (c.getLeft()==null && c.getRight()!=null) { c = c.getRight(); } // RC only
            else if (c.getLeft()!=null && c.getRight()==null) { c = c.getLeft(); } // LC only
            else { // 2 children
                Node max = maxCell(c.getLeft());
                c.setValue(max.getValue());
                c.setLeft(remove_r(c.getValue(), c.getLeft()));   // recurse
            }
            return c;
        }

    }

    private Node maxCell(Node c) { // this is used in remove too
        if (c.getRight()==null) return c;
        return maxCell(c.getRight());
    }

    @Override
    public boolean isFull() {
        if (this.root == null) {
            return true; //Should an empty tree be T or F?
        }
        if (this.size == 0) {
            return true; //A tree with only a root
        }
        return recurseIsFull(this.root.getLeft(), this.root.getRight());
    }

    private boolean recurseIsFull(Node nodeLeft, Node nodeRight) {
        if (nodeStatus(nodeLeft) == "missing Child" || nodeStatus(nodeRight) == "missing Child") {
            return false;
        }
        if (nodeStatus(nodeLeft) == "0 Child" && nodeStatus(nodeRight) == "0 Child") {
            return true;
        }
        if (nodeStatus(nodeLeft) == "2 Child" && nodeStatus(nodeRight) == "0 Child") {
            return recurseIsFull(nodeLeft.getLeft(), nodeLeft.getRight());
        }
        if (nodeStatus(nodeLeft) == "0 Child" && nodeStatus(nodeRight) == "2 Child") {
            return recurseIsFull(nodeRight.getLeft(), nodeRight.getRight());
        }
        if (nodeStatus(nodeLeft) == "2 Child" && nodeStatus(nodeRight) == "2 Child") {
            if (recurseIsFull(nodeLeft.getLeft(), nodeLeft.getRight()) == true) {
                return recurseIsFull(nodeRight.getLeft(), nodeRight.getRight());
            }
            else {
                return false;
            }
        }
        return false;
    }

    private boolean hasLeft(Node node) {
        if (node.getLeft() == null) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean hasRight(Node node) {
        if (node.getRight() == null) {
            return false;
        }
        else {
            return true;
        }
    }

    private String nodeStatus(Node node) {
        //There is a child missing either right or left--not full
        if (hasRight(node) == false && hasLeft(node) == true) {
            return "missing Child";
        }
        if (hasRight(node) == true && hasLeft(node) == false) {
            return "missing Child";
        }
        //There are no children--considered full
        if (hasRight(node) == false && hasLeft(node) == false) {
            return "0 Child";
        }
        //There are both right and left node--considered full
        if (hasRight(node) == true && hasLeft(node) == true) {
            return "2 Child";
        }
        return null;
    }


    @Override
    public String findMin() {
        if (this.root == null) {
            return null;
        }
        if (this.size == 0) {
            return this.root.getValue();
        }
        return recurseMin(this.root);
    }

    private String recurseMin(Node node) {
        if (node.getLeft() == null){
            return node.getValue();
        }
        else {
            return recurseMin(node.getLeft());
        }
    }

    @Override
    public String findMax() {
        if (this.root == null) {
            return null;
        }
        if (this.size == 0) {
            return this.root.getValue();
        }
        return recurseMax(this.root);
    }

    private String recurseMax(Node node) {
        if (node.getRight() == null){
            return node.getValue();
        }
        else {
            return recurseMax(node.getRight());
        }
    }

    @Override
    public boolean contains(String s) {
        return false;
    }

    @Override
    public Node get(String s) {
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void show() {
        int off=4;
        int lev=0;
        for (int k=0; k<10; k++) {
            System.out.print("+");
            for (int kk=0; kk<off-1; kk++) {System.out.print("-");}
        }
        System.out.println("+");
        show_r(this.root,lev,off);
        for (int k=0; k<10; k++){
            System.out.print("+");
            for (int kk=0; kk<off-1; kk++) {System.out.print("-");}
        }
        System.out.println("+");
    }
    private void show_r(Node n, int lev, int off) {
        if (n==null) return;
        show_r(n.getRight(), lev+off, off);
        for (int b=0; b<lev; b++) {System.out.print(" ");}
        System.out.println(n.getValue());
        show_r(n.getLeft(),lev+off, off);
    }


}

