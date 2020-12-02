public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(3);
        tree.insert(1);
        tree.insert(2);
        tree.insert(0);
        tree.insert(7);
        tree.insert(8);
        tree.insert(10);
        tree.insert(11);
        tree.insert(13);
        tree.insert(14);
        tree.insert(15);
        System.out.println(tree.contains(33));
        System.out.println(tree.contains(15));
        System.out.println(tree);
    }
}
