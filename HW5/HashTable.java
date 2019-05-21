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
    public HashTable(Function<Key, Integer> initialHashFunction)
    {
        hashFunction = initialHashFunction;
        internalList = new ArrayList<Value>();

        for(int i = 0; i < 100; i++)
        {
            internalList.add(null);
        }
    }

    public void Add(Key key, Value v)
    {
        internalList.set(GetHash(key), v);
    }

    public Value Search(Key key)
    {
        return internalList.get(GetHash(key));
    }

    public Value SearchByHash(int i)
    {
        return internalList.get(i);
    }

    public void Delete(Key key)
    {
        internalList.set(GetHash(key), null);
    }

    public int GetHash(Key key)
    {
        return (hashFunction.apply(key) % 100);
    }
}