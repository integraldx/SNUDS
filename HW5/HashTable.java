import java.util.function.Function;
import java.util.*;

/**
 * <h1>HashTable</h1> This is hash table class made for DS Homework "Matching".
 * Implemented with generic functionality Used in StringMatcher
 * 
 * @author : integraldx
 * @since : 2019-05-17
 */

public class HashTable<Key, Value>
{
    Function<Key, Integer> hashFunction;
    ArrayList<Value> internalList;

    /**
     * Makes new hashtable.
     * @param initialHashFunction : Hash function used to hash key into integer
     */
    public HashTable(Function<Key, Integer> initialHashFunction, int tableLength)
    {
        hashFunction = initialHashFunction;
        internalList = new ArrayList<Value>();

        for(int i = 0; i < tableLength; i++)
        {
            internalList.add(null);
        }
    }

    /**
     * Assign given value to given key.
     * If already exists, it overwrites.
     * @param key
     * @param v
     */
    public void Insert(Key key, Value v)
    {
        internalList.set(GetHash(key), v);
    }

    /**
     * Get value associated with given key
     * @param key
     * @return : search result
     */
    public Value Search(Key key)
    {
        return internalList.get(GetHash(key));
    }

    /**
     * Get value associated with given hash
     * @param i : hash value
     * @return : search result
     */
    public Value SearchByHash(int i)
    {
        return internalList.get(i);
    }

    /**
     * Fills null at given key.
     * @param key
     */
    public void Delete(Key key)
    {
        internalList.set(GetHash(key), null);
    }

    /**
     * Get hash value from given key.
     * @param key
     * @return
     */
    private int GetHash(Key key)
    {
        return hashFunction.apply(key);
    }
}