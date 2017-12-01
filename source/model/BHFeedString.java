package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public final class BHFeedString implements Iterable<String> {

	//there's a difference between instance and arrayList
	@Nullable
	private static BHFeedString instance;
	@NotNull
	private ArrayList<String> arrayList;
	private final static int DEFAULT_CAPACITY = 50;
	
	private BHFeedString(int capacity) {
		arrayList = new ArrayList<>(capacity);
	}

	public static BHFeedString getInstance() {
		if (instance == null) {
			instance = new BHFeedString(DEFAULT_CAPACITY);
		}
		return instance;
	}

	public static BHFeedString getNewInstance() {
		instance = new BHFeedString(DEFAULT_CAPACITY);
		return instance;
	}

	public static BHFeedString getNewInstanceWithCapacity(int capacity) {
		instance = new BHFeedString(capacity);
		return instance;
	}

	public int size() {
		return arrayList.size();
	}

	public boolean isEmpty() {
		return arrayList.isEmpty();
	}

	@Override
	public Iterator<String> iterator() {
		return arrayList.iterator();
	}

	@Override
	public void forEach(Consumer action) {
		arrayList.forEach(action);
	}

	public void add(String string) {
		arrayList.add(string);
	}

	public void addAll(String... strings) {
		arrayList.addAll(Arrays.asList(strings));
	}

	public void addAll(Collection<String> strings) {
		arrayList.addAll(strings);
	}

	public void clear() {
		arrayList.clear();
	}

	public ArrayList<String> getAllAsArrayList() {
		return arrayList;
	}

	public String[] getAllAsArray() {
		return arrayList.toArray(new String[arrayList.size()]);
	}
	
	public BHFeedString copyToNewInstance() {
		if (instance == null) {
			return getNewInstance();
		} else {
			BHFeedString local = new BHFeedString(DEFAULT_CAPACITY);
			local.addAll(instance.getAllAsArrayList());
			instance = local;
			return instance;
		}
	}

	public BHFeedString copyToNewInstanceWithCapacity(int capacity) {
		if (instance == null) {
			return getNewInstanceWithCapacity(capacity);
		} else {
			BHFeedString local = new BHFeedString(capacity);
			local.addAll(instance.getAllAsArrayList());
			instance = local;
			return instance;
		}
	}
	
	public BHFeedString updateWith(Collection<String> strings) {
		if (instance == null) {
			instance = new BHFeedString(DEFAULT_CAPACITY);
			instance.addAll(strings);
		} else {
			instance.clear();
			instance.addAll(strings);
		}
		return instance;
	}

	public BHFeedString updateWith(String... strings) {
		if (instance == null) {
			instance = new BHFeedString(DEFAULT_CAPACITY);
			instance.addAll(strings);
		} else {
			instance.clear();
			instance.addAll(strings);
		}
		return instance;
	}
	
	public BHFeedString updateWithCapacity(int capacity, Collection<String> strings) {
		if (instance == null) {
			instance = new BHFeedString(capacity);
			instance.addAll(strings);
		} else {
			instance = new BHFeedString(capacity);
			instance.addAll(strings);
		}
		return instance;
	}
	
	public BHFeedString updateWithCapacity(int capacity, String... strings) {
		if (instance == null) {
			instance = new BHFeedString(capacity);
			instance.addAll(strings);
		} else {
			instance = new BHFeedString(capacity);
			instance.addAll(strings);
		}
		return instance;
	}
	
	public void destroyInstance() {
		instance = null;
	}
	
	@Override
	public int hashCode() {
		return arrayList.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Collection) && (obj.equals(arrayList));
	}
	
	//implement this later!!!
	@Override
	public String toString() {
		return super.toString();
	}
	
	//hashCode equals and toString

}

