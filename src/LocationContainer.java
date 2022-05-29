import java.util.ArrayList;

//Component
interface LocatingElement {
    void Add(LocatingElement locatingElement);

    void Remove(LocatingElement locatingElement);

    void Display(int indent);

    //TODO: WARNING VERY UNSURE ABOUT THAT
    WasteCollectionDepartment getWCD();
}

//Composite Object
public class LocationContainer implements LocatingElement {
    public LocationContainer(String name) {
        this.name = name;
    }

    private String name;
    private ArrayList<LocatingElement> locationList = new ArrayList<>();

    @Override
    public void Add(LocatingElement locatingElement) {
        locationList.add(locatingElement);
    }

    @Override
    public void Remove(LocatingElement locatingElement) {
        locationList.remove(locatingElement);
    }

    @Override
    public void Display(int indent) {
        for (int i = 0; i < indent+1 ; i++) {
            System.out.print("-");
        }

        System.out.println(name);
        for (int i = 0; i < locationList.size(); i++) {
            locationList.get(i).Display(indent+1);
        }
    }

    @Override
    public WasteCollectionDepartment getWCD() {
        return null;
    }
}

//Leaf is in StreetIterator.java


//Cities are composite objects, they have neighborhoods in them
class City extends LocationContainer {
    //City has 3 landfills: one for medical waste, 2 for general use
    private MedicalLandfill medicalLandfill;
    private GeneralLandfill generalLandfill1;
    private GeneralLandfill generalLandfill2;

    public City(String name, WasteCollectionDepartment WCD) {
        super(name);
        this.WCD = WCD;
    }

    public WasteCollectionDepartment getWCD() {
        return WCD;
    }

    private WasteCollectionDepartment WCD;

    //comes from abstract factory pattern, city is the client
    //city uses Landfills
    private ArrayList<Waste> wastes;

    public City(String name) {
        super(name);
    }

    public void createLandfill(Landfill landfill) {
        wastes = new ArrayList<>();
        wastes.add(landfill.collectRecyclableWaste());
        wastes.add(landfill.collectNonRecyclableWaste());
    }

    void displayLandfillParts() {
        System.out.println("\tListing Wastes\n\t-------------");
        wastes.forEach(waste -> waste.printAllInfo());
    }
}


//Neighborhoods are composite objects, they have streets in them
class Neighborhood extends LocationContainer {
    public Neighborhood(String name) {
        super(name);
    }

    @Override
    public WasteCollectionDepartment getWCD() {
        System.out.println("Neighborhoods do not have Waste Collection department");
        return super.getWCD();
    }
}

