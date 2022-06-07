import java.util.ArrayList;

//Component
interface LocatingElement {

    void Add(LocatingElement locatingElement);

    void Remove(LocatingElement locatingElement);

    void Display(int indent);

    void Traverse(TruckDriver truckDriver, String type);//type is "M" or "G" meaning medical or general

    //TODO: WARNING VERY UNSURE ABOUT THAT
    WasteCollectionDepartment getWCD();

    MedicalLandfill getMedicalLandfill() throws LandfillNotFoundException;

    GeneralLandfill[] getGeneralLandfills() throws LandfillNotFoundException;
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
        for (int i = 0; i < indent + 1; i++) {
            System.out.print("-");
        }

        System.out.println(name);
        for (int i = 0; i < locationList.size(); i++) {
            locationList.get(i).Display(indent + 1);
        }
    }

    @Override
    public void Traverse(TruckDriver truckDriver, String type) {
        for (int i = 0; i < locationList.size(); i++) {
            locationList.get(i).Traverse(truckDriver, type);
        }
    }

    @Override
    public WasteCollectionDepartment getWCD() {
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

//Leaf is in StreetIterator.java


//Cities are composite objects, they have neighborhoods in them
class City extends LocationContainer {
    //City has 3 landfills: one for medical waste, 2 for general use
    private MedicalLandfill medicalLandfill;
    private GeneralLandfill generalLandfill1;
    private GeneralLandfill generalLandfill2;

    public City(String name, WasteCollectionDepartment WCD, MedicalLandfill medicalLandfill, GeneralLandfill generalLandfill1, GeneralLandfill generalLandfill2) {
        super(name);
        this.WCD = WCD;
        this.medicalLandfill = medicalLandfill;
        this.generalLandfill1 = generalLandfill1;
        this.generalLandfill2 = generalLandfill2;
    }

    public WasteCollectionDepartment getWCD() {
        return WCD;
    }

    private WasteCollectionDepartment WCD;

    //comes from abstract factory pattern, city is the client
    //city uses Landfills
    //private ArrayList<Waste> wastes;

    @Override
    public MedicalLandfill getMedicalLandfill() {
        return medicalLandfill;
    }

    @Override
    public GeneralLandfill[] getGeneralLandfills() {
        return new GeneralLandfill[]{generalLandfill1, generalLandfill2};
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

    @Override
    public MedicalLandfill getMedicalLandfill() throws LandfillNotFoundException {
        throw new LandfillNotFoundException();
    }

    @Override
    public GeneralLandfill[] getGeneralLandfills() throws LandfillNotFoundException {
        throw new LandfillNotFoundException();
    }
}

class LandfillNotFoundException extends Exception {

}

