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

    // creates collection order depending on waste type and hands it over to truck driver
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

class WasteDecomposer {

    void Decompose(Landfill landfill, Truck truck) {
        RecyclableWaste wasteR = landfill.decomposeRecyclableWaste(truck.getRecyclableWasteAmount());
        NonRecyclableWaste wasteN = landfill.decomposeNonRecyclableWaste(truck.getNonRecyclableWasteAmount());

        landfill.addWaste(wasteR);
        landfill.addWaste(wasteN);

        System.out.println("Waste Decomposed.");

        System.out.println("\u001B[32m" + "Decomposed recyclable waste amount: " + landfill.getRecyclableWaste() + "\u001B[0m\t");
        System.out.println("\u001B[32m" + "Decomposed non-recyclable waste amount: " + landfill.getNonRecyclableWaste() + "\u001B[0m\t");
    }
}

//There is a Waste Collection Department in the City which as employees in it
class WasteCollectionDepartment {
        private WasteDecomposer wasteDecomposer;
    //This is the person who is in charge in the Collection Department
    private CollectionDepartmentEmployee employee;
    private ArrayList<TruckDriver> truckDrivers = new ArrayList<>();
    private int m_collectionNeedingBinCounter = 0;
    private int g_collectionNeedingBinCounter = 0;

    public WasteCollectionDepartment(CollectionDepartmentEmployee employee, WasteDecomposer wasteDecomposer) {
        this.employee = employee;
        this.wasteDecomposer = wasteDecomposer;
    }
    //adds truck driver to waste collection department
    public void AddTruckDriver(TruckDriver driver) {
        truckDrivers.add(driver);

    }

    //if 4 or more trash bins are at least 80% full, employee of department creates a collection order (for medical waste)
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

    //if 4 or more trash bins are at least 80% full, employee of department creates a collection order (for general waste)
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

    //decomposes the waste
    public void DecomposeWaste(Landfill landfill, Truck truck) {
        wasteDecomposer.Decompose(landfill, truck);
    }
}

//This class is for TruckScreen, because it is not logical to have a truckScreen out of nowhere
class Truck {
    private final TruckScreen truckScreen = new TruckScreen();
    private double nonRecyclableWasteAmount;
    private double recyclableWasteAmount;


    //collects trash from streets
    void addTrash(double recyclableWasteAmount, double nonRecyclableWasteAmount) {
        this.recyclableWasteAmount += recyclableWasteAmount;
        this.nonRecyclableWasteAmount += nonRecyclableWasteAmount;
    }

    //calculates collected total waste amount
    public double getWasteAmount() {
        return nonRecyclableWasteAmount + recyclableWasteAmount;
    }

    //calculates collected Non-Recyclable waste amount
    public double getNonRecyclableWasteAmount() {
        return nonRecyclableWasteAmount;
    }

    //calculates collected Recyclable waste amount
    public double getRecyclableWasteAmount() {
        return recyclableWasteAmount;
    }

    public TruckScreen getTruckScreen() {
        return truckScreen;
    }

    //truck driver empties the truck and reset the waste amount in the truck
    public void resetWasteAmount() {
        nonRecyclableWasteAmount = 0;
        recyclableWasteAmount = 0;
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

    //executes the order
    public void Execute();
}

//ConcreteCommand 1
class MedicalCollectionOrder implements Order {
    TruckDriver truckDriver;

    public MedicalCollectionOrder(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    @Override
    public void Execute() {
        truckDriver.CollectMedicalWaste();  //executes truck driver's given order (collects medicak-waste)
        truckDriver.EmptyTruckToMedicalLandfill(); //executes truck driver's given order (empties the truck to landfill)

    }
}

//Concrete Command 2
class GeneralCollectionOrder implements Order {
    TruckDriver truckDriver;

