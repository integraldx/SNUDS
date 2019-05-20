import java.util.function.Function;

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
    LinkedList<Value> internalList;
    public HashTable(Function<Key, Integer> initialHashFunction)
    {
        hashFunction = initialHashFunction;
        internalList = new LinkedList<Value>();

        for(int i = 0; i < 100; i++)
        {
            internalList.Add(null);
        }
    }

    public void Add(Key key, Value v)
    {
        internalList.SetElementAt(GetHash(key), v);
    }

    public Value Search(Key key)
    {
        return internalList.At(GetHash(key));
    }

    public Value SearchByHash(int i)
    {
        return internalList.At(i);
    }

    public void Delete(Key key)
    {
        internalList.SetElementAt(GetHash(key), null);
    }

    public int GetHash(Key key)
    {
        return (hashFunction.apply(key) % 100);
    }
}