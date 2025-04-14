package lr3;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task32 {
    public static void tasking32(){
        List<Integer> numbers = readNumbersFromFile("C:\\programming\\java\\BTrees\\numbertree.txt");
        TreeNode root = null;
        for (int num : numbers) {
            root = insertIntoBST(root, num);
        }

        TreeNode finalRoot = root;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Binary Tree Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TreePanel(finalRoot));
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }

    // Чтение чисел из файла
    static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    // Построение сбалансированного BST из отсортированного списка
    static TreeNode buildBalancedBST(List<Integer> nums, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(nums.get(mid));
        node.left = buildBalancedBST(nums, start, mid - 1);
        node.right = buildBalancedBST(nums, mid + 1, end);
        return node;
    }

    // Класс узла дерева
    static class TreeNode {
        int value;
        TreeNode left, right;

        TreeNode(int val) {
            value = val;
        }
    }

    // Панель для отрисовки дерева
    static class TreePanel extends JPanel {
        TreeNode root;
        int nodeRadius = 20;
        int verticalGap = 50;

        TreePanel(TreeNode root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, root, getWidth() / 2, 40, getWidth() / 4);
        }

        void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
            if (node == null) return;

            g.setColor(Color.BLACK);
            g.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(node.value), x - 6, y + 4);

            if (node.left != null) {
                int childX = x - xOffset;
                int childY = y + verticalGap;
                g.setColor(Color.BLACK);
                g.drawLine(x, y, childX, childY);
                drawTree(g, node.left, childX, childY, xOffset / 2);
            }

            if (node.right != null) {
                int childX = x + xOffset;
                int childY = y + verticalGap;
                g.setColor(Color.BLACK);
                g.drawLine(x, y, childX, childY);
                drawTree(g, node.right, childX, childY, xOffset / 2);
            }
        }
    }
    static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.value) root.left = insertIntoBST(root.left, val);
        else root.right = insertIntoBST(root.right, val);
        return root;
    }
}