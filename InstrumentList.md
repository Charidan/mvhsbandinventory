# Introduction #

Contains Instrument classes which store their own status. Can reference and search among the Instruments to find desired specific objects. Can use instrument objects to save and load to disk.

# Details #

This class is responsible for creating, sorting, and maintaining the list of instruments in the application. The constructor finds all the .txt files of the instruments and uses them to create the instrument objects viewed in the application. Instrument List’s methods include:

  * the add({[Instrument](Instrument.md) attribute}) method, which creates a new instrument file and object.
  * the sort({attribute name}) method, which sorts the list of instruments in accordance to each instrument’s {attribute name}.
  * the delete([Instrument](Instrument.md)) method, which removes instrument from database (warnings preventing accidental deletion of inventory are prudent).
  * the selectList({attribute name}, attribute) method, which searches through all the instruments with {attribute name} returns a list of instruments that have attribute.
  * the exportToExcel({list}) method, which exports the list to Excel