    public GeneralCollectionOrder(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    @Override
    public void Execute() {
        truckDriver.CollectGeneralWaste();
        truckDriver.EmptyTruckToGeneralLandfill();

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

    //collects medical waste
    public void CollectMedicalWaste() {
        System.out.println("\u001B[35m" + "WASTE COLLECTION BEGINNING: Truck current waste amount: " + truck.getWasteAmount() + "\u001B[0m");
        System.out.println("Truck driver drives through streets...");
        city.Traverse(this, "M");
        System.out.println("\u001B[35m" + "WASTE COLLECTION END: Truck current waste amount: " + truck.getWasteAmount() + "\u001B[0m");

    }

    //collects general waste
    public void CollectGeneralWaste() {
        System.out.println("Truck driver drives through streets...");
        System.out.println("\u001B[35m" + "WASTE COLLECTION BEGINNING: Truck current waste amount: " + truck.getWasteAmount() + "\u001B[0m");
        city.Traverse(this, "G");
        System.out.println("\u001B[35m" + "WASTE COLLECTION END: Truck current waste amount: " + truck.getWasteAmount() + "\u001B[0m");

    }

    //empties medical bins of given streets
    public void EmptyFullMedicalBins(Street street) {
        Iterator streetIterator = new StreetIterator(street);

        for (streetIterator.First(); !streetIterator.IsDone(); streetIterator.Next()) {
            System.out.print(streetIterator.CurrentTrashBin().getToken().equals("M") ? "Medical thrash bin: " : "General thrash bin: ");
            System.out.println("Trash level of current bin: " + streetIterator.CurrentTrashBin().getFullnessLevel());

            if (streetIterator.CurrentTrashBin().getFullnessLevel() >= 80) {
                if (streetIterator.CurrentTrashBin().getToken().equals("M")) {
                    System.out.println("Emptying bin...");
                    truck.addTrash(streetIterator.CurrentTrashBin().getRecyclableWasteLevel(), streetIterator.CurrentTrashBin().getNonRecyclableWasteLevel());
                    streetIterator.CurrentTrashBin().resetFullnessLevel();
                } else {
                    System.out.println("Skipping general trash bin...");
                }
            } else {
                System.out.println("Skipping bin...");
            }
        }
    }

    //empties general bins of given streets
    public void EmptyFullGeneralBins(Street street) {
        Iterator streetIterator = new StreetIterator(street);
        for (streetIterator.First(); !streetIterator.IsDone(); streetIterator.Next()) {
            System.out.print(streetIterator.CurrentTrashBin().getToken().equals("M") ? "Medical thrash bin: " : "General thrash bin: ");
            System.out.println("Trash level of current bin: " + streetIterator.CurrentTrashBin().getFullnessLevel());
            if (streetIterator.CurrentTrashBin().getFullnessLevel() >= 80) {
                if (streetIterator.CurrentTrashBin().getToken().equals("G")) {
                    System.out.println("Emptying bin...");
                    truck.addTrash(streetIterator.CurrentTrashBin().getRecyclableWasteLevel(), streetIterator.CurrentTrashBin().getNonRecyclableWasteLevel());
                    streetIterator.CurrentTrashBin().resetFullnessLevel();
                } else {
                    System.out.println("Skipping medical trash bin...");

                }
            } else {
                System.out.println("Skipping bin...");
            }
        }
    }

    //empties truck to medical landfill
    public void EmptyTruckToMedicalLandfill() {
        System.out.println("Driving to facility...");
        System.out.println("Emptying to Medical Landfill and decomposing to recyclable and non-recyclable waste...");
        //TODO: Empty Truck to one of the landfills but how to separate general waste from medical waste?
        //Do we collect waste seperated? For example a trash bin in front of a hospital would be considered medical
        //waste. Will we label trash bins as "medical" and "general"? If so will there be medical waste trucks and
        //general waste trucks? Or will there be only one type of truck with two waste collecting orders:
        //CollectMedicalWaste and CollectGeneralWaste?

        try {
            System.out.println("\u001B[33m" + "TRUCK EMPTYING BEGINNING: Medical Landfill current waste amount: " + city.getMedicalLandfill().getTotalWaste() + "\u001B[0m");
            city.getWCD().DecomposeWaste(city.getMedicalLandfill(), truck);
            System.out.println("\u001B[33m" + "TRUCK EMPTYING END: Medical Landfill current waste amount: " + city.getMedicalLandfill().getTotalWaste() + "\u001B[0m");

        } catch (LandfillNotFoundException e) {
            System.out.println("");
        }

        System.out.println("Waste collection ended");

    }


    //empties truck to general landfill
    public void EmptyTruckToGeneralLandfill() {
        System.out.println("Driving to facility...");

        try {
            GeneralLandfill[] landfills = city.getGeneralLandfills();
            System.out.println("Choosing which bin to empty...");

            if (landfills[0].getTotalWaste() > landfills[1].getTotalWaste()) {
                System.out.println("Emptying to General Landfill 1 and decomposing to recyclable and non-recyclable waste...");
                System.out.println("\u001B[33m" + "TRUCK EMPTYING BEGINNING: General Landfill 1 current waste amount: " + landfills[0].getTotalWaste() + "\u001B[0m");
                city.getWCD().DecomposeWaste(landfills[1], truck);
                System.out.println("\u001B[33m" + "TRUCK EMPTYING END: General Landfill 1 current waste amount: " + landfills[0].getTotalWaste() + "\u001B[0m");

            } else {
                System.out.println("Emptying to General Landfill 2 and decomposing to recyclable and non-recyclable waste...");
                System.out.println("\u001B[33m" + "TRUCK EMPTYING BEGINNING: General Landfill 2 current waste amount: " + landfills[1].getTotalWaste() + "\u001B[0m");
                city.getWCD().DecomposeWaste(landfills[0], truck);
                System.out.println("\u001B[33m" + "TRUCK EMPTYING END: General Landfill 2 current waste amount: " + landfills[0].getTotalWaste() + "\u001B[0m");

                truck.resetWasteAmount();
                System.out.println("\u001B[35m" + "TRUCK EMPTYING END: Truck current waste amount: " + truck.getWasteAmount() + "\u001B[0m");


            }

        } catch (LandfillNotFoundException e) {
            System.out.println("");
        }

        System.out.println("Waste collection ended");
    }

    public Truck getTruck() {
        return truck;
    }
}
