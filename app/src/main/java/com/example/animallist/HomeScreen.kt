package com.example.animallist

import Adapter.ListDataRVAdapter
import Database.GlobalVar
import Interface.cardListener
import Model.Animal
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animallist.databinding.ActivityHomeScreenBinding
import com.google.android.material.snackbar.Snackbar


class HomeScreen : AppCompatActivity(), cardListener {

    private lateinit var viewBinding: ActivityHomeScreenBinding
    private val listDataAnimal = ArrayList<Animal>()
    private val adapter = ListDataRVAdapter(GlobalVar.listDataAnimal, this)
    private var jml: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        checkPermissions()
        addListener()
        setupRecyclerView()
    }
    override fun onResume() {
        super.onResume()
        jml = GlobalVar.listDataAnimal.size
        if(jml == 0)
        {
            viewBinding.textView4.alpha = 1f
        }else
        {
            viewBinding.textView4.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }

    private fun addListener(){
        viewBinding.floatingActionButton.setOnClickListener {
            val myIntent = Intent(this, AddUpAnimal::class.java)
            startActivity(myIntent)
        }
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(baseContext)
        viewBinding.recyclerView.layoutManager =layoutManager
        viewBinding.recyclerView.adapter = adapter
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), GlobalVar.STORAGEWrite_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Write Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GlobalVar.STORAGERead_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Read Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GlobalVar.STORAGERead_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Read Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == GlobalVar.STORAGEWrite_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Write Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Write Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCardClick(namatombol: String, position: Int) {
        if(namatombol == "edit"){
            val myIntent =Intent(this, AddUpAnimal::class.java).apply {
                putExtra("position", position)
            }
            startActivity(myIntent)
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Hapus Data Hewan")
            builder.setMessage("Apakah kamu yakin untuk menghapus data hewan ini?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { function, which ->
                Toast.makeText(this,"Data Hewan Berhasil Terhapus", Toast.LENGTH_SHORT).show()
                GlobalVar.listDataAnimal.removeAt(position)

                adapter.notifyDataSetChanged()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(this,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()

        }
    }

}