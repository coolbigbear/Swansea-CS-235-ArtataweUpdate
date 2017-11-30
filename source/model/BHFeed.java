package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A Feed is a dynamic collection of Auctions with specific functionality for Artatawe.
 *
 * A Feed is a singleton, meaning only one instance can exist at any given time.
 * To get a new instance of Feed use one of its many static factory methods.
 *
 * The functionality of a Feed is limited, a Feed can only perform the following:
 * <ul>
 * <li>Add an Auction or a collection of Auctions to its contents</li>
 * <li>Clear its contents</li>
 * <li>Retrieve all of its contents as a collection</li>
 * <li>Copy all of its contents to a new instance of itself</li>
 * <li>Update itself with new contents, this would mean clearing and adding</li>
 * </ul>
 *
 * Note that a Feed is iterable, meaning it can be used in a for each loop.
 *
 * @author Bassam Helal
 * @version 1.0
 * @see Auction
 * @see Iterable
 */
public final class BHFeed implements Iterable<Auction> {
	
	// TODO: 29-Nov-17 Bassam Helal, consider sorting and getting sorted lists based on a given Comparator
	
	/*
	 * A sequential collection of Auctions that is required to
	 * update itself entirely, clear itself entirely and return an iterable
	 * copy of itself which will be used by the view to show Auctions
	 *
	 * This is because of the following:
	 *
	 * A Feed will be used to contain all the Auctions read from the Database, it
	 * will not care about parameters or filtering, it will only be a collection of
	 * Auctions and the view will use this collection to decide what to display,
	 * the filtering should be done through a method in Util by reading specific things in the
	 * Database, for example to see only Paintings the function will read through the
	 * entire Database and choose what to add to the Feed based on this filtering, the Feed
	 * never knows about what the Database is doing, in fact the Feed does not know about the Database
	 * at all.
	 *
	 */
	
	/*
	 * This implementation of Feed is like a selective ArrayList.
	 *
	 * Basically the Feed is an ArrayList of Auctions with limited functionality
	 * and some special functionality. It is a singleton, meaning there can only ever exist
	 * a single instance of itself at any given time.
	 *
	 * The Feed has the ability to:
	 *
	 *      Add one or add many Auctions using a Collection or Array
	 *      Clear its contents
	 *      Retrieve all of its contents as an ArrayList or Array
	 *      Copy all of its contents to a new instance of itself
	 *      Update itself with new contents of a Collection or Array
	 *          this would clear the Feed and add all of the Collection or Array
	 */
	
	//The current instance, may be null
	private static BHFeed instance;
	
	//The main implementation and data structure of Feed, a dynamic array of Auctions
	private ArrayList<Auction> arrayList;
	
	//The default size of the Feed, if one is never given, this is just initial size
	private final static int DEFAULT_SIZE = 50;
	
	//Private because it's a singleton
	private BHFeed(int size) {
		arrayList = new ArrayList<>(size);
	}
	
	/**
	 * Returns the instance of Feed.
	 *
	 * If one does not exist returns a new one with the default size
	 *
	 * @return the current instance of Feed if one exists, a new one otherwise
	 */
	public static BHFeed getInstance() {
		if (instance == null) {
			instance = new BHFeed(DEFAULT_SIZE);
		}
		return instance;
	}
	
	/**
	 * Returns the instance of Feed if its size is greater than the given size.
	 *
	 * If one does not exist or the current instance's size is less than
	 * the given size then returns a new one with the given size.
	 *
	 * @param size the desired initial size of the Feed
	 *
	 * @return the current instance of Feed if one exists and its size is greater than
	 * 		the given size, a new one with the given size otherwise
	 */
	public static BHFeed getInstanceWithSize(int size) {
		if (instance == null || instance.size() < size) {
			instance = new BHFeed(size);
		}
		return instance;
	}
	
	/**
	 * Returns a new instance of Feed with the default size.
	 *
	 * This would be used to delete the current instance and get new one
	 *
	 * @return a new instance of Feed with the default size
	 */
	public static BHFeed getNewInstance() {
		instance = new BHFeed(DEFAULT_SIZE);
		return instance;
	}
	
