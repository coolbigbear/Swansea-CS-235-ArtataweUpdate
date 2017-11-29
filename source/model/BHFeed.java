package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public final class BHFeed implements Iterable<Auction> {
	
	
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
	 *      Copy all of its contents to a new instance
	 *      Update itself with new contents of a Collection or Array
	 *          this would clear the Feed and add all of the Collection or Array
	 */
	
	private static BHFeed instance;
	private ArrayList<Auction> arrayList;
	private final static int DEFAULT_SIZE = 50;
	
	private BHFeed(int size) {
		arrayList = new ArrayList<>(size);
	}
	
	public static BHFeed getInstance() {
		if (instance == null) {
			instance = new BHFeed(DEFAULT_SIZE);
		}
		return instance;
	}
	
	public static BHFeed getInstanceWithSize(int size) {
		if (instance == null || instance.size() < size) {
			instance = new BHFeed(size);
		}
		return instance;
	}
	
	public static BHFeed getNewInstance() {
		instance = new BHFeed(DEFAULT_SIZE);
		return instance;
	}
	
	public static BHFeed getNewInstanceWithSize(int size) {
		instance = new BHFeed(size);
		return instance;
	}
	
	public int size() {
		return arrayList.size();
	}
	
	public boolean isEmpty() {
		return arrayList.isEmpty();
	}
	
	@Override
	public Iterator<Auction> iterator() {
		return arrayList.iterator();
	}
	
	@Override
	public void forEach(Consumer action) {
		arrayList.forEach(action);
	}
	
	public void add(Auction auction) {
		arrayList.add(auction);
	}
	
	public void addAll(Auction... auctions) {
		arrayList.addAll(Arrays.asList(auctions));
	}
	
	public void addAll(Collection<Auction> auctions) {
		arrayList.addAll(auctions);
	}
	
	public void clear() {
		arrayList.clear();
	}
	
	public ArrayList<Auction> getAllAsArrayList() {
		return arrayList;
	}
	
	public Auction[] getAllAsArray() {
		return arrayList.toArray(new Auction[arrayList.size()]);
	}
	
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
	
	public void updateWith(Collection<Auction> auctions) {
		instance.clear();
		instance.addAll(auctions);
	}
	
	public void updateWith(Auction... auctions) {
		instance.clear();
		instance.addAll(auctions);
	}
	
	public void updateWithSize(int size, Collection<Auction> auctions) {
		instance.clear();
		instance = getInstanceWithSize(size);
		instance.addAll(auctions);
	}
	
	public void updateWithSize(int size, Auction... auctions) {
		instance.clear();
		instance = getNewInstanceWithSize(size);
		instance.addAll(auctions);
	}
	
	//constructor (probably not needed, this will be a singleton)
	//size
	//isEmpty
	//iterator (used by the view)
	//add (used for adding elements one by one could make an addIterator)
	//clear (empty itself)
	//copy (copy itself to another thing, not very useful or needed)
	//update (used to update itself)
	
	
}
