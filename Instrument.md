# Introduction #

Each instantiation of this class represents a physical instrument and all of the attributes thereof. The [InstrumentList](InstrumentList.md) can utilize the information held in the Instrument object to save to a file, and later create an Instrument based on the saved file.

# Details #

This class, representing each instrument, is responsible for holding all the attributes of the instrument in one place.

These attributes include: name, brand, serial # (String), rank (int), value (int), status, notes, history, ligature, mouthpiece, mouthpiece cap, and bow.

The constructor takes all the attributes initially provided and stores them as private variables. The Instrument’s methods revolve around returning and changing these attributes (getSerialNumber, setSerialNumber, etc.). The other extremely important method in this class is the makeContract({name of student}), which creates the contract for the student to verify and sign in order to loan out the instrument (see sample contract for more detail).