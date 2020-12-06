import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        System.out.println("Está vazia? " + tree.isEmpty());
        tree.add(3);
        tree.add(2);
        tree.add(11);
        tree.add(0);
        tree.add(7);
        tree.add(1);
        tree.add(8);
        tree.add(14);
        tree.add(10);
        tree.add(13);
        tree.add(4);
        tree.add(5);
        tree.remove(7);

        System.out.println("Contém 5? " + tree.contains(5));
        System.out.println("Está balanceada? " + tree.isBalanced());
        System.out.println("Altura da árvore:" + tree.getTreeHeight());
        System.out.println("Pré: " + Arrays.toString(tree.positionsPre().toArray(new Integer[0])));
        System.out.println("Pós: " + Arrays.toString(tree.positionsPos().toArray(new Integer[0])));
        System.out.println("Central: " + Arrays.toString(tree.positionsCentral().toArray(new Integer[0])));
        System.out.println("Width: " + Arrays.toString(tree.positionsWidth().toArray(new Integer[0])));



    }
}
