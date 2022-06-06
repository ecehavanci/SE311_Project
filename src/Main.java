import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //Trying command pattern:
        /*WasteCollectionDepartment wasteCollectionDepartment = new WasteCollectionDepartment();
        CollectionDepartmentEmployee employee = new CollectionDepartmentEmployee();
        TruckDriver truckDriver = new TruckDriver();
        TruckScreen truckScreen = new TruckScreen();
        employee.CreateCollectionOrder(truckDriver, truckScreen);*/

        //Trying Composite Pattern:

        WasteCollectionDepartment IzmirWCD = new WasteCollectionDepartment(new CollectionDepartmentEmployee("John", "Hatter", 59, 1009));
        //IzmirWCD.AddEmployee(new CollectionDepartmentEmployee( "Marylynne", "Buttons",45, 1000));
        //IzmirWCD.AddEmployee(new CollectionDepartmentEmployee("Gerard", "Greene", 33, 1001));
        //IzmirWCD.AddEmployee(new CollectionDepartmentEmployee("Cheryll", "Livingston", 27, 1002));
        //IzmirWCD.AddEmployee(new CollectionDepartmentEmployee("John", "Hatter", 59, 1003));

        //TODO: I had to change the two on below to City from LocationContainer to City (first part), it needs discussion
        LocatingElement Izmir = new City("Izmir", IzmirWCD);
        LocatingElement Eskisehir = new City("Eskisehir", new WasteCollectionDepartment(new CollectionDepartmentEmployee("Gerard", "Greene", 33, 1007)));

        IzmirWCD.AddTruckDriver(new TruckDriver("Scarlet", "Roseland", 60, 1000,Izmir, new Truck()));
        IzmirWCD.AddTruckDriver(new TruckDriver("Luis", "Williams", 22, 1001, Izmir, new Truck()));
        IzmirWCD.AddTruckDriver(new TruckDriver("Alice", "King", 36, 1002, Izmir, new Truck()));
        IzmirWCD.AddTruckDriver(new TruckDriver("Edgar", "Johns", 42, 1003, Izmir, new Truck()));


        System.out.println("SETTING UP ENVIRONMENT");
        Street s165 = new Street("165 Street");
        Street s166 = new Street("166 Street");
        Street s167 = new Street("167 Street");
        Street s168 = new Street("168 Street");

        s165.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));
        s165.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));

        s166.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));
        s166.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));
        s166.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));

        s167.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));
        s167.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));

        s168.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));


        s165.GetTrashBin(0).Attach(new Sensor());
        s165.GetTrashBin(1).Attach(new Sensor());

        s166.GetTrashBin(0).Attach(new Sensor());
        s166.GetTrashBin(1).Attach(new Sensor());
        s166.GetTrashBin(2).Attach(new Sensor());

        s167.GetTrashBin(0).Attach(new Sensor());
        s167.GetTrashBin(1).Attach(new Sensor());

        s168.GetTrashBin(0).Attach(new Sensor());


        LocatingElement GY = new Neighborhood("Guzelyali");

        Street s42 = new Street("42 Street");
        Street s43 = new Street("43 Street");
        Street s44 = new Street("44 Street");


        s42.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));
        s42.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));
        s42.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));
        s42.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));

        s43.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));
        s43.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));

        s44.AddTrashBin(new GeneralTrashBin(Izmir.getWCD()));
        s44.AddTrashBin(new MedicalTrashBin(Izmir.getWCD()));


        s42.GetTrashBin(0).Attach(new Sensor());
        s42.GetTrashBin(1).Attach(new Sensor());
        s42.GetTrashBin(2).Attach(new Sensor());
        s42.GetTrashBin(3).Attach(new Sensor());

        s43.GetTrashBin(0).Attach(new Sensor());
        s43.GetTrashBin(1).Attach(new Sensor());

        s44.GetTrashBin(0).Attach(new Sensor());
        s44.GetTrashBin(1).Attach(new Sensor());


        LocatingElement OP = new Neighborhood("Odunpazari");

        Izmir.Add(GY);
        GY.Add(s165);
        GY.Add(s166);
        GY.Add(s167);
        GY.Add(s168);

        Eskisehir.Add(OP);
        OP.Add(s42);
        OP.Add(s43);
        OP.Add(s44);

        //Izmir.Display(0);
        //Eskisehir.Display(0);


        System.out.println("\n~Welcome to Waste Collection System~");
        System.out.println("-QUICK INFORMATION-");
        System.out.println("There are 2 Cities namely Izmir and Eskisehir, each city has 8 bins in it.");
        System.out.println("There are medical trash bins (the ones in front of an hospital for example) and general trash bins (the ones in a normal street).");
        System.out.println("Izmir has 3 mecial, 5 general trash bins.");
        System.out.println("Eskisehir has 6 mecial, 2 general trash bins.\n");

        System.out.println("Hierarchy in Izmir as follows:");
        Izmir.Display(0);

        System.out.println("\nWhen 4 or more bins in are 80% full, waste collection starts. \n");

        System.out.println("-SYSTEM STARTED-");
        System.out.println("All bins are empty.\n");

        outputInformation("Trying to add sensor to a bin that is already attached sensor...");
        s165.GetTrashBin(0).Attach(new Sensor());

        outputInformation("\n - DEMONSTRATING ADDING TRASH TO TRASH BINS - \n");
        outputInformation("Adding trash to bin 1: Izmir - Medical trash bin...");
        s165.GetTrashBin(0).AddTrash(13);
        s165.GetTrashBin(0).AddTrash(12);
        s165.GetTrashBin(0).AddTrash(5);
        s165.GetTrashBin(0).AddTrash(32);
        s165.GetTrashBin(0).AddTrash(6);
        s165.GetTrashBin(0).AddTrash(10);
        s165.GetTrashBin(0).AddTrash(16);

        outputInformation("\nAdding trash to bin 2: Izmir - General trash bin...");
        s165.GetTrashBin(1).AddTrash(79);
        s165.GetTrashBin(1).AddTrash(1);



        outputInformation("\nAdding trash to bin 3: Izmir - Medical trash bin...");
        s166.GetTrashBin(0).AddTrash(90);

        outputInformation("\nAdding trash to bin 4: Izmir - General trash bin...");
        s166.GetTrashBin(1).AddTrash(95);

        outputInformation("\nAdding trash to bin 5: Izmir - General trash bin...");
        s166.GetTrashBin(2).AddTrash(91);

        outputInformation("\nAdding trash to bin 6: Izmir - General trash bin...");
        s167.GetTrashBin(0).AddTrash(94);

        outputInformation("\nAdding trash to bin 7: Izmir - General trash bin...");
        s167.GetTrashBin(1).AddTrash(96);

        outputInformation("\nAdding trash to bin 8: Izmir - Medical trash bin...");
        s168.GetTrashBin(0).AddTrash(92);





    }
    public static void outputInformation(String text){
        System.out.println("\u001B[34m"+text+"\u001B[0m");
    }
}
