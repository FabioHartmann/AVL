import java.util.*;

class Node {
    public Node parent;
    public Node left;
    public Node right;
    public Integer element;
    public Integer height;

    public Node(Node left, Node right, Node parent, Integer element) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = element;
        this.height = 0;
    }
}

public class AVLTree {
    private Node root;

    public AVLTree() {

    }

    public boolean contains(Integer e) {
        return searchNode(e) != null;
    }

    public Node searchNode(Integer e) {
        return searchNode(e, root);
    }

    private Node searchNode(Integer e, Node node) {
        if (node.element.equals(e)) {
            return node;
        } else {
            if (e < node.element) {
                if (node.left != null) {
                    Node n = searchNode(e, node.left);
                    if (n != null) {
                        return n;
                    }
                }
            } else {
                if (node.right != null) {
                    Node n = searchNode(e, node.right);
                    if (n != null) {
                        return n;
                    }
                }
            }
        }
        return null;
    }

    public void insert(Integer element) {
        root = insert(root, element);
    }

    private Node insert(Node node, Integer element) {
        if (node == null) {
            return new Node(null, null, null, element);
        } else if (node.element > element) {
            Node n = insert(node.left, element);
            n.parent = node;
            node.left = n;
        } else if (node.element < element) {
            Node n = insert(node.right, element);
            n.parent = node;
            node.right = n;
        } else {
            throw new RuntimeException("Element repetido!");
        }
        return rebalance(node);=
    }

    public void remove(Integer e) {
        //TODO
    }

    private Node rebalance(Node node) {
        updateHeight(node);

        int balance = getBalanceFactor(node);
        if (balance > 1) {
            if (getBalanceFactor(node.right) > 0) {
                node = leftRotation(node);
            } else {
                node.right = rightRotation(node.right);
                node = leftRotation(node);
            }
        } else if (balance < -1) {
            if (getBalanceFactor(node.left) > 0) {
                node = rightRotation(node);
            } else {
                node.left = leftRotation(node.left);
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
        int heightLeft = getHeight(node.left);
        int heightRight = getHeight(node.right);
        return heightRight - heightLeft;
    }

    private void updateHeight(Node node) {
        int heightLeft = getHeight(node.left);
        int heightRight = getHeight(node.right);
        if (heightLeft > heightRight) {
            node.height = heightLeft + 1;
        } else {
            node.height = heightRight + 1;
        }
    }

    private Node rightRotation(Node node) {
        Node x = node.left;
        Node z = x.right;
        x.right = node;
        node.left = z;

        updateHeight(node);
        updateHeight(x);

        return x;
    }

    private Node leftRotation(Node node) {
        Node x = node.right;
        Node z = x.left;
        x.left = node;
        node.right = z;

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
                newNodes.add(node.left);
                newNodes.add(node.right);
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

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BinaryTreePrinter.printWhitespaces(1);

                BinaryTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
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

        return Math.max(BinaryTreePrinter.maxLevel(node.left), BinaryTreePrinter.maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List<Node> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}