	/**
	 * Returns a new instance of Feed with the given size.
	 *
	 * This would be used to delete the current instance and get new one with the given size
	 *
	 * @param size the desired initial size of the new Feed
	 *
	 * @return a new instance of Feed with the given size
	 */
	public static BHFeed getNewInstanceWithSize(int size) {
		instance = new BHFeed(size);
		return instance;
	}
	
	/**
	 * Gets the size of the Feed.
	 *
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
	 */
	@Override
	public Iterator<Auction> iterator() {
		return arrayList.iterator();
	}
	
	/**
	 * Would be used when calling a for each loop on a Feed
	 *
	 * @param action the action to take on the Iterator
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
	 *
	 * Note that this does not delete the current instance, neither does it tamper with the
	 * instance's capacity for those cases, retrieve a new instance.
	 *
	 * @see BHFeed#getNewInstance()
	 * @see BHFeed#getNewInstanceWithSize(int)
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
	 *
	 * This should not be needed often since the Feed is dynamic.
	 *
	 * @return the new Feed instance with all the current instance's elements copied,
	 * 		its size would be the default size.
	 */
	public BHFeed copyToNewInstance() {
		if (instance == null) {
			return getNewInstance();
		} else {
			BHFeed local = new BHFeed(DEFAULT_SIZE);
			local.addAll(instance.getAllAsArrayList());
			instance = local;
			return instance;
		}
	}
	
	/**
	 * Copies all the contents of the current instance (if one exists) to a new instance with the given
	 * size.
	 *
	 * This may be used when a sudden growth is required.
	 *
	 * @param size the desired size of the new Feed instance
	 *
	 * @return the new Feed instance with all the current instance's elements copied,
	 * 		its size would be the given size.
	 */
	public BHFeed copyToNewInstanceWithSize(int size) {
		if (instance == null) {
			return getNewInstanceWithSize(size);
		} else {
			BHFeed local = new BHFeed(size);
			local.addAll(instance.getAllAsArrayList());
			instance = local;
			return instance;
		}
	}
	
	/**
	 * Updates the current Feed instance with the given Collection.
	 *
	 * Updating means clearing the instance's contents and filling them with the parameter.
	 *
	 * @param auctions the Collection of Auctions to fill the Feed with
	 */
	public void updateWith(Collection<Auction> auctions) {
		if (instance == null) {
			instance = getNewInstance();
			instance.addAll(auctions);
		} else {
			instance.clear();
			instance.addAll(auctions);
		}
		
	}
	
	/**
	 * Updates the current Feed instance with the given Array or varargs.
	 *
	 * Updating means clearing the instance's contents and filling them with the parameter.
	 *
	 * @param auctions the array or varargs Auctions to fill the Feed with
	 */
	public void updateWith(Auction... auctions) {
		if (instance == null) {
			instance = getNewInstance();
			instance.addAll(auctions);
		} else {
			instance.clear();
			instance.addAll(auctions);
		}
	}
	
	/**
	 * Updates the current Feed instance with the given Collection and a new size.
	 *
	 * If the current instance's size is less than the given size then a new instance is created.
	 *
	 * Updating means clearing the instance's contents and filling them with the parameter.
	 *
	 * @param size the desired size of the Feed
	 * @param auctions the Collection of Auctions to fill the Feed with
	 */
	public void updateWithSize(int size, Collection<Auction> auctions) {
		if (instance == null) {
			instance = getNewInstanceWithSize(size);
			instance.addAll(auctions);
		} else {
			instance.clear();
			instance = getInstanceWithSize(size);
			instance.addAll(auctions);
		}
	}
	
	/**
	 * Updates the current Feed instance with the given Array or varargs and a new size.
	 *
	 * If the current instance's size is less than the given size then a new instance is created.
	 *
	 * Updating means clearing the instance's contents and filling them with the parameter.
	 *
	 * @param size the desired size of the Feed
	 * @param auctions the Array or varargs Auctions to fill the Feed with
	 */
	public void updateWithSize(int size, Auction... auctions) {
		if (instance == null) {
			instance = getNewInstanceWithSize(size);
			instance.addAll(auctions);
		} else {
			instance.clear();
			instance = getNewInstanceWithSize(size);
			instance.addAll(auctions);
		}
	}
	
	// TODO: 29-Nov-17 hashCode equals and toString
	
}
