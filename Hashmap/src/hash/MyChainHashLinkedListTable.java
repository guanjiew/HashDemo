package hash;
import java.util.Iterator;
import java.util.LinkedList;

public class MyChainHashLinkedListTable<K extends Comparable<K>, V> implements HashStructure<K, V>, Iterable<K> {
	private int numElements;

	public int getNumElements() {
		return numElements;
	}

	public void setNumElements(int numElements) {
		this.numElements = numElements;
	}

	private int tableSize;

	public int getTableSize() {
		return tableSize;
	}

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	private double loadFactor;

	public double getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(double loadFactor) {
		this.loadFactor = loadFactor;
	}

	private LinkedList<HashElement<K, V>>[] hashList;

	/*
	 * Private inner class that holds a key-value pair In addition, the key is
	 * comparable
	 */
	private class HashElement<U extends Comparable<U>, Y> implements Comparable<HashElement<U, Y>> {
		public U key;
		public Y value;

		public HashElement(U key, Y value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(HashElement<U, Y> o) {
			return key.compareTo(o.key);
		}
	}

	/*
	 * Private inner class that implements a iterator looping keys
	 */

	private class KeyIterator<T> implements Iterator<T> {
		T[] keys;
		int pos;

		@SuppressWarnings("unchecked")
		public KeyIterator() {
			this.keys = (T[]) new Object[numElements];
			int p = 0;
			pos = 0;
			for (int i = 0; i < tableSize; i++) {
				for (HashElement<K, V> keyE : hashList[i]) {
					if (keyE.key != null) {
						this.keys[p++] = (T) keyE.key;
					}
				}
			}
		}

		@Override
		public boolean hasNext() {
			return pos < keys.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				return null;
			}
			return this.keys[pos++];
		}

	}


	@SuppressWarnings("unchecked")
	public MyChainHashLinkedListTable(int tableSize) {
		if (tableSize <= 0) {
			throw new NegativeArraySizeException();
		}
		this.loadFactor = 0.75;
		this.tableSize = tableSize;
		this.hashList = (LinkedList<HashElement<K, V>>[]) new LinkedList[this.tableSize];
		for (int i = 0; i < this.tableSize; i++) {
			hashList[i] = new LinkedList<HashElement<K, V>>();
		}
	}
	

	@Override
	public void rehash(int tableSize) {
		if (tableSize <= 0) {
			throw new NegativeArraySizeException();
		}
		int new_tableSize = tableSize;
		@SuppressWarnings("unchecked")
		LinkedList<HashElement<K, V>>[] new_harray = (LinkedList<HashElement<K, V>>[]) new LinkedList[new_tableSize];
		for (int i = 0; i < new_tableSize; i++) {
			new_harray[i] = new LinkedList<HashElement<K, V>>();
		}
		for (K key : this) {
			if (key != null) {
				V val = this.getValue(key);
				int hashval = (key.hashCode() & 0x7FFFFFFF) % new_tableSize;
				HashElement<K, V> hashe = new HashElement<K, V>(key, val);
				new_harray[hashval].add(hashe);
			}
		}

		this.hashList = new_harray;
		this.tableSize = new_tableSize;
	}

	@Override
	public void add(K key, V value) {
		if (this.loadFactor() > this.loadFactor) {
			rehash((int) (this.tableSize / this.loadFactor + 1));
			return;
		}
		int hashval = (key.hashCode() & 0x7FFFFFFF) % this.tableSize;
		HashElement<K, V> hashe = new HashElement<K, V>(key, value);
		this.hashList[hashval].add(hashe);
		this.numElements++;
	}

	@Override
	public void remove(K key) {
		int hashval = (key.hashCode() & 0x7FFFFFFF) % this.tableSize;
		HashElement<K, V> he = null;
		for (HashElement<K, V> hashe : this.hashList[hashval]) {
			if (key.compareTo(hashe.key) == 0) {
				he = hashe;
			}
		}
		if (he != null) {
			this.hashList[hashval].remove(he);
			this.numElements--;
		}

	}

	@Override
	public V getValue(K key) {
		int hashval = (key.hashCode() & 0x7FFFFFFF) % this.tableSize;
		for (HashElement<K, V> hashe : this.hashList[hashval]) {
			if (key.compareTo(hashe.key) == 0) {
				return hashe.value;
			}
		}
		return null;
	}

	@Override
	public double loadFactor() {
		return this.numElements / (double) this.tableSize;
	}

	@Override
	public Iterator<K> iterator() {
		return new KeyIterator<K>();
	}
}