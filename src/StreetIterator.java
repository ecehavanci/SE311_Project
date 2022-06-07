
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
    //Although in observer pattern there can be many observers, in this project every thrash bin has only one sensor.
    //So there is a list of observers but only one observer can be attached to the trash bin
    ArrayList<Sensor> sensors = new ArrayList<>();
    protected double nonRecyclableWasteLevel;
    protected double recyclableWasteLevel;

    //Token is for identifying if it is general or medical later
    protected String token;


    public TrashBin(WasteCollectionDepartment WCD) {
        this.WCD = WCD;
    }

    void Attach(Sensor sensor) {
        System.out.println("Attaching sensor...");
        if (this.sensors.size() == 0) {
            sensors.add(sensor);
        } else {
            System.out.println("This trash bin already has a sensor attached.");
        }
    }

    void Detach() {
        //Basically this will get rid of the list with posibly one element in it and create another list with size 0
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

//ConcreteSubject 1
class MedicalTrashBin extends TrashBin {
    public MedicalTrashBin(WasteCollectionDepartment WCD) {
        super(WCD);
        token = "M";
    }

    public void AddTrash(double recyclableTrashAmount, double nonRecyclableTrashAmount) {
        if (recyclableTrashAmount + nonRecyclableTrashAmount > 100) {
            System.out.println("Invalid amount of thrash");
        } else {
            if (recyclableTrashAmount + nonRecyclableTrashAmount + this.recyclableWasteLevel + this.nonRecyclableWasteLevel < 100) {
                recyclableWasteLevel += recyclableTrashAmount;
                nonRecyclableWasteLevel += nonRecyclableTrashAmount;

                System.out.println("Thrash added. Current fullness level: " + (recyclableWasteLevel + nonRecyclableWasteLevel));
                if (recyclableWasteLevel + nonRecyclableWasteLevel >= 80) {
                    Notify('M');//"M" stands for general trash bin
                }
            } else {
                System.out.println("Bin is full.");
            }
        }
    }
}

//ConcreteSubject 2
class GeneralTrashBin extends TrashBin {
    public GeneralTrashBin(WasteCollectionDepartment WCD) {
        super(WCD);
        token = "G";
    }

    public void AddTrash(double recyclableTrashAmount, double nonRecyclableTrashAmount) {
        if (recyclableTrashAmount + nonRecyclableTrashAmount > 100) {
            System.out.println("Invalid amount of thrash");
        } else {
            if (recyclableTrashAmount + nonRecyclableTrashAmount + this.recyclableWasteLevel + this.nonRecyclableWasteLevel < 100) {
                recyclableWasteLevel += recyclableTrashAmount;
                nonRecyclableWasteLevel += nonRecyclableTrashAmount;

                System.out.println("Thrash added. Current fullness level: " + (recyclableWasteLevel + nonRecyclableWasteLevel));
                if (recyclableWasteLevel + nonRecyclableWasteLevel >= 80) {
                    Notify('G');//"G" stands for general trash bin
                }
            } else {
                System.out.println("Bin is full.");
            }
        }
    }
}

//Observer
interface Observer {
    public void M_Update(TrashBin trashBin);

    public void G_Update(TrashBin trashBin);
}

//ConcreteObserver
class Sensor implements Observer {

    @Override
    public void M_Update(TrashBin trashBin) {

        System.out.println("Bin is 80% full. A notification is being sent to Waste Collection Department...");
        trashBin.WCD.M_DecideIfGarbageCollectionNeeded();

    }

    @Override
    public void G_Update(TrashBin trashBin) {
        System.out.println("Bin is 80% full. A notification is being sent to Waste Collection Department...");
        trashBin.WCD.G_DecideIfGarbageCollectionNeeded();
    }
}

//-------------------------COMMON AREA----------------------------

//ITERATOR: Concrete Aggregate
//Implements the Iterator creation interface to return an instance of the proper ConcreteIterator.
// ------- COMPOSITE: Leaf
class Street implements LocatingElement {
    private String name;

    public Street(String name) {
        this.name = name;
    }

    //Maintains a list of Trash Bins
    private ArrayList<TrashBin> trashBins = new ArrayList<>();

    public void AddTrashBin(TrashBin bin) {
        trashBins.add(bin);
    }

    public TrashBin GetTrashBin(int index) {
        return trashBins.get(index);
    }

    //private ArrayList<TrashBin> trashBins = new ArrayList<TrashBin>();
    public StreetIterator CreateIterator() {
        return new StreetIterator(this);
    }


    public int getCount() {
        return trashBins.size();
    }

    //Adds a new Trash Bin to the list
    public void add(TrashBin trashBin) {
        trashBins.add(trashBin);
    }


    public TrashBin get(int index) {
        return trashBins.get(index);
    }


    @Override
    public void Add(LocatingElement locatingElement) {
        System.out.println("You can't add.");
    }

    @Override
    public void Remove(LocatingElement locatingElement) {
        System.out.println("You can't remove.");
    }

    @Override
    public void Display(int indent) {
        for (int i = 0; i < indent + 1; i++) {
            System.out.print("-");

        }
        System.out.println(name);
    }

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