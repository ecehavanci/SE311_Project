
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

}