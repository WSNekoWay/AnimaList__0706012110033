package com.example.animallist

import Database.GlobalVar
import Model.Animal
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.animallist.databinding.ActivityAddAnimalBinding

class AddUpAnimal : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAddAnimalBinding
    private lateinit var animal: Animal
    private var position = -1
    var image: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityAddAnimalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        GetIntent()
        Listener()
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if (position != -1){
            val animal = GlobalVar.listDataAnimal[position]
            viewBinding.toolbar.title = "Edit Animal Data"
            viewBinding.addButton.text = "Save"
            viewBinding.imageView.setImageURI(Uri.parse(GlobalVar.listDataAnimal[position].imageUri))
            viewBinding.namaTextInputLayout.editText?.setText(animal.nama)
            viewBinding.jenisTextInputLayout.editText?.setText(animal.jenis)
            viewBinding.umurTextInputLayout.editText?.setText(animal.usia)
        }

    }

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(uri != null){
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }}// GET PATH TO IMAGE FROM GALLEY
            viewBinding.imageView.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
            image = uri.toString()
        }
    }



    private fun Listener() {
        viewBinding.imageView.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBinding.addButton.setOnClickListener{
            var nama = viewBinding.namaTextInputLayout.editText?.text.toString().trim()
            var jenis = viewBinding.jenisTextInputLayout.editText?.text.toString().trim()
            var umur = viewBinding.umurTextInputLayout.editText?.text.toString().trim()


            animal = Animal(nama, jenis, umur, image.toUri().toString())
            checker()
        }
        viewBinding.toolbar.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker() {
        var isCompleted:Boolean = true

        if(animal.nama!!.isEmpty()){
            viewBinding.namaTextInputLayout.error = "Nama tidak boleh kosong!"
            isCompleted = false
        }else{
            viewBinding.namaTextInputLayout.error = ""
        }

        if(animal.jenis!!.isEmpty()){
            viewBinding.jenisTextInputLayout.error = "Jenis hewan tidak boleh kosong!"
            isCompleted = false
        }else{
            viewBinding.jenisTextInputLayout.error = ""
        }

        if(animal.usia!!.isEmpty()){
            viewBinding.umurTextInputLayout.error = "Usia hewan tidak boleh kosong!"
            isCompleted = false
        }else{
            viewBinding.umurTextInputLayout.error = ""
        }



        if(isCompleted)
        {
            if(position == -1)
            {
                animal.usia = viewBinding.umurTextInputLayout.editText?.text.toString()
                GlobalVar.listDataAnimal.add(animal)
                var index = GlobalVar.listDataAnimal.size - 1

                if (image.isNotEmpty()) {
                    GlobalVar.listDataAnimal[index].imageUri = image
                }
            }else
            {
                animal.nama = viewBinding.namaTextInputLayout.editText?.text.toString().trim()
                animal.jenis = viewBinding.jenisTextInputLayout.editText?.text.toString().trim()
                animal.usia = viewBinding.umurTextInputLayout.editText?.text.toString()
                GlobalVar.listDataAnimal[position]=animal
                if (image.isNotEmpty()) {

                    GlobalVar.listDataAnimal[position].imageUri = image
                }


            }
            finish()
        }
    }
}