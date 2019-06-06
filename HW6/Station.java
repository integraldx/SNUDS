import java.util.HashSet;

class Station
{
    private final String stationName;
    private HashSet<String> stationNumbers = new HashSet<String>();
    private HashSet<String> lines = new HashSet<String>();

    public Station(final String stationName)
    {
        this.stationName = new String(stationName);
    }

    public String GetStationName()
    {
        return stationName;
    }

    public void AddStationNumber(String stationNumber, String lineNo)
    {
        stationNumbers.add(stationNumber);
        lines.add(lineNo);
    }

    public HashSet<String> GetStationNumberSet()
    {
        return stationNumbers;
    }
}