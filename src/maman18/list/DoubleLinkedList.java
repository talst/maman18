package maman18.list;

/**
 * Double linked list implantation.
 *
 * @param <O> The object type for the list.
 */
public class DoubleLinkedList<O> {

    private final Link<O> NULL_LINK = new Link<O>(null);
    private int size;

    /**
     * The list constructor.
     */
    public DoubleLinkedList() {
        this.NULL_LINK.setNext(this.NULL_LINK);
        this.NULL_LINK.setPrev(this.NULL_LINK);
        this.size = 0;
    }

    /**
     * List search method.
     *
     * @param object Object to search.
     * @return Link of the list containing the object, null NULL_NODE if does not exist.
     */
    public Link<O> search(O object) {
        Link<O> x;
        x = NULL_LINK.getNext();
        while (x != NULL_LINK && !(x.getObject() == object)) {
            x = x.getNext();
        }
        return x;
    }

    /**
     * List insert method.
     *
     * @param x Link to insert.
     */
    public void insert(Link<O> x) {
        x.setNext(NULL_LINK.getNext());
        NULL_LINK.getNext().setPrev(x);
        NULL_LINK.setNext(x);
        x.setPrev(NULL_LINK);
        this.size++;
    }

    /**
     * List remove method.
     *
     * @param x Link to remove.
     */
    public void delete(Link<O> x) {
        x.getPrev().setNext(x.getNext());
        x.getNext().setPrev(x.getPrev());
        this.size--;
    }


    /**
     * Size getter
     *
     * @return size of the list
     */
    public int getSize() {
        return size;
    }
}
