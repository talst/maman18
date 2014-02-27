package maman18.tree;

/**
 * Red Black Tree Node class.
 *
 * @param <K> Object type of the key must be a Comparable object.
 * @param <V> Object type of the value.
 */
public class RBTreeNode<K extends Comparable<K>, V> {

    public K key;
    public V value;
    private NodeColor color;
    private RBTreeNode<K, V> leftChild, rightChild, parent;


    /**
     * Tree Node constructor.
     *
     * @param key   Key object.
     * @param value Value object.
     * @param color Color of the Node.
     */
    public RBTreeNode(K key, V value, NodeColor color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    /**
     * Key object getter.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Key setter
     *
     * @param key New key for the node.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Value object getter.
     *
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Value setter
     *
     * @param value new value for the node.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Left child getter.
     *
     * @return the leftChild
     */
    public RBTreeNode<K, V> getLeftChild() {
        return leftChild;
    }

    /**
     * Left child setter.
     *
     * @param leftChild RBTreeNode to set as a left child.
     */
    public void setLeftChild(RBTreeNode<K, V> leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Right Child getter.
     *
     * @return the rightChild.
     */
    public RBTreeNode<K, V> getRightChild() {
        return rightChild;
    }

    /**
     * Right child setter.
     *
     * @param rightChild RBTreeNode to set as right child.
     */
    public void setRightChild(RBTreeNode<K, V> rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Parent getter.
     *
     * @return the parent.
     */
    public RBTreeNode<K, V> getParent() {
        return parent;
    }

    /**
     * Parent setter.
     *
     * @param parent RBTreeNode to set as a parent.
     */
    public void setParent(RBTreeNode<K, V> parent) {
        this.parent = parent;
    }

    /**
     * Color getter
     *
     * @return NodeColor of the node./
     */
    public NodeColor getColor() {
        return color;
    }

    /**
     * Color setter.
     *
     * @param color Color of the Node.
     */
    public void setColor(NodeColor color) {
        this.color = color;
    }
}
