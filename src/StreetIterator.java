
//-----------------------ITERATOR-------------------------


//Abstract Iterator
interface Iterator{

}

//Concrete Iterator
public class StreetIterator implements Iterator {

}

//Item
class TrashBin{
    Sensor sensor;
}

//Every TrashBin has a sensor installed on it
class Sensor{

}

//-------------------------COMMON AREA----------------------------

//ITERATOR: Concrete Aggregate ------- COMPOSITE: Leaf
class Street implements LocatingElement{
    public Street(String name) {
        this.name = name;
    }

    private String name;


    @Override
    public void Add(LocatingElement locatingElement) {
        System.out.println("You can't add.");
    }

    @Override
    public void Remove(LocatingElement locatingElement) {
        System.out.println("You can't remove.");
    }

    @Override
    public void Display(int indent) {
        for (int i = 0; i < indent+1 ; i++) {
            System.out.print("-");

        }
        System.out.println(name);
    }
}