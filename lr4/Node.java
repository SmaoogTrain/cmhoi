package lr4;

public class Node {
    int key;
    int balance;
    Node left, right;

    Node(int key) {
        this.key = key;
        this.balance = 0;
    }
}
