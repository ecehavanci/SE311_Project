import java.util.ArrayList;
import java.util.Collections;
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

    public void CreateCollectionOrder(TruckDriver driver, TruckScreen screen) {
        Order collectionOrder = new CollectionOrder(driver);
        System.out.println("Waste collection order is created. Waiting for truck driver...");
        screen.TransmitOrder(collectionOrder);
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
    ArrayList<Truck> trucks = new ArrayList<>();


    private int m_collectionNeedingBinCounter = 0;
    private int g_collectionNeedingBinCounter = 0;


    /*public void AddEmployee(CollectionDepartmentEmployee employee) {
        System.out.println(employees.size());
        employees.add(employee);
    }*/

    public void AddTruckDriver(TruckDriver driver) {
        truckDrivers.add(driver);

    }

    public void AddTruck(Truck truck) {
        trucks.add(truck);
    }

    public void M_DecideIfGarbageCollectionNeeded() {
        m_collectionNeedingBinCounter = DecideIfGarbageCollectionNeeded(m_collectionNeedingBinCounter);
    }

    public void G_DecideIfGarbageCollectionNeeded() {
        g_collectionNeedingBinCounter = DecideIfGarbageCollectionNeeded(g_collectionNeedingBinCounter);
    }

    public int DecideIfGarbageCollectionNeeded(int collectionNeedingBinCounter) {
        collectionNeedingBinCounter++;

        if (employee == null || truckDrivers.size() == 0 || trucks.size() == 0) {
            System.out.println("Garbage collection failed due to source problems.");
        } else if (collectionNeedingBinCounter >= 4) {
            System.out.println("4 or more trash bins are at least 80% percent full. Starting Collection...");
            Random random = new Random();//This is for selecting a random employee and a random truck
            //int empNum = random.nextInt(employees.size());
            int driverNum = random.nextInt(truckDrivers.size());
            int truckNum = random.nextInt(trucks.size());

            employee.CreateCollectionOrder(truckDrivers.get(driverNum), trucks.get(truckNum).getTruckScreen());
            collectionNeedingBinCounter=0;
        }
        return collectionNeedingBinCounter;
    }


}

//This class is for TruckScreen, because it is not logical to have a truckScreen out of nowhere
class Truck {
    private final TruckScreen truckScreen = new TruckScreen();

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
class CollectionOrder implements Order {
    TruckDriver truckDriver;

    public CollectionOrder(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    @Override
    public void Execute() {
        truckDriver.CollectWaste();
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

    public TruckDriver(String name, String surname, int age, int ID) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.ID = ID;
    }

    public void CollectWaste() {//
        System.out.println("Collecting waste...");
        //TODO: Iterator: Collect waste from all bins 80 percent or more full
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


}