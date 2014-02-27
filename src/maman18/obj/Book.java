package maman18.obj;


/**
 * Book object.
 * Contains the book ID and the owner.
 */
public class Book {

    protected String id;
    protected Person owner;

    /**
     * Book constructor.
     *
     * @param id Book ID.
     */
    public Book(String id, Person owner) {
        this.id = id;
        this.owner = owner;
    }

    /**
     * ID getter.
     *
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Owner getter.
     *
     * @return Owner ID.
     */
    public Person getOwner() {
        return owner;
    }
}

