package mvhsbandinventory;

/**
 *
 * @author nicholson
 */
public class Instrument
{
    private String name;
    private String brand;
    private String serial;
    private int rank;
    private int value;
    private String status;
    private String notes;
    private String history;
    private String ligature;
    private String mpiece;
    private String cap;
    private String bow;

    public Instrument(String name, String brand, String serial, int rank,
            int value, String status, String notes, String history,
            String ligature, String mouthpiece, String cap, String bow)
    {
        this.name = name;
        this.brand = brand;
        this.serial = serial;
        this.rank = rank;
        this.value = value;
        this.status = status;
        this.notes = notes;
        this.history = history;
        this.ligature = ligature;
        this.mpiece = mouthpiece;
        this.cap = cap;
        this.bow = bow;
    }

    public String getName()
    {
        return name;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getSerial()
    {
        return serial;
    }

    public int getRank()
    {
        return rank;
    }

    public int getValue()
    {
        return value;
    }

    public String getStatus()
    {
        return status;
    }

    public String getNotes()
    {
        return notes;
    }

    public String getHistory()
    {
        return history;
    }

    public String getLigature()
    {
        return ligature;
    }

    public String getMouthpiece()
    {
        return mpiece;
    }

    public String getCap()
    {
        return cap;
    }

    public String getBow()
    {
        return bow;
    }

    void setName(String s)
    {
        name = s;
    }

    void setBrand(String s)
    {
        brand = s;
    }

    void setSerial(String s)
    {
        serial = s;
    }

    void setRank(int x)
    {
        rank = x;
    }

    void setValue(int x)
    {
        value = x;
    }

    void setStatus(String s)
    {
        status = s;
    }

    void setNotes(String s)
    {
        notes = s;
    }

    void setHistory(String s)
    {
        history = s;
    }

    void setLigature(String s)
    {
        ligature = s;
    }

    void setMouthpiece(String s)
    {
        mpiece = s;
    }

    void setCap(String s)
    {
        cap = s;
    }

    void setBow(String s)
    {
        bow = s;
    }

    @Override
    public String toString()
    {
       return name+","+brand+","+serial+","+rank+","+value+","+status+","+notes+
               ","+history+","+ligature+","+mpiece+","+cap+","+bow;
    }
}
