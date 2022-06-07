import java.util.ArrayList;

//Abstract factory
abstract class Landfill {
    protected ArrayList<RecyclableWaste> recyclableWasteStorage = new ArrayList<>();
    private ArrayList<NonRecyclableWaste> nonRecyclableWasteStorage = new ArrayList<>();

    public void addWaste(NonRecyclableWaste waste) {
        nonRecyclableWasteStorage.add(waste);
    }

    public void addWaste(RecyclableWaste waste) {
        recyclableWasteStorage.add(waste);
    }

    public double getTotalWaste() {
        double totalWaste = 0;
        for (Waste waste : recyclableWasteStorage) {
            totalWaste += waste.getWasteAmount();
        }

        for (Waste waste : nonRecyclableWasteStorage) {
            totalWaste += waste.getWasteAmount();
        }

        return totalWaste;
    }

    public double getRecyclableWaste() {
        double totalWaste = 0;
        for (Waste waste : recyclableWasteStorage) {
            totalWaste += waste.getWasteAmount();
        }
        return totalWaste;
    }

    public double getNonRecyclableWaste() {
        double totalWaste = 0;
        for (Waste waste : nonRecyclableWasteStorage) {
            totalWaste += waste.getWasteAmount();
        }

        return totalWaste;
    }

    abstract public RecyclableWaste decomposeRecyclableWaste(double amount);

    abstract public NonRecyclableWaste decomposeNonRecyclableWaste(double amount);
}

//Concrete factory 1
class MedicalLandfill extends Landfill {
    @Override
    public RecyclableWaste decomposeRecyclableWaste(double amount) {
        return new RecyclableMedicalWaste(amount);

    }

    @Override
    public NonRecyclableWaste decomposeNonRecyclableWaste(double amount) {
        return new NonRecyclableMedicalWaste(amount);
    }
}

//Concrete factory 2
class GeneralLandfill extends Landfill {

    @Override
    public RecyclableWaste decomposeRecyclableWaste(double amount) {
        return new RecyclableGeneralWaste(amount);
    }

    @Override
    public NonRecyclableWaste decomposeNonRecyclableWaste(double amount) {
        return new NonRecyclableGeneralWaste(amount);
    }
}

//Abstract Product
abstract class Waste {

    abstract double getWasteAmount();

    abstract public void printAllInfo();

}

//Abstract Product A
//RecyclableWaste base class
abstract class RecyclableWaste extends Waste {

    protected double wasteAmount;


    public void printAllInfo() {
        System.out.println("---- RECYCLABLE WASTE INFO ----");
        System.out.println("Waste Amount: " + getWasteAmount());
    }
}

//Concrete Product A1
class RecyclableMedicalWaste extends RecyclableWaste {
    public RecyclableMedicalWaste(double amount) {
        this.wasteAmount = amount;
    }

    @Override
    double getWasteAmount() {
        return wasteAmount;
    }

}

//Concrete Product A2
class RecyclableGeneralWaste extends RecyclableWaste {
    public RecyclableGeneralWaste(double amount) {
        this.wasteAmount = amount;
    }

    @Override
    double getWasteAmount() {
        return wasteAmount;
    }

}

//Abstract Product B
//NonRecyclableWaste base class
abstract class NonRecyclableWaste extends Waste {
    protected double wasteAmount;

    public void printAllInfo() {
        System.out.println("---- RECYCLABLE WASTE INFO ----");
        System.out.println("Waste Amount: " + getWasteAmount());
    }

}

//Concrete Product B1
class NonRecyclableMedicalWaste extends NonRecyclableWaste {

    public NonRecyclableMedicalWaste(double amount) {
        this.wasteAmount = amount;
    }

    @Override
    double getWasteAmount() {
        return wasteAmount;
    }
}

//Concrete Product B2
class NonRecyclableGeneralWaste extends NonRecyclableWaste {
    public NonRecyclableGeneralWaste(double amount) {
        this.wasteAmount = amount;
    }

    @Override
    double getWasteAmount() {
        return wasteAmount;
    }

}
