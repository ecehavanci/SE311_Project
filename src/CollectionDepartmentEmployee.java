import java.util.ArrayList;
import java.util.Collections;

//Client
public class CollectionDepartmentEmployee {
    public void CreateCollectionOrder(TruckDriver driver, TruckScreen screen){
        Order collectionOrder = new CollectionOrder(driver);
        screen.TransmitOrder(collectionOrder);
    }

}

//There is a Waste Collection Department in the City which as employees in it
class WasteCollectionDepartment{
    ArrayList<CollectionDepartmentEmployee> employees;
}

//Invoker
class TruckScreen{
    public void TransmitOrder(Order order){
        order.Execute();
    }
}

//AbstractCommand
interface Order{
    public void Execute();
}

//ConcreteCommand
class CollectionOrder implements Order{
    TruckDriver truckDriver;

    public CollectionOrder(TruckDriver truckDriver) {
        this.truckDriver=truckDriver;
    }

    @Override
    public void Execute() {
        truckDriver.CollectWaste();
        truckDriver.EmptyTruck();

    }
}

//-------------------------COMMON AREA--------------------------------
//COMMAND: Receiver ------- ITERATOR: Client
class TruckDriver{
    public void CollectWaste(){//
        System.out.println("Collecting waste...");
        //TODO: Iterator: Collect waste from all bins 80 percent or more full
    }

    public void EmptyTruck(){
        System.out.println("Driving to facility...");
        System.out.println("Emptying...");
        //TODO: Empty Truck to one of the landfills but how to separate general waste from medical waste?
        //Do we collect waste seperated? For example a trash bin in front of a hospital would be considered medical
        //waste. Will we label trash bins as "medical" and "general"? If so will there be medical waste trucks and
        //general waste trucks? Or will there be only one type of truck with two waste collecting orders:
        //CollectMedicalWaste and CollectGeneralWaste?


    }



}