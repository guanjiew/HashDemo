package hash;

/**
 * @author Guanjie Wang
 *
 */
public interface HashStructure<T,V> {
	
	/**
	 * Rehash the table when reload when then load factor is greater than the defined
	 * value.
	 */
	void rehash(int tableSize);
	
	/**
	 * Add an element into the hash table
	 */
	void add(T key, V value);
	
	/**
	 * Move an element from the hash table
	 */
	void remove(T key);
	
	/**
	 * Get the value from the hash table based on the key 
	 */
	V getValue(T key);	
	
	/**
	 * Calculate the current load factor
	 */
	double loadFactor();
	
}
