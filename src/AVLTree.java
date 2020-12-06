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
        if (node != null) {
            Node parentOfNode = node.parent;
            node.parent = this;
            if (node == parent) parent = parentOfNode;
        }
    }

    public void setRight(Node node) {
        this.right = node;
        if (node != null) {
            Node parentOfNode = node.parent;
            node.parent = this;
            if (node == parent) parent = parentOfNode;
        }
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
        count++;
    }

    public void remove(Integer e) {
        root = removeAux(root, e);
    }

    public Integer getTreeHeight() {
        return getHeight(root) + 1;
    }

    public Boolean isBalanced() {
        return isBalancedNode(root);
    }

    public Integer size() {
        return count;
    }

    public Boolean isEmpty() {
        return root == null;
    }

    public LinkedList<Integer> positionsPre() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        positionsPreAux(root, list);
        return list;
    }

    private void positionsPreAux(Node node, LinkedList<Integer> list) {
        if (node != null) {
            list.add(node.element);
            positionsPreAux(node.getLeft(), list);
            positionsPreAux(node.getRight(), list);
        }
    }

    public LinkedList<Integer> positionsPos() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        positionsPosAux(root,list );
        return list;
    }

    private void positionsPosAux(Node node, LinkedList<Integer> list) {
        if (node != null) {
            positionsPosAux(node.getLeft(), list);
            positionsPosAux(node.getRight(), list);
            list.add(node.element);
        }
    }

    public LinkedList<Integer> positionsCentral() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        positionsCentralAux(root, list);
        return list;
    }

    private void positionsCentralAux(Node node, LinkedList<Integer> list) {
        if (node != null) {
            positionsCentralAux(node.getLeft(), list);
            list.add(node.element);
            positionsCentralAux(node.getRight(), list);
        }
    }

    public LinkedList<Integer> positionsWidth() {
        Queue<Node> queue = new LinkedList<>();
        Node auxNode = null;
        LinkedList<Integer> list = new LinkedList<>();
        if (root != null) {
            queue.add(root);
            while (!queue.isEmpty()) {
                auxNode = queue.remove();
                if (auxNode.getLeft() != null) {
                    queue.add(auxNode.getLeft());
                }
                if (auxNode.getRight() != null) {
                    queue.add(auxNode.getRight());
                }
                list.add(auxNode.element);
            }
        }
        return list;
    }

    private Boolean isBalancedNode(Node node) {
        if (node == null) return true;
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
        if (node == null) return null;
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

    private Node removeAux(Node node, Integer e) {
        if (node == null) {
            return null;
        } else if (e < node.element) {
            node.setLeft(removeAux(node.getLeft(), e));
        } else if (e > node.element) {
            node.setRight(removeAux(node.getRight(), e));
        } else {
            if (node.getLeft() == null) {
                count--;
                return node.getRight();
            } else if (node.getRight() == null) {
                count--;
                return node.getLeft();
            } else {
                Node child = leftmostChild(node.getRight());
                node.element = child.element;
                node.setRight(removeAux(node.getRight(), node.element));
            }
        }
        return rebalance(node);
    }

    private Node leftmostChild(Node node) {
        if (node.getLeft() == null) return node;
        return leftmostChild(node.getLeft());
    }

    private Node rebalance(Node node) {
        updateHeight(node);

        int balance = getBalanceFactor(node);
        if (balance > 1) {
            if (getBalanceFactor(node.getRight()) < 0) {
                node.setRight(rightRotation(node.getRight().getLeft()));
                node = leftRotation(node.getRight());
            } else {
                node = leftRotation(node.getRight());
            }
        } else if (balance < -1) {
            if (getBalanceFactor(node.getLeft()) > 0) {
                node.setLeft(leftRotation(node.getLeft().getRight()));
                node = rightRotation(node.getLeft());
            } else {
                node = rightRotation(node.getLeft());
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
        Node parent = node.getParent();

        parent.setLeft(node.getRight());
        node.setRight(parent);

        updateHeight(parent);
        updateHeight(node);

        return node;
    }

    private Node leftRotation(Node node) {
        Node parent = node.getParent();

        parent.setRight(node.getLeft());
        node.setLeft(parent);

        updateHeight(parent);
        updateHeight(node);

        return node;
    }

}

