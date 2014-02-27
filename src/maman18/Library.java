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
     * @param member The member to add.
     * @return True if added, false if member already exists.
     */
    public boolean addMemeber(Person member) {
        RBTreeNode<Integer, Person> treeNode;
        Link<Person> listLink;
        if (this.members.search(member.getId()).getKey() == null) {
            treeNode = new RBTreeNode<Integer, Person>(member.getId(), member, NodeColor.RED);
            this.members.insert(treeNode);
            listLink = new Link<Person>(member);
            member.setPointerToLinkedList(listLink);
            this.amountOfBooks[0].insert(listLink);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove member by ID, will also remove all of owned books from the tree.
     *
     * @param id the member ID
     * @return True if the member was removed, false if the member did not exist.
     */
    public boolean removeMember(Integer id) {
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
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a book to a specified member.
     * If the book does not exist in the book tree, this method will add it.
     *
     * @param memberId Member ID to add the book to.
     * @param bookId   Book ID to add.
     * @return True if the book was added, false if the book was not added
     * (could be because of 3 reasons: Book was already in the book tree and had an owner,
     * member was not found, and member already have 10 books in his possession).
     */
    public boolean addBook(Integer memberId, String bookId) {
        RBTreeNode<String, Book> bookNode = this.books.search(bookId);
        Person member = this.members.search(memberId).getValue();
        Link<RBTreeNode<String, Book>> bookLink;
        Book newBook;
        if ((bookNode.getKey() == null) && (member != null) && (member.ownedBookSize() < 10)) {
            newBook = new Book(bookId, member);
            bookNode = new RBTreeNode<String, Book>(newBook.getId(), newBook, NodeColor.RED);
            bookLink = new Link<RBTreeNode<String, Book>>(bookNode);
            this.books.insert(bookNode);
            this.amountOfBooks[member.ownedBookSize()].delete(member.getPointerToLinkedList());
            member.addBook(bookLink);
            this.amountOfBooks[member.ownedBookSize()].insert(member.getPointerToLinkedList());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a book from a member possession method.
     * The book be remove for the book tree.
     *
     * @param bookId  Book ID to be removed.
     * @param ownerId ID of the owner.
     * @return True if was successful, false if the book was not found or the member does not possess the book.
     */
    public boolean removeBook(Integer ownerId, String bookId) {
        RBTreeNode<String, Book> bookNode = this.books.search(bookId);
        Book bookToRemove = bookNode.getValue();
        Link<RBTreeNode<String, Book>> bookLink;
        Person owner;
        if (bookToRemove != null && bookToRemove.getOwner().getId() == ownerId) {
            owner = bookToRemove.getOwner();
            bookLink = owner.searchBookLink(bookNode);
            bookDeleteHelper(bookLink, owner);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get all books a member possess query method.
     *
     * @param memberId Member ID to return the list for.
     * @return An array of Book objects.
     */
    public Book[] getOwnedBooksForMember(Integer memberId) {
        Person member = this.members.search(memberId).getValue();
        Book[] bookArray;
        Link<RBTreeNode<String, Book>> bookLink;
        if (member != null) {
            bookArray = new Book[member.ownedBookSize()];
            bookLink = member.getOwnedBooksHead();
            for (int i = 0; i < bookArray.length; i++) {
                bookArray[i] = bookLink.getObject().getValue();
                bookLink.getNext();
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
     * @return Person object of the owner.
     */
    public Person getBookOwner(String bookId) {
        Book book = this.books.search(bookId).getValue();
        if (book != null) {
            return book.getOwner();
        } else {
            return null;
        }
    }

    /**
     * Find the members who hold the largest amount of books.
     *
     * @return An array of Person objects
     */
    public Person[] getMembersWithMostBooks() {
        Person[] membersArray;
        Link<Person> listLink;
        for (int i = 10; i >= 0; i--) {
            if (this.amountOfBooks[i].getSize() > 0) {
                membersArray = new Person[this.amountOfBooks[i].getSize()];
                listLink = this.amountOfBooks[i].search(null).getNext();
                for (int j = 0; j < membersArray.length; j++) {
                    membersArray[j] = listLink.getObject();
                    listLink = listLink.getNext();
                }
                return membersArray;
            }
        }
        return null;
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

