//ConcreteSubject 1
class MedicalTrashBin extends TrashBin {
    public MedicalTrashBin(WasteCollectionDepartment WCD) {
        super(WCD);
        token = "M";
    }

    public void AddTrash(double recyclableTrashAmount, double nonRecyclableTrashAmount) {
        if (recyclableTrashAmount + nonRecyclableTrashAmount > 100) {
            System.out.println("Invalid amount of trash");
        } else {
            if (recyclableTrashAmount + nonRecyclableTrashAmount + this.recyclableWasteLevel + this.nonRecyclableWasteLevel < 100) {
                recyclableWasteLevel += recyclableTrashAmount;
                nonRecyclableWasteLevel += nonRecyclableTrashAmount;

                System.out.println("Trash added. Current fullness level: " + (recyclableWasteLevel + nonRecyclableWasteLevel));
                if (recyclableWasteLevel + nonRecyclableWasteLevel >= 80) {
                    Notify('M');//"M" stands for general trash bin
                }
            } else {
                System.out.println("Bin is full.");
            }
        }
    }
}

//ConcreteSubject 2
class GeneralTrashBin extends TrashBin {
    public GeneralTrashBin(WasteCollectionDepartment WCD) {
        super(WCD);
        token = "G";
    }

    public void AddTrash(double recyclableTrashAmount, double nonRecyclableTrashAmount) {
        if (recyclableTrashAmount + nonRecyclableTrashAmount > 100) {
            System.out.println("Invalid amount of trash");
        } else {
            if (recyclableTrashAmount + nonRecyclableTrashAmount + this.recyclableWasteLevel + this.nonRecyclableWasteLevel < 100) {
                recyclableWasteLevel += recyclableTrashAmount;
                nonRecyclableWasteLevel += nonRecyclableTrashAmount;

                System.out.println("Trash added. Current fullness level: " + (recyclableWasteLevel + nonRecyclableWasteLevel));
                if (recyclableWasteLevel + nonRecyclableWasteLevel >= 80) {
                    Notify('G');//"G" stands for general trash bin
                }
            } else {
                System.out.println("Bin is full.");
            }
        }
    }
}

//Observer
interface Observer {
    public void M_Update(TrashBin trashBin);

    public void G_Update(TrashBin trashBin);
}

//ConcreteObserver
class Sensor implements Observer {
    private double binFullnessLevel;

    @Override
    public void M_Update(TrashBin trashBin) {
        binFullnessLevel = trashBin.getFullnessLevel();
        System.out.println("Bin is " + binFullnessLevel + "% full. A notification is being sent to Waste Collection Department...");

        //Notification is sent to Waste Collection department so it can decide whether waste collection is needed
        trashBin.WCD.M_DecideIfGarbageCollectionNeeded();

    }

    @Override
    public void G_Update(TrashBin trashBin) {
        binFullnessLevel = trashBin.getFullnessLevel();
        System.out.println("Bin is " + binFullnessLevel + "% full. A notification is being sent to Waste Collection Department...");

        //Notification is sent to Waste Collection department so it can decide whether waste collection is needed
        trashBin.WCD.G_DecideIfGarbageCollectionNeeded();
    }
}
