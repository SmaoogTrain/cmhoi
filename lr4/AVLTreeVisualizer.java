package lr4;

import javax.swing.*;
import java.awt.*;

public class AVLTreeVisualizer extends JPanel {
    private Node root;

    public void insert(int key) {
        root = insertAVL(root, key);
    }

    private Node insertAVL(Node node, int key) {
        if (node == null) return new Node(key);

        if (key == node.key) return node; // ignore duplicates

        if (key < node.key) {
            node.left = insertAVL(node.left, key);
        } else {
            node.right = insertAVL(node.right, key);
        }

        updateBalance(node);
        return rebalance(node);
    }

    private void updateBalance(Node node) {
        node.balance = height(node.right) - height(node.left);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private Node rebalance(Node node) {
        if (node.balance == -2) {
            if (node.left.balance <= 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        } else if (node.balance == 2) {
            if (node.right.balance >= 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateBalance(y);
        updateBalance(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateBalance(x);
        updateBalance(y);
        return y;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, root, getWidth() / 2, 30, getWidth() / 4);
    }

    private void drawTree(Graphics g, Node node, int x, int y, int offset) {
        if (node == null) return;
        g.setColor(Color.BLACK);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(node.key), x - 6, y + 4);
        g.setColor(Color.BLACK);
        if (node.left != null) {
            g.drawLine(x, y, x - offset, y + 50);
            drawTree(g, node.left, x - offset, y + 50, offset / 2);
        }
        if (node.right != null) {
            g.drawLine(x, y, x + offset, y + 50);
            drawTree(g, node.right, x + offset, y + 50, offset / 2);
        }
    }
}
