public class Main {
    public static void main(String[] args) {
        //Trying command pattern:
        WasteCollectionDepartment wasteCollectionDepartment = new WasteCollectionDepartment();
        CollectionDepartmentEmployee employee = new CollectionDepartmentEmployee();
        TruckDriver truckDriver = new TruckDriver();
        TruckScreen truckScreen = new TruckScreen();
        employee.CreateCollectionOrder(truckDriver, truckScreen);


    }
}
