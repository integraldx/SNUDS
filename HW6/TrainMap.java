import java.util.*;
/**
 * <h1>TrainMap</h1>
 * @author : Integraldx
 * @since : 19/06/04
 */

class TrainMap
{
    private HashMap<String, Link> mapByStart;
    private HashMap<String, Link> mapByEnd;
    private HashMap<String, LinkedList<Link>> mapByLineNumber;

    /**
     * Initializes new map
     * @param linkArray : list of 
     */
    public TrainMap(List<Link> linkArray)
    {
        for (var l : linkArray)
        {
            mapByStart.putIfAbsent(l.GetFrom(), l);
            mapByEnd.putIfAbsent(l.GetTo(), l);
            var line = mapByLineNumber.get(l.GetLine());
            if (line == null)
            {
                mapByLineNumber.put(l.GetLine(), line = new LinkedList<Link>());
            }
            line.add(l);
        }
    }

    public int FindPath(String from, String to, LinkedList<String> ll)
    {
        return Integer.MAX_VALUE;
    }
}