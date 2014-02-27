package maman18.obj;

import maman18.list.DoubleLinkedList;
import maman18.list.Link;
import maman18.tree.RBTreeNode;

/**
 * Person object.
 * Holds the ID of the person, name,
 * pointer to the link in a list,
 * and a list of the owned books RBTreeNode.
 */
public class Person {

    private Integer id;
    private String lastName;
    private DoubleLinkedList<RBTreeNode<String, Book>> ownedBooks;
    private Link<Person> pointerToLinkedList;

    /**
     * Person constructor.
     *
     * @param id        The person ID.
     * @param lastName  Person last name.
     */
    public Person(Integer id, String lastName) {
        this.id = id;
        this.lastName = lastName;
        this.ownedBooks = new DoubleLinkedList<RBTreeNode<String, Book>>();
        this.pointerToLinkedList = null;
    }

    /**
     * ID getter.
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Last name getter.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * List link getter
     *
     * @return Link of the list.
     */
    public Link<Person> getPointerToLinkedList() {
        return pointerToLinkedList;
    }

    /**
     * List link setter.
     *
     * @param pointerToLinkedList List link of Person.
     */
    public void setPointerToLinkedList(Link<Person> pointerToLinkedList) {
        this.pointerToLinkedList = pointerToLinkedList;
    }

    /**
     * Add book to the owned list.
     * Check that list has room.
     *
     * @param bookNode The link containing the book node
     */
    public void addBook(Link<RBTreeNode<String, Book>> bookNode) {
        if (this.ownedBooks.getSize() < 10) {
            this.ownedBooks.insert(bookNode);
        }
    }

    /**
     * Delete a book from the list.
     *
     * @param bookNode The link to remove.
     */
    public void deleteBook(Link<RBTreeNode<String, Book>> bookNode) {
        this.ownedBooks.delete(bookNode);
    }

    /**
     * Search the list for the node
     *
     * @param bookNode Node to find.
     * @return The link if found or null if does not exists.
     */
    public Link<RBTreeNode<String, Book>> searchBookLink(RBTreeNode<String, Book> bookNode) {
        return this.ownedBooks.search(bookNode);
    }

    /**
     * Find and return the head of the list.
     *
     * @return Link object of the head.
     */
    public Link<RBTreeNode<String, Book>> getOwnedBooksHead() {
        return this.ownedBooks.search(null).getNext();
    }

    /**
     * The list size getter.
     *
     * @return the size.
     */
    public int ownedBookSize() {
        return this.ownedBooks.getSize();
    }

}
