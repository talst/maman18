package maman18.list;

/**
 * Link of the double linked list.
 *
 * @param <O> The object type of the link.
 */
public class Link<O> {

    private O object;
    private Link<O> next;
    private Link<O> prev;

    /**
     * The link constructor.
     *
     * @param object The object the link will store
     */
    public Link(O object) {
        this.object = object;
    }

    /**
     * Object getter.
     *
     * @return The object contained in the link.
     */
    public O getObject() {
        return object;
    }

    /**
     * Next object getter.
     *
     * @return The next list link.
     */
    public Link<O> getNext() {
        return next;
    }

    /**
     * Set the next object
     *
     * @param next Link of the next object.
     */
    public void setNext(Link<O> next) {
        this.next = next;
    }

    /**
     * Previous object getter.
     *
     * @return The previous list link
     */
    public Link<O> getPrev() {
        return prev;
    }

    /**
     * Set the previous object.
     *
     * @param prev Link of the previous object.
     */
    public void setPrev(Link<O> prev) {
        this.prev = prev;
    }

}
