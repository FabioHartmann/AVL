import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.add(3);
        tree.add(1);
        tree.add(2);
        tree.add(0);
        tree.add(7);
        tree.add(8);
        tree.add(10);
        tree.add(11);
        tree.add(13);
        tree.add(14);
        tree.add(15);
        System.out.println(tree);
        System.out.println(tree.size());
        tree.remove(11);
        System.out.println(tree);
        System.out.println(tree.getParent(2));
        System.out.println(tree.getTreeHeight());
        System.out.println(tree.isBalanced());
        System.out.println(tree.size());
        System.out.println(tree.isEmpty());
        System.out.println(Arrays.toString(tree.positionsPre().toArray(new Integer[0])));
        System.out.println(Arrays.toString(tree.positionsPos().toArray(new Integer[0])));
        System.out.println(Arrays.toString(tree.positionsCentral().toArray(new Integer[0])));
        System.out.println(Arrays.toString(tree.positionsWidth().toArray(new Integer[0])));

        /*System.out.println(tree.getParent(3));
        tree.remove(3);
        System.out.println(tree);

        System.out.println(tree.getParent(10));
        System.out.println(tree.getParent(7));*/

    }
}
