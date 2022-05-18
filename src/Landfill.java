
//Abstract factory
abstract class Landfill {
    abstract public RecyclableWaste collectRecyclableWaste();

    abstract public NonRecyclableWaste collectNonRecyclableWaste();
}

//Concrete factory 1
class MedicalLandfill extends Landfill {

    @Override
    public RecyclableWaste collectRecyclableWaste() {
        return new RecyclableMedicalWaste("Li", "Liquid");
    }

    @Override
    public NonRecyclableWaste collectNonRecyclableWaste() {
        return new NonRecyclableMedicalWaste("Li", "Liquid");
    }
}

//Concrete factory 2
class GeneralLandfill extends Landfill {

    @Override
    public RecyclableWaste collectRecyclableWaste() {
        return new RecyclableGeneralWaste("Fe", "Solid");
    }

    @Override
    public NonRecyclableWaste collectNonRecyclableWaste() {
        return new NonRecyclableGeneralWaste("Fe", "Solid");
    }
}

//Abstract Product
abstract class Waste {
    abstract public String getWasteElement();   //waste element

    abstract public String getWasteSubstance(); //if waste is solid,liquid or gas

    abstract public boolean isRecyclable();

    abstract public String getWasteType();

    abstract public void printAllInfo();

}

//Abstract Product A
//RecyclableWaste base class
abstract class RecyclableWaste extends Waste {
    protected String element;
    protected String substance;
    protected boolean isRecyclable;
    protected String wasteType;

    @Override
    public String getWasteElement() {
        return element;
    }

    @Override
    public String getWasteSubstance() {
        return substance;
    }

    @Override
    public boolean isRecyclable() {
        return true;
    }

    @Override
    public String getWasteType() {
        return wasteType;
    }

    @Override
    public void printAllInfo() {
        System.out.println("---- WASTE INFO ----");
        System.out.println("Element: " + getWasteElement());
        System.out.println("Substance: " + getWasteSubstance());
        System.out.println("Recyclable: " + isRecyclable());
        System.out.println("Waste Type: " + getWasteType());
    }
}

//Concrete Product A1
class RecyclableMedicalWaste extends RecyclableWaste {

    public RecyclableMedicalWaste(String element, String substance) {
        element = element;
        substance = substance;
        wasteType = "Medical Waste";
    }

}

//Concrete Product A2
class RecyclableGeneralWaste extends RecyclableWaste {

    public RecyclableGeneralWaste(String element, String substance) {
        element = element;
        substance = substance;
        wasteType = "General Waste";
    }

}

//Abstract Product B
//NonRecyclableWaste base class
abstract class NonRecyclableWaste extends Waste {
    protected String element;
    protected String substance;
    protected boolean isRecyclable;
    protected String wasteType;

    @Override
    public String getWasteElement() {
        return element;
    }

    @Override
    public String getWasteSubstance() {
        return substance;
    }

    @Override
    public boolean isRecyclable() {
        return false;
    }

    @Override
    public String getWasteType() {
        return wasteType;
    }

    @Override
    public void printAllInfo() {
        System.out.println("---- WASTE INFO ----");
        System.out.println("Element: " + getWasteElement());
        System.out.println("Substance: " + getWasteSubstance());
        System.out.println("Recyclable: " + isRecyclable());
        System.out.println("Waste Type: " + getWasteType());
    }

}

//Concrete Product B1
class NonRecyclableMedicalWaste extends NonRecyclableWaste {


    public NonRecyclableMedicalWaste(String element, String substance) {
        element = element;
        substance = substance;
        wasteType = "Mecical Waste";
    }
}

//Concrete Product B2
class NonRecyclableGeneralWaste extends NonRecyclableWaste {

    public NonRecyclableGeneralWaste(String element, String substance) {
        element = element;
        substance = substance;
        wasteType = "General Waste";
    }
}
