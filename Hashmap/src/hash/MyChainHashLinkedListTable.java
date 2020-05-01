package hash;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hash.MyChainHashLinkedListTable.HashElement;

public class MyChainHashLinkedListTable<T extends Comparable<T>,V> implements HashStructure<T,V>, Iterable<T> {
	public int numElements;
	public int tableSize;
	public double loadFactor;
	public LinkedList<HashElement<T,V>>[] hashList;
	class HashElement<T extends Comparable<T>,V> implements Comparable<HashElement<T,V>> {
		public T key;
		public V value;
		public HashElement(T key, V value) {
			this.key = key;
			this.value = value;
		}
		@Override
		public int compareTo(HashElement<T , V> o) {
			return key.compareTo(o.key);
		}
	} 
	
	private class IteratorHelper<K> implements Iterator<K>{
		K [] keys;
		int pos;
		public IteratorHelper() {
			this.keys = (K[]) new Object[numElements];
			int p = 0;
			for (int i = 0; i < tableSize; i++) {
				LinkedList<HashElement<T, V>> list = hashList[i];
				for (HashElement<T, V> h : list ) {
					if (h.key!=null) {
 					this.keys[p++]= (K) h.key;
 					}
				}
			}
			pos = 0;
		}
		

		@Override
		public boolean hasNext() {
			return pos < keys.length;
		}

		@Override
		public K next() {

			if(!hasNext()) {
				return null;
			}
			return this.keys[pos++];
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public MyChainHashLinkedListTable(int tableSize) {
		this.loadFactor = 0.75;
		this.tableSize = tableSize;
		this.hashList = (LinkedList<HashElement<T,V>>[])new LinkedList[this.tableSize];
		for (int i = 0; i < this.tableSize; i ++) {
			hashList[i] = new LinkedList<HashElement<T,V>>();
		}
		
	}

	@Override
	public void rehash(int tableSize) {
		int new_tableSize = tableSize;
		LinkedList<HashElement<T,V>>[] new_harray =(LinkedList<HashElement<T,V>>[]) new LinkedList[new_tableSize];
		for(int i = 0; i < new_tableSize; i ++) {
			new_harray[i] = new LinkedList<HashElement<T, V>>();
		}
		for (T key: this) {
//			System.out.println(key);
			if(key != null) {
			V val = this.getValue(key);
			int hashval = (key.hashCode() & 0x7FFFFFFF) % new_tableSize;
			HashElement<T,V> hashe = new HashElement<T, V>(key,val);
			new_harray[hashval].add(hashe);
//			System.out.println("ele"+ new_harray.length);
			}
		}

			
		this.hashList = new_harray; 
		this.tableSize = new_tableSize;
		
		
	}

	@Override
	public void add(T key, V value) {
		if (this.loadFactor() > this.loadFactor) {
			rehash((int) (this.tableSize / this.loadFactor + 1));
			return;
		}
		int hashval = (key.hashCode() & 0x7FFFFFFF) % this.tableSize;
		HashElement<T,V> hashe = new HashElement<T, V>(key,value);
		this.hashList[hashval].add(hashe);
		this.numElements ++;
	}

	@Override
	public void remove(T key) {
		int hashval = (key.hashCode() & 0x7FFFFFFF) % this.tableSize;
		HashElement<T, V> he = null;
		for (HashElement<T, V> hashe: this.hashList[hashval]) {
			if(key.compareTo(hashe.key)== 0) {
				he = hashe;
			}
		}
		if (he != null) {
		this.hashList[hashval].remove(he);
		this.numElements --;
		}
		
	}

	@Override
	public V getValue(T key) {
		int hashval = (key.hashCode() & 0x7FFFFFFF) % this.tableSize;
//	    System.out.println("ele"+ this.hashList.length);
		for (HashElement<T, V> hashe: this.hashList[hashval]) {
			if(key.compareTo(hashe.key)== 0) {
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
	public Iterator<T> iterator() {
		return new IteratorHelper<T>();
	}
}
