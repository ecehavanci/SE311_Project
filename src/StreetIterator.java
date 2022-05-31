
//-----------------------ITERATOR - OBSERVER-------------------------


import java.util.ArrayList;

//Abstract Iterator
interface Iterator {

}

//Concrete Iterator
public class StreetIterator implements Iterator {

}

//ITERATOR: Item --------- OBSERVER: Subject
abstract class TrashBin {
    //Every trash bin knows about the Waste Collection Department they are going to notify when bin is 80% full
    WasteCollectionDepartment WCD;
    //Although in observer pattern there can be many observers, in this project every thrash bin has only one sensor.
    //So there is a list of observers but only one observer can be attached to the trash bin
    ArrayList <Sensor> sensors = new ArrayList<>();
    protected double fullnessLevel;

    public TrashBin(WasteCollectionDepartment WCD) {
        this.WCD = WCD;
    }

    void Attach(Sensor sensor) {
        System.out.println("Attaching sensor...");
        if (this.sensors.size()==0) {
            sensors.add(sensor);
        } else {
            System.out.println("This trash bin already has a sensor attached.");
        }
    }

    void Detach() {
        //Basically this will get rid of the list with posibly one element in it and create another list with size 0
        if (sensors.size()==0){
            sensors = new ArrayList<>();
        }else {
            System.out.println("There is no sensor attached, so nothing can be detached.");
        }
    }

    public void Notify(char token) {
        if (sensors.size()>0){
            if (token == 'M') {
                sensors.get(0).M_Update(this);
            }
            else if (token == 'G') {
                sensors.get(0).G_Update(this);
            }
        }
        else{
            System.out.println("Problem! No sensor is attached to trash bin! Cannot send update to Waste Collection Department!");
        }
    }

    public abstract void AddTrash(double trashAmount);
}

//ConcreteSubject 1
class MedicalTrashBin extends TrashBin {
    public MedicalTrashBin(WasteCollectionDepartment WCD) {
        super(WCD);
    }

    public void AddTrash(double trashAmount) {
        if (fullnessLevel + trashAmount <= 100) {
            fullnessLevel += trashAmount;
            System.out.println("Thrash added. Current fullness level: " + fullnessLevel);
            if (fullnessLevel >= 80) {
                Notify('M');//"M" stands for medical trash bin
            }
        } else {
            System.out.println("Bin is full.");
        }
    }
}

//ConcreteSubject 2
class GeneralTrashBin extends TrashBin {
    public GeneralTrashBin(WasteCollectionDepartment WCD) {
        super(WCD);
    }

    public void AddTrash(double trashAmount) {
        if (fullnessLevel + trashAmount < 100) {
            fullnessLevel += trashAmount;
            System.out.println("Thrash added. Current fullness level: " + fullnessLevel);
            if (fullnessLevel >= 80) {
                Notify('G');//"G" stands for general trash bin
            }
        } else {
            System.out.println("Bin is full.");
        }
    }
}

interface Observer {
    public void M_Update(TrashBin trashBin);
    public void G_Update(TrashBin trashBin);
}

//ConcreteObserver
class Sensor implements Observer {

    @Override
    public void M_Update(TrashBin trashBin) { //TODO: ADD A TYPE, THEN ADD M_DECIDE AND G_DECIDE
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

//ITERATOR: Concrete Aggregate ------- COMPOSITE: Leaf
class Street implements LocatingElement {
    private String name;

    public Street(String name) {
        this.name = name;
    }

    private ArrayList<TrashBin> trashBins = new ArrayList<>();

    public void AddTrashBin(TrashBin bin){
        trashBins.add(bin);
    }

    public TrashBin GetTrashBin(int index){
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
    public WasteCollectionDepartment getWCD() {
        System.out.println("Streets do not have a waste collection department");
        return null;
    }
}