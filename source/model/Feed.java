package model;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

/**
 * A Feed is a dynamic collection (similar to an ArrayList) of Auctions with specific functionality for Artatawe.
 * <p>
 * A Feed is a singleton, meaning only one instance can exist at any given time.
 * To get a new instance of Feed use one of its many static factory methods.
 * <p>
 * The functionality of a Feed is limited, a Feed can only perform the following:
 * <ul>
 * <li>Add an Auction or a collection of Auctions to its contents</li>
 * <li>Clear its contents</li>
 * <li>Retrieve all of its contents as a collection</li>
 * <li>Copy all of its contents to a new instance of itself</li>
 * <li>Update itself with new contents, this would mean clearing and adding</li>
 * </ul>
 * <p>
 * Note that a Feed is iterable, meaning it can be used in a for each loop.
 *
 * @author Bassam Helal
 * @version 1.1
 * @see Auction
 * @see Iterable
 * @see ArrayList
 */
public final class Feed implements Iterable<Auction> {

    //The current Feed instance, may be null
    @Nullable
    private static Feed instance;

    //The main data structure, an ArrayList
    private ArrayList<Auction> arrayList;

    //The initial capacity, note that the Feed grows
    //this is only useful for guaranteeing no overhead with larger collections
    private final static int DEFAULT_CAPACITY = 50;

    /**
     * The only constructor to create a Feed, used by all the factory methods
     *
     * @param capacity the initial capacity of the Feed to be returned
     */
    private Feed(int capacity) {
        arrayList = new ArrayList<>(capacity);
    }

    /**
     * Returns the instance of Feed.
     * <p>
     * If one does not exist returns a new one with the default size
     *
     * @return the current instance of Feed if one exists, a new one otherwise
     */
    public static Feed getInstance() {
        if (instance == null) {
            instance = new Feed(DEFAULT_CAPACITY);
        }
        return instance;
    }

    /**
     * Returns a new instance of Feed with the default size.
     * <p>
     * This would be used to delete the current instance and get new one
     *
     * @return a new instance of Feed with the default size
     */
    public static Feed getNewInstance() {
        instance = new Feed(DEFAULT_CAPACITY);
        return instance;
    }

    /**
     * Returns a new instance of Feed with the given size.
     * <p>
     * This would be used to delete the current instance and get new one with the given size
     *
     * @param capacity the desired initial size of the new Feed
     * @return a new instance of Feed with the given size
     */
    public static Feed getNewInstanceWithCapacity(int capacity) {
        instance = new Feed(capacity);
        return instance;
    }

    /**
     * Gets the size of the Feed.
     * <p>
     * This means how many elements are contained in the Feed, not its capacity.
     *
     * @return the int representing the number of elements in the Feed
     */
    public int size() {
        return arrayList.size();
    }

    /**
     * Tells whether the Feed is empty or not
     *
     * @return the boolean representing whether the Feed is empty or not
     */
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    /**
     * Returns the Iterator of the Feed, an Iterator of type Auction
     *
     * @return the Iterator of type Auction
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<Auction> iterator() {
        return arrayList.iterator();
    }

    /**
     * Would be used when calling a for each loop on a Feed
     *
     * @param action the action to take on the Iterator
     * @see Iterable#forEach(Consumer)
     */
    @Override
    public void forEach(Consumer action) {
        arrayList.forEach(action);
    }

    /**
     * Adds one Auction to the Feed
     *
     * @param auction the Auction to be added
     */
    public void add(Auction auction) {
        arrayList.add(auction);
    }

    /**
     * Adds an array or varargs number of Auctions to the Feed
     *
     * @param auctions the array or varargs to be added
     */
    public void addAll(Auction... auctions) {
        arrayList.addAll(Arrays.asList(auctions));
    }

    /**
     * Adds a Collection of Auctions to the Feed
     *
     * @param auctions the collection of Auctions to be added
     */
    public void addAll(Collection<Auction> auctions) {
        arrayList.addAll(auctions);
    }

    /**
     * Clears the Feed, this will reduce its size to 0, making it empty.
     * <p>
     * Note that this does not delete the current instance, neither does it tamper with the
     * instance's capacity for those cases, retrieve a new instance.
     *
     * @see Feed#getNewInstance()
     * @see Feed#getNewInstanceWithCapacity(int)
     */
    public void clear() {
        arrayList.clear();
    }

    /**
     * Gets all the contents of the Feed as an ArrayList, this would be useful for
     * Collections API based functions.
     *
     * @return the ArrayList containing all the contents of the Feed
     */
    public ArrayList<Auction> getAllAsArrayList() {
        return arrayList;
    }

    /**
     * Gets all the elements of the Feed as an Array.
     * Note that the returned array would be filled,
     * its length would be exactly that of the Feed size, not capacity.
     *
     * @return the Array containing all the contents of the Feed
     */
    public Auction[] getAllAsArray() {
        return arrayList.toArray(new Auction[arrayList.size()]);
    }

