/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvhsbandinventory;

/**
 *
 * @author Charidan
 */
class InstrumentLock
{
    int lockType;
    Instrument instru;

    public InstrumentLock(int lockType, Instrument toLock)
    {
        this.lockType = lockType;
        instru = toLock;
    }

    public int getLockType()
    {
        return lockType;
    }

    public Instrument getInstrument()
    {
        return instru;
    }
}
