import java.util.*;
/**
 * <h1>TrainMap</h1>
 * @author : Integraldx
 * @since : 19/06/04
 */

class TrainMap
{
    class DijkPair
    {
        public final String s;
        public final long p;

        public DijkPair(String s, long p)
        {
            this.s = s;
            this.p = p;
        }
    }

    class DijkComparator implements Comparator<DijkPair>
    {
        public int compare(DijkPair left, DijkPair right)
        {
            if (left.p < right.p)
            {
                return -1;
            }
            else if (left.p == right.p)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
    }

    private HashMap<String, LinkedList<Link>> mapByStart = new HashMap<String, LinkedList<Link>>();
    private HashMap<String, LinkedList<Link>> mapByEnd = new HashMap<String, LinkedList<Link>>();

    /**
     * Initializes new map
     * @param linkArray : list of 
     */
    public TrainMap(List<Link> linkArray)
    {
        for (var l : linkArray)
        {
            var start = mapByStart.get(l.GetFrom());
            if (start == null)
            {
                start = new LinkedList<Link>();
                mapByStart.put(l.GetFrom(), start);
            }
            start.add(l);

            var end = mapByEnd.get(l.GetTo());
            if (end == null)
            {
                end = new LinkedList<Link>();
                mapByEnd.put(l.GetTo(),end);
            }
            end.add(l);
        }
    }

    public long FindPath(HashSet<String> from, String to, LinkedList<String> ll)
    {
        HashMap<String, Long> costMap = new HashMap<String, Long>();
        Set<String> visitedSet = new HashSet<String>();
        PriorityQueue<DijkPair> pQueue = new PriorityQueue<DijkPair>(10, new DijkComparator());
        costMap.put((String)from.toArray()[0], Long.parseLong("0"));
        pQueue.add(new DijkPair((String)from.toArray()[0], 0));
        while (!pQueue.isEmpty())
        {
            var current = pQueue.poll();
            if (visitedSet.contains(current.s))
            {
                continue;
            }
            else
            {
                if (mapByStart.get(current.s) == null)
                {
                    continue;
                }
                visitedSet.add(current.s);
                for (var i : mapByStart.get(current.s))
                {
                    if (!costMap.containsKey(i.GetTo()))
                    {
                        costMap.put(i.GetTo(), Long.MAX_VALUE);
                    }

                    if (i.isTransfer() && from.contains(i.GetTo()))
                    {
                        costMap.put(i.GetTo(), costMap.get(current.s));
                        pQueue.add(new DijkPair(i.GetTo(), costMap.get(i.GetTo())));
                    }
                    if (costMap.get(i.GetTo()).longValue() > costMap.get(current.s) + i.GetTime())
                    {
                        costMap.put(i.GetTo(), costMap.get(current.s) + i.GetTime());
                        pQueue.add(new DijkPair(i.GetTo(), costMap.get(i.GetTo())));
                    }
                }
            }
            if (current.s.equals(to))
            {
                break;
            }
        }

        var path = EvaluatePath(from, to, costMap);
        for (var i : path)
        {
            ll.push(i);
        }

        return costMap.get(to);
    }

    LinkedList<String> EvaluatePath(HashSet<String> fromSet, String to, HashMap<String, Long> costMap)
    {
        LinkedList<String> ll = null;

        for (var i : fromSet)
        {
            if (i.equals(to))
            {
                ll = new LinkedList<String>();
                ll.push(to);
                return ll;
            }
        }

        var cost = costMap.get(to);

        for (var l : mapByEnd.get(to))
        {
            if (costMap.containsKey(l.GetFrom()))
            {
                if (cost.longValue() == costMap.get(l.GetFrom()) + l.GetTime())
                {
                    ll = EvaluatePath(fromSet, l.GetFrom(), costMap);
                }
            }

            if (ll != null)
            {
                break;
            }
        }

        if (ll != null)
        {
            ll.push(to);
        }
        return ll;
    }
}