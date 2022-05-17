
//Component
interface LocatingElement{

}

//Composite Object
public class LocationContainer {

}

//Leaf is in StreetIterator.java


//Cities are composite objects, they have neighborhoods in them
class City extends LocationContainer{
    //City has 3 landfills: one for medical waste, 2 for general use
    MedicalLandfill medicalLandfill;
    GeneralLandfill generalLandfill1;
    GeneralLandfill generalLandfill2;

}


//Neighborhoods are composite objects, they have streets in them
class Neighborhood extends LocationContainer{

}

