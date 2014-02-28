package maman18;

import maman18.list.DoubleLinkedList;
import maman18.list.Link;
import maman18.obj.Book;
import maman18.obj.Person;
import maman18.tree.NodeColor;
import maman18.tree.RBTreeNode;
import maman18.tree.RedBlackTree;

/**
 * The library database class.
 */
@SuppressWarnings("unchecked")
public class Library {

    protected RedBlackTree<Integer, Person> members;
    protected RedBlackTree<String, Book> books;
    protected DoubleLinkedList[] amountOfBooks;

    /**
     * Library class constructor.
     */
    public Library() {
        members = new RedBlackTree<Integer, Person>();
        books = new RedBlackTree<String, Book>();
        amountOfBooks = new DoubleLinkedList[11];
        for (int i = 0; i < amountOfBooks.length; i++) {
            amountOfBooks[i] = new DoubleLinkedList<Person>();
        }
    }

    /**
     * Add member method
     *
     * @param id       ID of the new member.
     * @param lastName Last name of the new member.
     * @return 1 if added, -1 if member already exists.
     */
    public int addMember(Integer id, String lastName) {
        RBTreeNode<Integer, Person> treeNode;
        Link<Person> listLink;
        Person newMember;
        if (this.members.search(id).getValue() == null) {
            newMember = new Person(id, lastName);
            treeNode = new RBTreeNode<Integer, Person>(newMember.getId(), newMember, NodeColor.RED);
            this.members.insert(treeNode);
            listLink = new Link<Person>(newMember);
            newMember.setPointerToLinkedList(listLink);
            this.amountOfBooks[0].insert(listLink);
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Remove member by ID, will also remove all of owned books from the tree.
     *
     * @param id the member ID
     * @return 1 if the member was removed, -1 if the member did not exist.
     */
    public int removeMember(Integer id) {
        RBTreeNode<Integer, Person> treeNode;
        treeNode = this.members.search(id);
        Person memberToDelete;
        Link<RBTreeNode<String, Book>> listHead;
        Link<RBTreeNode<String, Book>> tempPointer;
        if (treeNode.getKey() != null) {
            memberToDelete = treeNode.getValue();
            listHead = memberToDelete.getOwnedBooksHead();
            while (listHead.getObject() != null) {
                tempPointer = listHead.getNext();
                bookDeleteHelper(listHead, memberToDelete);
                listHead = tempPointer;
            }
            this.amountOfBooks[memberToDelete.ownedBookSize()].delete(memberToDelete.getPointerToLinkedList());
            this.members.delete(treeNode);
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Add a book to a specified member.
     * If the book does not exist in the book tree, this method will add it.
     *
     * @param memberId Member ID to add the book to.
     * @param bookId   Book ID to add.
     * @return 1 if the book was added or a negative value for 1 of 3 cases.
     * -1 book already exists in the tree.
     * -2 member was not found.
     * -3 member already have 10 books in his possession.
     */
    public int addBook(Integer memberId, String bookId) {
        RBTreeNode<String, Book> bookNode = this.books.search(bookId);
        Person member = this.members.search(memberId).getValue();
        Link<RBTreeNode<String, Book>> bookLink;
        Book newBook;
        if (bookNode.getValue() != null) {
            return -1;
        }
        if (member == null) {
            return -2;
        }
        if (member.ownedBookSize() >= 10) {
            return -3;
        } else {
            newBook = new Book(bookId, member);
            bookNode = new RBTreeNode<String, Book>(newBook.getId(), newBook, NodeColor.RED);
            bookLink = new Link<RBTreeNode<String, Book>>(bookNode);
            this.books.insert(bookNode);
            this.amountOfBooks[member.ownedBookSize()].delete(member.getPointerToLinkedList());
            member.addBook(bookLink);
            this.amountOfBooks[member.ownedBookSize()].insert(member.getPointerToLinkedList());
            return 1;
        }
    }

    /**
     * Remove a book from a member possession method.
     * The book be remove for the book tree.
     *
     * @param bookId  Book ID to be removed.
     * @param ownerId ID of the owner.
     * @return 1 if was successful or a negative value for 1 of 2 cases.
     * -1 book was not found.
     * -2 owners is not the owner of this book.
     */
    public int removeBook(Integer ownerId, String bookId) {
        RBTreeNode<String, Book> bookNode = this.books.search(bookId);
        Book bookToRemove = bookNode.getValue();
        Link<RBTreeNode<String, Book>> bookLink;
        Person owner;
        if (bookToRemove == null) {
            return -1;
        }
        if (!bookToRemove.getOwner().getId().equals(ownerId)) {
            return -2;
        } else {
            owner = bookToRemove.getOwner();
            bookLink = owner.searchBookLink(bookNode);
            bookDeleteHelper(bookLink, owner);
            return 1;
        }
    }

    /**
     * Get all books a member possess query method.
     *
     * @param memberId Member ID to return the list for.
     * @return String array of the book ids.
     * If the member have no books will return array of size 0.
     * If member was not found return null.
     */
    public String[] getOwnedBooksForMember(Integer memberId) {
        Person member = this.members.search(memberId).getValue();
        String[] bookArray;
        Link<RBTreeNode<String, Book>> bookLink;
        if (member != null) {
            bookArray = new String[member.ownedBookSize()];
            bookLink = member.getOwnedBooksHead();
            for (int i = 0; i < bookArray.length; i++) {
                bookArray[i] = bookLink.getObject().getValue().getId();
                bookLink = bookLink.getNext();
            }
            return bookArray;
        } else {
            return null;
        }
    }

    /**
     * Find the owner of a book query method.
     *
     * @param bookId Book ID to find owner for.
     * @return String with owner last name and id for example "Tal 12432".
     */
    public String getBookOwner(String bookId) {
        Book book = this.books.search(bookId).getValue();
        String output;
        if (book != null) {
            output = book.getOwner().getLastName() + " " + book.getOwner().getId();
            return output;
        } else {
            return null;
        }
    }

    /**
     * Find the members who hold the largest amount of books.
     *
     * @return String array of the members last name followed by id.
     * If all members have 0 books will return an array of size 0.
     * if no members exists will return null.
     */
    public String[] getMembersWithMostBooks() {
        String[] membersArray;
        Link<Person> listLink;
        for (int i = 10; i > 0; i--) {
            if (this.amountOfBooks[i].getSize() > 0) {
                membersArray = new String[this.amountOfBooks[i].getSize()];
                listLink = this.amountOfBooks[i].search(null).getNext();
                for (int j = 0; j < membersArray.length; j++) {
                    membersArray[j] = listLink.getObject().getLastName() + " " + listLink.getObject().getId();
                    listLink = listLink.getNext();
                }
                return membersArray;
            }
        }
        if (this.amountOfBooks[0].getSize() > 0) {
            membersArray = new String[0];
            return membersArray;
        } else {
            return null;
        }
    }

    /**
     * Maintain all the correct information for the database upon deleting a book.
     *
     * @param bookLink Link of the book node.
     * @param owner    Person object for the owner.
     */
    private void bookDeleteHelper(Link<RBTreeNode<String, Book>> bookLink, Person owner) {
        this.amountOfBooks[owner.ownedBookSize()].delete(owner.getPointerToLinkedList());
        this.books.delete(bookLink.getObject());
        owner.deleteBook(bookLink);
        this.amountOfBooks[owner.ownedBookSize()].insert(owner.getPointerToLinkedList());
    }
}

