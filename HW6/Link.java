/**
 * <h1>Link</h1>
 * 
 * @author : Integraldx
 * @since : 19/06/04
 */

class Link
{
    private String startStation;
    private String endStation;
    private int minute;

    public Link(String startStation, String endStation, int minute)
    {
        this.startStation = startStation;
        this.endStation = endStation; 
        this.minute = minute;
    }

    public String GetFrom()
    {
        return startStation;
    }

    public String GetTo()
    {
        return endStation;
    }

    public int GetTime()
    {
        return minute;
    }
}