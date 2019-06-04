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
        public final int p;

        public DijkPair(String s, int p)
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
    private HashMap<String, LinkedList<Link>> mapByLineNumber = new HashMap<String, LinkedList<Link>>();

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
                mapByStart.put(l.GetFrom(), start = new LinkedList<Link>());
            }
            start.add(l);

            var end = mapByEnd.get(l.GetFrom());
            if (end == null)
            {
                mapByEnd.put(l.GetTo(), end = new LinkedList<Link>());
            }
            end.add(l);

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
        HashMap<String, Integer> costMap = new HashMap<String, Integer>();
        Set<String> visitedSet = new HashSet<String>();
        PriorityQueue<DijkPair> pQueue = new PriorityQueue<DijkPair>(10, new DijkComparator());
        costMap.put(from, 0);
        pQueue.add(new DijkPair(from, 0));
        while (!pQueue.isEmpty())
        {
            var current = pQueue.poll();
            if (visitedSet.contains(current.s))
            {
                continue;
            }
            else
            {
                visitedSet.add(current.s);
                for (var i : mapByStart.get(current.s))
                {
                    if (!costMap.containsKey(i.GetTo()))
                    {
                        costMap.put(i.GetTo(), Integer.MAX_VALUE);
                    }

                    if (costMap.get(i.GetTo()).intValue() > costMap.get(current.s) + i.GetTime())
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

        // System.err.println(costMap.get(from));
        // System.err.println(costMap.get(to));

        return costMap.get(to);
    }
}