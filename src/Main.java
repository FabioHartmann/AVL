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
        tree.remove(10);
        System.out.println(tree);
        tree.remove(11);
        System.out.println(tree);
        tree.remove(7);
        System.out.println(tree);
        /*tree.remove(14);
        System.out.println(tree);
        tree.remove(11);
        System.out.println(tree);*/
        System.out.println(tree.getParent(15));
        /*System.out.println(tree.getParent(3));
        tree.remove(3);
        System.out.println(tree);

        System.out.println(tree.getParent(10));
        System.out.println(tree.getParent(7));*/

    }
}
