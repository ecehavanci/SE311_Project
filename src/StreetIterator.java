
//-----------------------ITERATOR - OBSERVER-------------------------

import java.util.ArrayList;

import java.util.ArrayList;

//Abstract Iterator
interface Iterator {

    //First element in the list
    void First();

    //Allows us to move to the next elements in the list
    void Next();

    //Checks that all list items are all traversed.
    Boolean IsDone();

    //It gives the information on which element we are on while traversing the list.
    TrashBin CurrentTrashBin();
}

//Concrete Iterator
//Implements the Iterator interface.
//Keeps track of the current position in the traversal.
public class StreetIterator implements Iterator {
    private Street street;
    private int current;

    public void First() {
        current = 0;
    }

    public void Next() {
        current++;
    }

    public TrashBin CurrentTrashBin() {
        return (IsDone() ? null : street.get(current));
    }

    public Boolean IsDone() {
        return current >= street.getCount();
    }

    public StreetIterator(Street street) {
        this.street = street;
        current = 0;
    }
}


//ITERATOR: Item --------- SUBJECT: TrashBin
abstract class TrashBin {
    //Every trash bin knows about the Waste Collection Department they are going to notify when bin is 80% full
    WasteCollectionDepartment WCD;
    //Although in observer pattern there can be many observers, in this project every trash bin has only one sensor.
    //So there is a list of observers but only one observer can be attached to the trash bin
    ArrayList<Sensor> sensors = new ArrayList<>();
    protected double nonRecyclableWasteLevel;
    protected double recyclableWasteLevel;

    //Token is for identifying if it is general or medical later
    protected String token;


    public TrashBin(WasteCollectionDepartment WCD) {
        this.WCD = WCD;
    }

    //Only the first attached sensor is accepted
    void Attach(Sensor sensor) {
        System.out.println("Attaching sensor...");
        if (this.sensors.size() == 0) {
            sensors.add(sensor);
        } else {
            System.out.println("This trash bin already has a sensor attached.");
        }
    }

    void Detach() {
        //Basically this will get rid of the list with one element in it and create another list with size 0
        if (sensors.size() == 0) {
            sensors = new ArrayList<>();
        } else {
            System.out.println("There is no sensor attached, so nothing can be detached.");
        }
    }

    public void Notify(char token) {
        if (sensors.size() > 0) {
            if (token == 'M') {

                sensors.get(0).M_Update(this);

            } else if (token == 'G') {

                sensors.get(0).G_Update(this);
            }
        } else {
            System.out.println("Problem! No sensor is attached to trash bin! Cannot send update to Waste Collection Department!");
        }
    }

    public void resetFullnessLevel() {
        recyclableWasteLevel = 0;
        nonRecyclableWasteLevel = 0;
    }

    public double getFullnessLevel() {
        return (recyclableWasteLevel + nonRecyclableWasteLevel);
    }

    public String getToken() {
        return token;
    }

    public abstract void AddTrash(double recyclableTrashAmount, double nonRecyclableTrashAmount);

    public double getNonRecyclableWasteLevel() {
        return nonRecyclableWasteLevel;
    }

    public double getRecyclableWasteLevel() {
        return recyclableWasteLevel;
    }
}


interface Aggregate{
    public Iterator CreateIterator();
    public void add(TrashBin trashBin);
    public int getCount ();
    public TrashBin get(int idx);
}

//-------------------------COMMON AREA----------------------------

//ITERATOR: Concrete Aggregate
//Implements the Iterator creation interface to return an instance of the proper ConcreteIterator.
// ------- COMPOSITE: Leaf
class Street implements LocatingElement,Aggregate {
    private String name;

    public Street(String name) {
        this.name = name;
    }

    //Stores a list of TrashBins
    private ArrayList<TrashBin> trashBins = new ArrayList<>();

    public TrashBin GetTrashBin(int index) {
        return trashBins.get(index);
    }

    public StreetIterator CreateIterator() {
        return new StreetIterator(this);
    }


    public int getCount() {
        return trashBins.size();
    }

    //Adds a new TrashBin to the list
    public void add(TrashBin trashBin) {
        trashBins.add(trashBin);
    }

    //Gets the TrashBin in the given index
    public TrashBin get(int index) {
        return trashBins.get(index);
    }


    //Since this is the leaf element nothing can be added
    @Override
    public void Add(LocatingElement locatingElement) {
        System.out.println("You can't add.");
    }

    //Since this is the leaf element nothing can be removed
    @Override
    public void Remove(LocatingElement locatingElement) {
        System.out.println("You can't remove.");
    }

    //This function is for displaying leaf (the smallest element of hierarchy)
    @Override
    public void Display(int indent) {
        for (int i = 0; i < indent + 1; i++) {
            System.out.print("-");

        }
        System.out.println(name);
    }

    //Traverse function is for emptying bins by calling another function that collects the waste in TrashBins iteratively
    @Override
    public void Traverse(TruckDriver truckDriver, String type) {
        if (type.equals("M")) {
            truckDriver.EmptyFullMedicalBins(this);
        }
        if (type.equals("G")) {
            truckDriver.EmptyFullGeneralBins(this);
        }
    }

    @Override
    public WasteCollectionDepartment getWCD() {
        System.out.println("Streets do not have a waste collection department");
        return null;
    }

    @Override
    public MedicalLandfill getMedicalLandfill() throws LandfillNotFoundException {
        throw new LandfillNotFoundException();
    }

    @Override
    public GeneralLandfill[] getGeneralLandfills() throws LandfillNotFoundException {
        throw new LandfillNotFoundException();
    }
}