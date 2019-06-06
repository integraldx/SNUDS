import java.security.InvalidParameterException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <h1> Subway </h1>
 * This is program for DS HW6 "Subway"
 * @author : Integraldx
 * @since : 19/06/04
 */
class Subway
{
    /**
     * Main method
     * @param args : execution param
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("Usage : java Subway [data]");
            return;
        }
        Scanner s;
        try
        {
            s = new Scanner(new File(args[0]));
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Cannot open file!");
            return;
        }

        var stationMap = GetStations(s);
        var links = GetEdges(s, stationMap);

        // File input done
        s.close();

        TrainMap map = new TrainMap(links);

        var revMap = MakeStationReverseMap(stationMap);

        // Get user input
        s = new Scanner(System.in);
        HandleUserInput(s, map, stationMap, revMap);
        return;
    }

    private static HashMap<String, Station> GetStations(Scanner s)
    {
        String stringLine;
        HashMap<String, Station> map = new HashMap<String, Station>();

        while (s.hasNextLine())
        {
            stringLine = s.nextLine();
            var l = stringLine.split(" ");
            if (l.length != 3)
            {
                break;
            }

            if (!map.containsKey(l[1]))
            {
                map.put(l[1], new Station(l[1]));
            }
            Station t = map.get(l[1]);

            t.AddStationNumber(l[0], l[2]);
        }

        return map;
    }

    private static LinkedList<Link> GetEdges(Scanner s, HashMap<String, Station> stationInfo)
    {
        String stringLine;
        LinkedList<Link> list = new LinkedList<Link>();

        while (s.hasNextLine())
        {
            stringLine = s.nextLine();
            var l = stringLine.split(" ");
            if (l.length != 3)
            {
                break;
            }

            String from = l[0];
            String to = l[1];
            list.add(new Link(from, to, Integer.parseInt(l[2])));
        }

        for (var i : stationInfo.values())
        {
            if (i.GetStationNumberSet().size() > 1)
            {
                var noList = i.GetStationNumberSet();
                for (var j : noList)
                {
                    for (var k : noList)
                    {
                        if (!j.equals(k))
                        {
                            list.add(new Link(j, k, 5, true));
                            list.add(new Link(k, j, 5, true));
                        }
                    }
                }
            }
        }

        return list;
    }

    private static HashMap<String, String> MakeStationReverseMap(HashMap<String, Station> m)
    {
        var result = new HashMap<String, String>();
        for (var i : m.entrySet())
        {
            for (var j : i.getValue().GetStationNumberSet())
            {
                result.put(j, i.getValue().GetStationName());
            }
        }

        return result;
    }

    private static void HandleUserInput(Scanner s, TrainMap map, HashMap<String, Station> stationInfo, HashMap<String, String> translationInfo)
    {
        String line;
        while (true)
        {
            line = s.nextLine();
            if (line.equals("QUIT"))
            {
                break;
            }
            var from = stationInfo.get(line.split(" ")[0]);
            var to = stationInfo.get(line.split(" ")[1]);


            LinkedList<String> ll = new LinkedList<String>();
            long timeCost = Long.MAX_VALUE;
            for (var j : to.GetStationNumberSet())
            {
                var tempLL = new LinkedList<String>();
                long temp = map.FindPath(from.GetStationNumberSet(), j, tempLL);

                if (temp < timeCost)
                {
                    timeCost = temp;
                    ll = tempLL;
                }
            }
            StringBuilder sb = new StringBuilder();
            String prev = null;
            boolean skip = false;
            for (String i : ll)
            {
                boolean ts = skip;
                if (prev != null)
                {
                    if (translationInfo.get(prev).equals(translationInfo.get(i)))
                    {
                        skip = true;
                        ts = true;
                        sb.append("[" + translationInfo.get(prev) + "] ");
                    }
                    else
                    {
                        skip = false;
                    }

                    if (!ts)
                    {
                        sb.append(translationInfo.get(prev) + " ");
                    }
                }
                prev = i;
            }
            sb.append(translationInfo.get(prev) + " ");
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n" + timeCost + "\n");

            System.out.print(sb.toString());
        }
    }
}