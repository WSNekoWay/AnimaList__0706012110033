package Database

import Model.Animal

class GlobalVar {
    companion object{
        val STORAGERead_PERMISSION_CODE: Int = 2
        val STORAGEWrite_PERMISSION_CODE: Int = 3
        val listDataAnimal = ArrayList<Animal>()
    }
}