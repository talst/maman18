package maman18.tree;

/**
 * Red Black Tree class.
 *
 * @param <K> Object type of the key must be a Comparable object.
 * @param <V> Object type of the value.
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private final RBTreeNode<K, V> NULL_NODE = new RBTreeNode<K, V>(null, null, NodeColor.BLACK);
    private RBTreeNode<K, V> root;
    private int size;

    /**
     * RedBlackTree constructor.
     */
    public RedBlackTree() {
        this.root = NULL_NODE;
        this.size = 0;
    }

    /**
     * Size getter
     *
     * @return size of the tree.
     */
    public int getSize() {
        return size;
    }

    /**
     * Search method for the tree.
     *
     * @param key Key to search for.
     * @return Tree node of the key, returns NULL_NODE if not found.
     */
    public RBTreeNode<K, V> search(K key) {

        RBTreeNode<K, V> currentNode;
        currentNode = this.root;

        while (currentNode != NULL_NODE && !key.equals(currentNode.getKey())) {

            if (key.compareTo(currentNode.getKey()) < 0) {
                currentNode = currentNode.getLeftChild();
            } else {
                currentNode = currentNode.getRightChild();
            }
        }

        return currentNode;
    }

    /**
     * Find successor to a give node.
     *
     * @param x Node to find a successor for.
     * @return The node of the successor.
     */
    private RBTreeNode<K, V> findSuccessor(RBTreeNode<K, V> x) {
        RBTreeNode<K, V> y;
        if (x.getRightChild() != NULL_NODE) {
            return treeMinimum(x.getRightChild());
        }
        y = x.getParent();
        while (y != NULL_NODE && x == y.getRightChild()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    /**
     * Find minimum for sub-tree of given node.
     *
     * @param x Node to find minimum for.
     * @return Node of the minimum.
     */
    private RBTreeNode<K, V> treeMinimum(RBTreeNode<K, V> x) {
        while (x.getLeftChild() != NULL_NODE) {
            x = x.getLeftChild();
        }
        return x;
    }

    /**
     * Right rotate helper method.
     *
     * @param x Node to rotate around.
     */
    private void rightRotate(RBTreeNode<K, V> x) {
        RBTreeNode<K, V> y;
        y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());
        if (y.getRightChild() != NULL_NODE) {
            y.getRightChild().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == NULL_NODE) {
            this.root = y;
        } else {
            if (x == x.getParent().getRightChild()) {
                x.getParent().setRightChild(y);
            } else {
                x.getParent().setLeftChild(y);
            }
        }
        y.setRightChild(x);
        x.setParent(y);
    }

    /**
     * Left rotate helper method.
     *
     * @param x Node to rotate around.
     */
    private void leftRotate(RBTreeNode<K, V> x) {
        RBTreeNode<K, V> y;
        y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if (y.getLeftChild() != NULL_NODE) {
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == NULL_NODE) {
            this.root = y;
        } else {
            if (x == x.getParent().getLeftChild()) {
                x.getParent().setLeftChild(y);
            } else {
                x.getParent().setRightChild(y);
            }
        }
        y.setLeftChild(x);
        x.setParent(y);
    }

    /**
     * Tree insert method.
     *
     * @param z node to insert.
     */
    public void insert(RBTreeNode<K, V> z) {

        RBTreeNode<K, V> y, x;
        x = NULL_NODE;
        y = this.root;

        while (y != NULL_NODE) {
            x = y;
            if (z.getKey().compareTo(y.getKey()) < 0) {
                y = y.getLeftChild();
            } else {
                y = y.getRightChild();
            }
        }

        z.setParent(x);
        if (x == NULL_NODE) {
            this.root = z;
        } else {
            if (z.getKey().compareTo(x.getKey()) < 0) {
                x.setLeftChild(z);
            } else {
                x.setRightChild(z);
            }
        }
        z.setLeftChild(NULL_NODE);
        z.setRightChild(NULL_NODE);
        z.setColor(NodeColor.RED);
        insertFixup(z);
        this.size++;
    }

    /**
     * Insert fixup helper method.
     *
     * @param z Node to fix.
     */
    private void insertFixup(RBTreeNode<K, V> z) {
        RBTreeNode<K, V> y;
        while (z.getParent().getColor() == NodeColor.RED) {
            if (z.getParent() == z.getParent().getParent().getLeftChild()) {
                y = z.getParent().getParent().getRightChild();
                if (y.getColor() == NodeColor.RED) {
                    /*Case 1*/
                    z.getParent().setColor(NodeColor.BLACK);
                    y.setColor(NodeColor.BLACK);
                    z.getParent().getParent().setColor(NodeColor.RED);
                    z = z.getParent().getParent();
                    /*End of Case 1*/
                } else {
                    if (z == z.getParent().getRightChild()) {
                        /*Case 2*/
                        z = z.getParent();
                        leftRotate(z);
                        /*End of Case 2*/
                    }
                    /*Case 3*/
                    z.getParent().setColor(NodeColor.BLACK);
                    z.getParent().getParent().setColor(NodeColor.RED);
                    rightRotate(z.getParent().getParent());
                    /*End of Case 3*/
                }
            } else {
                y = z.getParent().getParent().getLeftChild();
                if (y.getColor() == NodeColor.RED) {
                    /*Case 1*/
                    z.getParent().setColor(NodeColor.BLACK);
                    y.setColor(NodeColor.BLACK);
                    z.getParent().getParent().setColor(NodeColor.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeftChild()) {
                        /*Case 2*/
                        z = z.getParent();
                        rightRotate(z);
                        /*End of Case 2*/
                    }
                    /*Case 3*/
                    z.getParent().setColor(NodeColor.BLACK);
                    z.getParent().getParent().setColor(NodeColor.RED);
                    leftRotate(z.getParent().getParent());
                    /*End of Case 3*/
                }
            }
        }
        this.root.setColor(NodeColor.BLACK);
    }

    /**
     * Tree delete method.
     *
     * @param z Node to delete.
     */
    public void delete(RBTreeNode<K, V> z) {
        RBTreeNode<K, V> y, x;
        NodeColor tempColor = null;
        if (z.getLeftChild() == NULL_NODE || z.getRightChild() == NULL_NODE) {
            y = z;
        } else {
            y = findSuccessor(z);
        }
        if (y.getLeftChild() != NULL_NODE) {
            x = y.getLeftChild();
        } else {
            x = y.getRightChild();
        }
        x.setParent(y.getParent());
        if (y.getParent() == NULL_NODE) {
            this.root = x;
        } else {
            if (y == y.getParent().getLeftChild()) {
                y.getParent().setLeftChild(x);
            } else {
                y.getParent().setRightChild(x);
            }
        }
        if (y != z) {                                // Due to limitation of Java we can't swap nodes (java pass by reference and not pointer).
            y.setLeftChild(z.getLeftChild());        // What we do is "turn" y into z this way we can keep all the correct objects
            if (z.getLeftChild() != NULL_NODE) {     // in the right structure.
                z.getLeftChild().setParent(y);
            }
            y.setRightChild(z.getRightChild());
            if (z.getRightChild() != NULL_NODE) {
                z.getRightChild().setParent(y);
            }
            y.setParent(z.getParent());
            if (z.getParent() == NULL_NODE) {
                this.root = y;
            } else {
                if (z == z.getParent().getLeftChild()) {
                    z.getParent().setLeftChild(y);
                } else {
                    z.getParent().setRightChild(y);
                }
            }
            tempColor = y.getColor();
            y.setColor(z.getColor());

        }
        if (tempColor != null) {
            if (tempColor == NodeColor.BLACK && x != NULL_NODE) {  // Make sure we don't fix around the NULL_NODE
                deleteFixup(x);
            }
        } else {
            if (y.getColor() == NodeColor.BLACK && x != NULL_NODE) { // Make sure we don't fix around the NULL_NODE
                deleteFixup(x);
            }
        }
        this.size--;
    }

    /**
     * Delete fixup helper method.
     *
     * @param x Node to fix.
     */
    private void deleteFixup(RBTreeNode<K, V> x) {
        RBTreeNode<K, V> w;
        while (x != this.root && x.getColor() == NodeColor.BLACK) {
            if (x == x.getParent().getLeftChild()) {
                w = x.getParent().getRightChild();
                if (w.getColor() == NodeColor.RED) {
                    /*Case 1*/
                    w.setColor(NodeColor.BLACK);
                    x.getParent().setColor(NodeColor.RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRightChild();
                    /*End of Case 1*/
                }
                if (w.getLeftChild().getColor() == NodeColor.BLACK && w.getRightChild().getColor() == NodeColor.BLACK) {
                    /*Case 2*/
                    w.setColor(NodeColor.RED);
                    x = x.getParent();
                    /*End of Case 2*/
                } else {
                    if (w.getRightChild().getColor() == NodeColor.BLACK) {
                        /*Case 3*/
                        w.getLeftChild().setColor(NodeColor.BLACK);
                        w.setColor(NodeColor.RED);
                        rightRotate(w);
                        w = x.getParent().getRightChild();
                        /*End of Case 3*/
                    }
                    /*Case 4*/
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(NodeColor.BLACK);
                    w.getRightChild().setColor(NodeColor.BLACK);
                    leftRotate(x.getParent());
                    x = this.root;
                }
            } else {
                w = x.getParent().getLeftChild();
                if (w.getColor() == NodeColor.RED) {
                    /*Case 1*/
                    w.setColor(NodeColor.BLACK);
                    x.getParent().setColor(NodeColor.RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftChild();
                    /*End of Case 1*/
                }
                if (w.getRightChild().getColor() == NodeColor.BLACK && w.getLeftChild().getColor() == NodeColor.BLACK) {
                    /*Case 2*/
                    w.setColor(NodeColor.RED);
                    x = x.getParent();
                    /*End of Case 2*/
                } else {
                    if (w.getLeftChild().getColor() == NodeColor.BLACK) {
                        /*Case 3*/
                        w.getRightChild().setColor(NodeColor.BLACK);
                        w.setColor(NodeColor.RED);
                        leftRotate(w);
                        w = x.getParent().getLeftChild();
                        /*End of Case 3*/
                    }
                    /*Case 4*/
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(NodeColor.BLACK);
                    w.getLeftChild().setColor(NodeColor.BLACK);
                    rightRotate(x.getParent());
                    x = this.root;
                }
            }
        }
    }
}