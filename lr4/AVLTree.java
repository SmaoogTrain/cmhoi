package lr4;

public class AVLTree {
    static class Node {
        int key;
        Node left, right;
        int balanceFactor;

        Node(int key) {
            this.key = key;
            this.left = this.right = null;
            this.balanceFactor = 0;
        }
    }

    static class HeadNode {
        Node root;
        int height;

        HeadNode() {
            root = null;
            height = 0;
        }
    }

    HeadNode HEAD = new HeadNode();

    public void insert(int key) {
        if (HEAD.root == null) {
            HEAD.root = new Node(key);
            HEAD.height = 1;
            return;
        }

        Node T = null, S = HEAD.root, P = HEAD.root;
        Node parent = null;

        while (true) {
            if (key == P.key) {
                System.out.println("Ключ уже существует: " + key);
                return;
            }

            if (P.balanceFactor != 0) {
                T = parent;
                S = P;
            }

            parent = P;

            if (key < P.key) {
                if (P.left == null) {
                    P.left = new Node(key);
                    break;
                }
                P = P.left;
            } else {
                if (P.right == null) {
                    P.right = new Node(key);
                    break;
                }
                P = P.right;
            }
        }

        // Теперь балансируем
        int a = (key < S.key) ? +1 : -1;
        Node R = (a == +1) ? S.left : S.right;
        P = R;

        // Обновим баланс-факторы от S до Q (новый узел)
        while (P.key != key) {
            if (key < P.key) {
                P.balanceFactor = +1;
                P = P.left;
            } else {
                P.balanceFactor = -1;
                P = P.right;
            }
        }

        // Корректировка в зависимости от изначального баланса
        if (S.balanceFactor == 0) {
            S.balanceFactor = a;
            HEAD.height++;
            return;
        }

        if (S.balanceFactor == -a) {
            S.balanceFactor = 0;
            return;
        }

        // Баланс нарушен
        if (((a == +1) && (R.balanceFactor == +1)) || ((a == -1) && (R.balanceFactor == -1))) {
            // Однократный поворот
            Node PNew = R;
            if (a == +1) {
                S.left = PNew.right;
                PNew.right = S;
            } else {
                S.right = PNew.left;
                PNew.left = S;
            }
            S.balanceFactor = 0;
            PNew.balanceFactor = 0;

            if (S == HEAD.root) {
                HEAD.root = PNew;
            } else {
                if (T.left == S) T.left = PNew;
                else T.right = PNew;
            }
        } else {
            // Двойной поворот
            Node PNew = (a == +1) ? R.right : R.left;

            if (a == +1) {
                R.right = PNew.left;
                S.left = PNew.right;
                PNew.left = R;
                PNew.right = S;
            } else {
                R.left = PNew.right;
                S.right = PNew.left;
                PNew.right = R;
                PNew.left = S;
            }

            switch (PNew.balanceFactor) {
                case 0:
                    R.balanceFactor = 0;
                    S.balanceFactor = 0;
                    break;
                case 1:
                    R.balanceFactor = 0;
                    S.balanceFactor = -a;
                    break;
                case -1:
                    R.balanceFactor = a;
                    S.balanceFactor = 0;
                    break;
            }
            PNew.balanceFactor = 0;

            if (S == HEAD.root) {
                HEAD.root = PNew;
            } else {
                if (T.left == S) T.left = PNew;
                else T.right = PNew;
            }
        }
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println("KEY: " + node.key + " B: " + node.balanceFactor);
            inOrder(node.right);
        }
    }

    public void printTree() {
        System.out.println("In-order Traversal of Tree:");
        inOrder(HEAD.root);
    }
}