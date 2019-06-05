import java.util.LinkedList;

class Station
{
    private final String stationName;
    private LinkedList<String> stationNumbers = new LinkedList<String>();
    private LinkedList<String> lines = new LinkedList<String>();

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

    public LinkedList<String> GetStationNumberList()
    {
        return stationNumbers;
    }
}