    /**
     * Copies all the contents of the current instance (if one exists) to a new instance with the default
     * size.
     * <p>
     * This should not be needed often since the Feed is dynamic.
     *
     * @return the new Feed instance with all the current instance's elements copied,
     * its size would be the default size.
     */
    public Feed copyToNewInstance() {
        if (instance == null) {
            return getNewInstance();
        } else {
            Feed local = new Feed(DEFAULT_CAPACITY);
            local.addAll(instance.getAllAsArrayList());
            instance = local;
            return instance;
        }
    }

    /**
     * Copies all the contents of the current instance (if one exists) to a new instance with the given
     * size.
     * <p>
     * This may be used when a sudden growth is required.
     *
     * @param capacity the desired size of the new Feed instance
     * @return the new Feed instance with all the current instance's elements copied,
     * its size would be the given size.
     */
    public Feed copyToNewInstanceWithCapacity(int capacity) {
        if (instance == null) {
            return getNewInstanceWithCapacity(capacity);
        } else {
            Feed local = new Feed(capacity);
            local.addAll(instance.getAllAsArrayList());
            instance = local;
            return instance;
        }
    }

    /**
     * Updates the current Feed instance with the given Collection.
     * <p>
     * Updating means clearing the instance's contents and filling them with the parameter.
     *
     * @param auctions the Collection of Auctions to fill the Feed with
     * @return the new Feed instance
     */
    public Feed updateWith(Collection<Auction> auctions) {
        Auction[] converted = auctions.toArray(new Auction[0]);
        return newInstanceClearOtherwise(converted);
    }

    /**
     * Updates the current Feed instance with the given Array or varargs.
     * <p>
     * Updating means clearing the instance's contents and filling them with the parameter.
     *
     * @param auctions the array or varargs Auctions to fill the Feed with
     * @return the new Feed instance
     */
    public Feed updateWith(Auction... auctions) {
        return newInstanceClearOtherwise(auctions);
    }

    private Feed newInstanceClearOtherwise(Auction[] strings) {
        if (instance == null) {
            instance = new Feed(DEFAULT_CAPACITY);
            instance.addAll(strings);
        } else {
            instance.clear();
            instance.addAll(strings);
        }
        return instance;
    }

    /**
     * Updates the current Feed instance with the given Collection and a new size.
     * <p>
     * If the current instance's size is less than the given size then a new instance is created.
     * <p>
     * Updating means clearing the instance's contents and filling them with the parameter.
     *
     * @param capacity the desired size of the Feed
     * @param auctions the Collection of Auctions to fill the Feed with
     * @return the new Feed instance
     */
    public Feed updateWithCapacity(int capacity, Collection<Auction> auctions) {
        Auction[] converted = auctions.toArray(new Auction[0]);
        return updateWithCapacityCall(capacity, converted);
    }

    /**
     * Updates the current Feed instance with the given Array or varargs and a new size.
     * <p>
     * If the current instance's size is less than the given size then a new instance is created.
     * <p>
     * Updating means clearing the instance's contents and filling them with the parameter.
     *
     * @param capacity the desired size of the Feed
     * @param auctions the Array or varargs Auctions to fill the Feed with
     * @return the new Feed instance
     */
    public Feed updateWithCapacity(int capacity, Auction... auctions) {
        return updateWithCapacityCall(capacity, auctions);
    }

    private Feed updateWithCapacityCall(int capacity, Auction[] strings) {
        if (instance == null) {
            instance = new Feed(capacity);
            instance.addAll(strings);
        } else {
            instance = new Feed(capacity);
            instance.addAll(strings);
        }
        return instance;
    }

    /**
     * Destroys the current Feed instance, this is safe and has no effect and purpose
     * other than to clear memory, would be used before application shutdown for example
     * or to save memory when the current instance is becoming very large
     */
    public void destroyInstance() {
        instance = null;
    }

    /**
     * Sorts the current Feed instance.
     *
     * @see Collections#sort(List)
     */
    public void sortByDate() {
        Collections.sort(this.arrayList);
    }

    /**
     * Gets the hashcode of the Feed.
     *
     * @return the integer representing the hashcode of the Feed
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return arrayList.hashCode();
    }

    /**
     * Checks whether this Feed is equal to the Feed passed in, this is only used as a utility and doesn't currently
     * have a sensible use.
     *
     * @return true if they are equal and false otherwise
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return ((super.equals(obj) && (obj.hashCode() == this.hashCode())));
    }

    /**
     * Returns a String representation of the Feed.
     *
     * @return the String representation of the Feed
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder contents = new StringBuilder();
        for (Auction auction : this.arrayList) {
            contents.append(auction.toString());
            contents.append("\n\t\t");
        }
        return "Feed: " + "\n" +
                "\tSize: " + this.size() + "\n" +
                "\tContents: " + contents + "\n";
    }


}
