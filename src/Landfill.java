
//Abstract factory
public class Landfill {

}

//Concrete factory 1
class MedicalLandfill extends Landfill {

}

//Concrete factory 2
class GeneralLandfill extends Landfill {

}

//Abstract Product
abstract class Waste{

}

//Abstract Product A
abstract class RecyclableWaste extends Waste{

}

//Concrete Product A1
class RecyclableMedicalWaste extends RecyclableWaste{

}

//Concrete Product A2
class RecyclableGeneralWaste extends  RecyclableWaste{

}

//Abstract Product B
abstract class NonRecyclableWaste{

}

//Concrete Product B1
class NonRecyclableMedicalWaste extends NonRecyclableWaste{

}

//Concrete Product B2
class NonRecyclableGeneralWaste extends  NonRecyclableWaste{

}



