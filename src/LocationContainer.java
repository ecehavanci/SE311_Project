import java.util.ArrayList;

//Component
interface LocatingElement {

}

//Composite Object
public class LocationContainer implements LocatingElement {

}

//Leaf is in StreetIterator.java


//Cities are composite objects, they have neighborhoods in them
class City extends LocationContainer {
    //City has 3 landfills: one for medical waste, 2 for general use
    MedicalLandfill medicalLandfill;
    GeneralLandfill generalLandfill1;
    GeneralLandfill generalLandfill2;

    //comes from abstract factory pattern, city is the client
    //city uses Landfills
    private ArrayList<Waste> wastes;

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

}

