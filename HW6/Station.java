
class Station
{
    private final String stationName;
    private String line;

    public Station(final String stationName, final String line)
    {
        this.stationName = new String(stationName);
        this.line = new String(line);
    }

    public String GetStationName()
    {
        return stationName;
    }

    public String GetLine()
    {
        return line;
    }
}