import java.util.*;

class Node {
    private Node parent;
    private Node left;
    private Node right;
    public Integer element;
    public Integer height;

    public Node(Node left, Node right, Node parent, Integer element) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = element;
        this.height = 0;
    }

    public void setLeft(Node node) {
        this.left = node;
        if (node != null) node.parent = this;
    }

    public void setRight(Node node) {
        this.right = node;
        if (node != null) node.parent = this;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getParent() {
        return parent;
    }

    public void resetParent() {
        parent = null;
    }
}

public class AVLTree {
    private Node root;
    private int count = 0;

    public AVLTree() {

    }

    public boolean contains(Integer e) {
        return searchNode(e) != null;
    }

    public Integer getParent(Integer element) {
        Node node = searchNode(element);
        if (node == null) return null;
        Node parent = node.getParent();
        if (parent == null) return null;
        return parent.element;
    }

    public void add(Integer element) {
        root = insert(root, element);
        root.resetParent();
        count++;
    }

    public void remove(Integer e) {
        root = removeNode(root, e);
        if (root != null) root.resetParent();
    }

    public int getTreeHeight() {
        return getHeight(root) + 1;
    }

    public boolean isBalanced() {
        return isBalancedNode(root);
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public LinkedList<Integer> positionsPre() {
        LinkedList<Integer> res = new LinkedList<Integer>();
        positionsPreAux(root, res);
        return res;
    }

    private void positionsPreAux(Node n, LinkedList<Integer> res) {
        if (n != null) {
            res.add(n.element); //Visita o nodo
            positionsPreAux(n.getLeft(), res); //Visita a subárvore da esquerda
            positionsPreAux(n.getRight(), res); //Visita a subárvore da direita
        }
    }

    public LinkedList<Integer> positionsPos() {
        LinkedList<Integer> res = new LinkedList<Integer>();
        positionsPosAux(root, res);
        return res;
    }

    private void positionsPosAux(Node n, LinkedList<Integer> res) {
        if (n != null) {
            positionsPosAux(n.getLeft(), res); //Visita a subárvore da esquerda
            positionsPosAux(n.getRight(), res); //Visita a subárvore da direita
            res.add(n.element); //Visita o nodo
        }
    }

    public LinkedList<Integer> positionsCentral() {
        LinkedList<Integer> res = new LinkedList<Integer>();
        positionsCentralAux(root, res);
        return res;
    }

    private void positionsCentralAux(Node n, LinkedList<Integer> res) {
        if (n != null) {
            positionsCentralAux(n.getLeft(), res); //Visita a subárvore da esquerda
            res.add(n.element); //Visita o nodo
            positionsCentralAux(n.getRight(), res); //Visita a subárvore da direita
        }
    }

    public LinkedList<Integer> positionsWidth() {
        Queue<Node> fila = new LinkedList<>();
        Node atual = null;
        LinkedList<Integer> res = new LinkedList<Integer>();
        if (root != null) {
            fila.add(root);
            while (!fila.isEmpty()) {
                atual = fila.remove();
                if (atual.getLeft() != null) {
                    fila.add(atual.getLeft());
                }
                if (atual.getRight() != null) {
                    fila.add(atual.getRight());
                }
                res.add(atual.element);
            }
        }
        return res;
    }

    private boolean isBalancedNode(Node node) {
        int balance = getBalanceFactor(node);
        if (balance < -1 || balance > 1) return false;
        if (node.getLeft() != null) {
            if (!isBalancedNode(node.getLeft())) return false;
        }
        if (node.getRight() != null) {
            if (!isBalancedNode(node.getRight())) return false;
        }
        return true;
    }

    public Node searchNode(Integer e) {
        return searchNode(e, root);
    }

    private Node searchNode(Integer e, Node node) {
        if (node.element.equals(e)) {
            return node;
        } else {
            if (e < node.element) {
                if (node.getLeft() != null) {
                    Node n = searchNode(e, node.getLeft());
                    if (n != null) {
                        return n;
                    }
                }
            } else {
                if (node.getRight() != null) {
                    Node n = searchNode(e, node.getRight());
                    if (n != null) {
                        return n;
                    }
                }
            }
        }
        return null;
    }

    private Node insert(Node node, Integer element) {
        if (node == null) {
            return new Node(null, null, null, element);
        } else if (node.element > element) {
            node.setLeft(insert(node.getLeft(), element));
        } else if (node.element < element) {
            node.setRight(insert(node.getRight(), element));
        } else {
            throw new RuntimeException("Element repetido!");
        }
        return rebalance(node);
    }

    private Node removeNode(Node node, Integer e) {
        if (node == null) {
            return null;
        } else if (e < node.element) {
            node.setLeft(removeNode(node.getLeft(), e));
        } else if (e > node.element) {
            node.setRight(removeNode(node.getRight(), e));
        } else {
            if (node.getLeft() == null) {
                count--;
                return node.getRight();
            } else if (node.getRight() == null) {
                count--;
                return node.getLeft();
            } else {
                Node mostLeftChild = mostLeftChild(node.getRight());
                node.element = mostLeftChild.element;
                node.setRight(removeNode(node.getRight(), node.element));
            }
        }
        return rebalance(node);
    }

    private Node mostLeftChild(Node node) {
        if (node.getLeft() == null) return node;
        return mostLeftChild(node.getLeft());
    }

    private Node rebalance(Node node) {
        updateHeight(node);

        int balance = getBalanceFactor(node);
        if (balance > 1) {
            if (getHeight(node.getRight().getRight()) > getHeight(node.getRight().getLeft())) {
            //if (getBalanceFactor(node.right) > 0) {
                node = leftRotation(node);
            } else {
                node.setRight(rightRotation(node.getRight()));
                node = leftRotation(node);
            }
        } else if (balance < -1) {
            //if (getBalanceFactor(node.left) < 0) {
            if (getHeight(node.getLeft().getLeft()) > getHeight(node.getLeft().getRight())) {
                node = rightRotation(node);
            } else {
                node.setLeft(leftRotation(node.getLeft()));
                node = rightRotation(node);
            }
        }

        return node;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        int heightLeft = getHeight(node.getLeft());
        int heightRight = getHeight(node.getRight());
        return heightRight - heightLeft;
    }

    private void updateHeight(Node node) {
        int heightLeft = getHeight(node.getLeft());
        int heightRight = getHeight(node.getRight());
        if (heightLeft > heightRight) {
            node.height = heightLeft + 1;
        } else {
            node.height = heightRight + 1;
        }
    }

    private Node rightRotation(Node node) {
        Node x = node.getLeft();
        Node z = x.getRight();
        x.setRight(node);
        node.setLeft(z);

        updateHeight(node);
        updateHeight(x);

        return x;
    }

    private Node leftRotation(Node node) {
        Node x = node.getRight();
        Node z = x.getLeft();
        x.setLeft(node);
        node.setRight(z);

        updateHeight(node);
        updateHeight(x);

        return x;
    }

    @Override
    public String toString() {
        BinaryTreePrinter.printNode(root);
        return "";
    }
}

class BinaryTreePrinter {
    public static void printNode(Node root) {
        int maxLevel = BinaryTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BinaryTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BinaryTreePrinter.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.element);
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BinaryTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BinaryTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BinaryTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null)
                    System.out.print("/");
                else
                    BinaryTreePrinter.printWhitespaces(1);

                BinaryTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    BinaryTreePrinter.printWhitespaces(1);

                BinaryTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(BinaryTreePrinter.maxLevel(node.getLeft()), BinaryTreePrinter.maxLevel(node.getRight())) + 1;
    }

    private static boolean isAllElementsNull(List<Node> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}

