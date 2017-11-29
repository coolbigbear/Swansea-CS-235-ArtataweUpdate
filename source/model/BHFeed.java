package model;

public final class BHFeed{
	
	
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
	
	
	//constructor (probably not needed, this will be a singleton)
	//size
	//isEmpty
	//iterator (used by the view)
	//add (used for adding elements one by one could make an addIterator)
	//clear (empty itself)
	//copy (copy itself to another thing, not very useful)
	//update (used to update itself)
	
	
}
