import java.util.HashSet;

class Station
{
    private final String stationName;
    private HashSet<String> lineNumbers;

    public Station(final String stationName)
    {
        this.stationName = new String(stationName);
        lineNumbers = new HashSet<String>();
    }

    public String GetStationName()
    {
        return stationName;
    }

    public boolean IsOnLine(final String line)
    {
        return lineNumbers.contains(line);
    }

    public void AddLine(final String line)
    {
        lineNumbers.add(line);
    }

    public HashSet<String> GetLineList()
    {
        return new HashSet<String>(lineNumbers);
    }
}