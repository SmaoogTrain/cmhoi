package lr3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class Task33 {
    public static void tasking33(){
        TreeNode root = null;
        for (int num : readNumbersFromFile("C:\\programming\\java\\BTrees\\numbertree.txt")) {
            root = insert(root, num);
        }

        TreeNode[] rootRef = { root };
        TreePanel treePanel = new TreePanel(rootRef[0]);

        JFrame frame = new JFrame("Бинарное дерево с алгоритмами T(x) и D(x)");
        JTextField inputField = new JTextField(10);
        JButton insertButton = new JButton("Вставить (T)");
        JButton deleteButton = new JButton("Удалить (D)");

        insertButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(inputField.getText());
                rootRef[0] = insert(rootRef[0], x);
                treePanel.setRoot(rootRef[0]);
            } catch (NumberFormatException ex) {
                log("Неверный ввод");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int x = Integer.parseInt(inputField.getText());
                rootRef[0] = delete(rootRef[0], x);
                treePanel.setRoot(rootRef[0]);
            } catch (NumberFormatException ex) {
                log("Неверный ввод");
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Число:"));
        controlPanel.add(inputField);
        controlPanel.add(insertButton);
        controlPanel.add(deleteButton);

        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        frame.setLayout(new BorderLayout());
        frame.add(treePanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static class TreeNode {
        int value;
        TreeNode left, right;
        TreeNode(int val) { this.value = val; }
    }

    static TreeNode insert(TreeNode root, int x) {
        if (root == null) {
            log("Вставлен узел: " + x);
            return new TreeNode(x);
        }
        if (x < root.value) root.left = insert(root.left, x);
        else if (x > root.value) root.right = insert(root.right, x);
        else log("Узел уже существует: " + x);
        return root;
    }

    static TreeNode delete(TreeNode root, int x) {
        if (root == null) {
            log("Узел не найден: " + x);
            return null;
        }
        if (x < root.value) root.left = delete(root.left, x);
        else if (x > root.value) root.right = delete(root.right, x);
        else {
            if (root.left == null && root.right == null) {
                log("Удалён лист: " + x);
                return null;
            } else if (root.left == null) {
                log("Удалён узел с правым потомком: " + x);
                return root.right;
            } else if (root.right == null) {
                log("Удалён узел с левым потомком: " + x);
                return root.left;
            } else {
                TreeNode min = findMin(root.right);
                log("Заменён " + root.value + " на " + min.value);
                root.value = min.value;
                root.right = delete(root.right, min.value);
            }
        }
        return root;
    }

    static TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextInt()) numbers.add(scanner.nextInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    static JTextArea logArea = new JTextArea(5, 40);
    static void log(String message) {
        System.out.println(message);
        logArea.append(message + "\n");
    }

    static class TreePanel extends JPanel {
        TreeNode root;
        TreePanel(TreeNode root) {
            this.root = root;
            setBackground(Color.WHITE);
        }
        void setRoot(TreeNode root) {
            this.root = root;
            repaint();
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (root != null) drawTree(g, root, getWidth() / 2, 30, getWidth() / 4);
        }
        void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
            g.setColor(Color.BLACK);
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(node.value), x - 7, y + 5);

            if (node.left != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x - 5, y + 15, x - xOffset + 5, y + 60 - 15);
                drawTree(g, node.left, x - xOffset, y + 60, xOffset / 2);
            }
            if (node.right != null) {
                g.setColor(Color.BLACK);
                g.drawLine(x + 5, y + 15, x + xOffset - 5, y + 60 - 15);
                drawTree(g, node.right, x + xOffset, y + 60, xOffset / 2);
            }
        }
    }
}