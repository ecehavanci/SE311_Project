public class Main {
    public static void main(String[] args) {
        //Trying command pattern:
        /*WasteCollectionDepartment wasteCollectionDepartment = new WasteCollectionDepartment();
        CollectionDepartmentEmployee employee = new CollectionDepartmentEmployee();
        TruckDriver truckDriver = new TruckDriver();
        TruckScreen truckScreen = new TruckScreen();
        employee.CreateCollectionOrder(truckDriver, truckScreen);*/

        //Trying Composite Pattern:
        Street s165 = new Street("165 Street");
        Street s166 = new Street("166 Street");
        LocatingElement gy = new Neighborhood("Guzelyali");
        LocatingElement op = new Neighborhood("Odunpazari");
        LocatingElement izmir = new City("Izmir");
        LocatingElement eskisehir = new City("Eskisehir");

        izmir.Add(gy);
        gy.Add(s165);
        eskisehir.Add(op);
        op.Add(s166);
        izmir.Display(0);
        eskisehir.Display(0);
    }
}
