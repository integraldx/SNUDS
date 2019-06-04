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

        s.close();
        // File input done

        TrainMap map = new TrainMap(links);

        // Get user input
        s = new Scanner(System.in);
        HandleUserInput(s, map, stationMap);
        return;
    }

    private static HashMap<String, Station> GetStations(Scanner s)
    {
        String stringLine;
        HashMap<String, Station> map = new HashMap<String, Station>();

        while (true)
        {
            stringLine = s.nextLine();
            var l = stringLine.split(" ");
            if (l.length != 3)
            {
                break;
            }
            Station t;
            if (null == (t = map.get(l[0])))
            {
                map.putIfAbsent(l[0], new Station(l[1]));
            }
            t = map.get(l[0]);

            t.AddLine(l[2]);
        }

        return map;
    }

    private static LinkedList<Link> GetEdges(Scanner s, HashMap<String, Station> stationInfo)
    {
        String stringLine;
        LinkedList<Link> list = new LinkedList<Link>();

        while (true)
        {
            if (s.hasNextLine())
            {
                stringLine = s.nextLine();
            }
            else
            {
                break;
            }
            var l = stringLine.split(" ");
            if (l.length != 3)
            {
                break;
            }

            Station from = stationInfo.get(l[0]);
            Station to = stationInfo.get(l[1]);
            String line = null;
            for(var i : from.GetLineList())
            {
                if (to.IsOnLine(i))
                {
                    line = i;
                    break;
                }
            }
            if (line != null)
            {
                list.add(new Link(l[0], l[1], line, Integer.parseInt(l[2])));
            }
            else
            {
                throw new InvalidParameterException();
            }
        }

        return list;
    }

    private static void HandleUserInput(Scanner s, TrainMap map, HashMap<String, Station> stationInfo)
    {
        String line;
        HashMap<String, String> reverseMap = new HashMap<String, String>();
        for (var i : stationInfo.entrySet())
        {
            reverseMap.put(i.getValue().GetStationName(), i.getKey());
        }
        while (true)
        {
            line = s.nextLine();
            if (line.equals("QUIT"))
            {
                break;
            }
            var from = reverseMap.get(line.split(" ")[0]);
            var to = reverseMap.get(line.split(" ")[1]);

            LinkedList<String> ll = new LinkedList<String>();
            int timeCost = map.FindPath(from, to, ll);
            StringBuilder sb = new StringBuilder();
            // for (String i : ll)
            // {
            //     if (i.startsWith("*"))
            //     {
            //         sb.append("[" + stationInfo.get(i) + "] ");
            //     }
            //     else
            //     {
            //         sb.append(stationInfo.get(i) + " ");
            //     }
            // }
            // sb.deleteCharAt(sb.length() - 1);
            sb.append("" + timeCost + "\n");

            System.out.print(sb.toString());
        }
    }
}