import java.util.ArrayList;
import java.util.Random;

//Client
public class CollectionDepartmentEmployee {
    private String name;
    private String surname;
    private int age;
    private int ID;

    public CollectionDepartmentEmployee(String name, String surname, int age, int ID) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.ID = ID;
    }

    public void CreateCollectionOrder(TruckDriver driver, TruckScreen screen, String type) {
        if (type.equals("M")) {
            Order collectionOrder = new MedicalCollectionOrder(driver);
            System.out.println("Medical waste collection order is created. Waiting for truck driver...");
            screen.TransmitOrder(collectionOrder);
        }
        if (type.equals("G")) {
            Order collectionOrder = new GeneralCollectionOrder(driver);
            System.out.println("General waste collection order is created. Waiting for truck driver...");
            screen.TransmitOrder(collectionOrder);
        }

    }

}

//There is a Waste Collection Department in the City which as employees in it
class WasteCollectionDepartment {
    //ArrayList<CollectionDepartmentEmployee> employees = new ArrayList<>();


    public WasteCollectionDepartment(CollectionDepartmentEmployee employee) {
        this.employee = employee;
    }

    //This is the person who is in charge in the Collection Department
    CollectionDepartmentEmployee employee;
    ArrayList<TruckDriver> truckDrivers = new ArrayList<>();


    private int m_collectionNeedingBinCounter = 0;
    private int g_collectionNeedingBinCounter = 0;


    /*public void AddEmployee(CollectionDepartmentEmployee employee) {
        System.out.println(employees.size());
        employees.add(employee);
    }*/

    public void AddTruckDriver(TruckDriver driver) {
        truckDrivers.add(driver);

    }

    /*public void AddTruck(Truck truck) {
        trucks.add(truck);
    }*/

    public void M_DecideIfGarbageCollectionNeeded() {
        m_collectionNeedingBinCounter++;

        if (employee == null || truckDrivers.size() == 0) {
            System.out.println("Garbage collection failed due to source problems.");
        } else if (m_collectionNeedingBinCounter >= 4) {
            System.out.println("4 or more trash bins are at least 80% full. Starting Collection...");
            Random random = new Random();//This is for selecting a random employee and a random truck
            //int empNum = random.nextInt(employees.size());
            int driverNum = random.nextInt(truckDrivers.size());

            employee.CreateCollectionOrder(truckDrivers.get(driverNum), truckDrivers.get(driverNum).getTruck().getTruckScreen(), "M");
            m_collectionNeedingBinCounter = 0;
        }
    }

    public void G_DecideIfGarbageCollectionNeeded() {
        g_collectionNeedingBinCounter++;

        if (employee == null || truckDrivers.size() == 0) {
            System.out.println("Garbage collection failed due to source problems.");
        } else if (g_collectionNeedingBinCounter >= 4) {
            System.out.println("4 or more trash bins are at least 80% full. Starting Collection...");
            Random random = new Random();//This is for selecting a random truck driver
            //int empNum = random.nextInt(employees.size());
            int driverNum = random.nextInt(truckDrivers.size());

            employee.CreateCollectionOrder(truckDrivers.get(driverNum), truckDrivers.get(driverNum).getTruck().getTruckScreen(), "G");
            g_collectionNeedingBinCounter = 0;
        }
    }
}

//This class is for TruckScreen, because it is not logical to have a truckScreen out of nowhere
class Truck {
    private final TruckScreen truckScreen = new TruckScreen();
    private int fullnessLevel;

    void addTrash(double trashAmount) {
        fullnessLevel += trashAmount;
    }

    public TruckScreen getTruckScreen() {
        return truckScreen;
    }
}


//Invoker
class TruckScreen {
    public void TransmitOrder(Order order) {
        order.Execute();
    }
}

//AbstractCommand
interface Order {
    public void Execute();
}

//ConcreteCommand
class MedicalCollectionOrder implements Order {
    TruckDriver truckDriver;

    public MedicalCollectionOrder(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    @Override
    public void Execute() {
        truckDriver.CollectMedicalWaste();
        truckDriver.EmptyTruck();

    }
}

class GeneralCollectionOrder implements Order {
    TruckDriver truckDriver;

    public GeneralCollectionOrder(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    @Override
    public void Execute() {
        truckDriver.CollectGeneralWaste();
        truckDriver.EmptyTruck();

    }
}

//-------------------------COMMON AREA--------------------------------
//COMMAND: Receiver ------- ITERATOR: Client
class TruckDriver {
    private String name;
    private String surname;
    private int age;
    private int ID;
    private LocatingElement city;
    private Truck truck;

    public TruckDriver(String name, String surname, int age, int ID, LocatingElement city, Truck truck) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.ID = ID;
        this.city = city;
        this.truck = truck;
    }

    public void CollectMedicalWaste() {

        System.out.println("Truck driver drives through streets...");
        //TODO: Iterator: Collect waste from all bins 80 percent or more full
        city.Traverse(this, "M");
    }

    public void CollectGeneralWaste() {

        System.out.println("Truck driver drives through streets...");
        //TODO: Iterator: Collect waste from all bins 80 percent or more full
        city.Traverse(this, "G");
    }

    public void EmptyFullMedicalBins(Street street) {
        Iterator streetIterator = new StreetIterator(street);
        for (streetIterator.First(); !streetIterator.IsDone(); streetIterator.Next()) {
            System.out.print(streetIterator.CurrentTrashBin().getToken().equals("M") ? "Medical thrash bin: "  : "General thrash bin: ");
            System.out.println("Trash level of current bin: " + streetIterator.CurrentTrashBin().fullnessLevel);
            if (streetIterator.CurrentTrashBin().getFullnessLevel() >= 80) {
                if (streetIterator.CurrentTrashBin().getToken().equals("M")) {
                    System.out.println("Emptying bin...");
                    truck.addTrash(streetIterator.CurrentTrashBin().getFullnessLevel());
                    streetIterator.CurrentTrashBin().setFullnessLevel(0);
                } else {
                    System.out.println("Skipping general trash bin...");
                }
            } else {
                System.out.println("Skipping bin...");
            }
        }
    }

    public void EmptyFullGeneralBins(Street street) {
        Iterator streetIterator = new StreetIterator(street);
        for (streetIterator.First(); !streetIterator.IsDone(); streetIterator.Next()) {
            System.out.print(streetIterator.CurrentTrashBin().getToken().equals("M") ? "Medical thrash bin: "  : "General thrash bin: ");
            System.out.println("Trash level of current bin: " + streetIterator.CurrentTrashBin().fullnessLevel);
            if (streetIterator.CurrentTrashBin().getFullnessLevel() >= 80) {
                if (streetIterator.CurrentTrashBin().getToken().equals("G")) {
                    System.out.println("Emptying bin...");
                    truck.addTrash(streetIterator.CurrentTrashBin().getFullnessLevel());
                    streetIterator.CurrentTrashBin().setFullnessLevel(0);
                } else {
                    System.out.println("Skipping medical trash bin...");

                }
            } else {
                System.out.println("Skipping bin...");
            }
        }
    }

    public void EmptyTruck() {
        System.out.println("Driving to facility...");
        System.out.println("Emptying...");
        //TODO: Empty Truck to one of the landfills but how to separate general waste from medical waste?
        //Do we collect waste seperated? For example a trash bin in front of a hospital would be considered medical
        //waste. Will we label trash bins as "medical" and "general"? If so will there be medical waste trucks and
        //general waste trucks? Or will there be only one type of truck with two waste collecting orders:
        //CollectMedicalWaste and CollectGeneralWaste?


    }

    public Truck getTruck() {
        return truck;
    }
}