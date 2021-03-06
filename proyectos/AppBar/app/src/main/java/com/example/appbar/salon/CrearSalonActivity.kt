package com.example.appbar.salon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appbar.R
import com.example.appbar.clases_auxiliares.Prefs
import com.example.appbar.databinding.ActivityCrearSalonBinding
import com.example.appbar.recycler_salon.DatosSalon
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CrearSalonActivity : AppCompatActivity() {
    //Declaración de todas las variables necesarias
    lateinit var binding: com.example.appbar.databinding.ActivityCrearSalonBinding
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    lateinit var prefs : Prefs

    var id = 0
    var nombreSalon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearSalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Inicializamos las variables necesarias
        prefs = Prefs(this)
        //Colocamos los métodos
        setListeners()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para dar funcionalidad a los botones
     */
    private fun setListeners() {

        binding.btnGuardarSalon.setOnClickListener {
            guardarSalon()
        }
        binding.btnLimpiarSalon.setOnClickListener {
            limpiar()
        }
        binding.btnVolverSalon.setOnClickListener {
            onBackPressed()
        }

    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para colocar los campos en su estado por defecto
     */
    private fun limpiar() {
        binding.edtNombreSalon.setText("")
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que asigna los datos a las variables y guarda el artículo
     */
    private fun guardarSalon() {
        if (comprobarNombreSalon()){
            nombreSalon = binding.edtNombreSalon.text.toString().trim()
            guardarSalones()
            limpiar()
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que guarda el salon o mesa en la base de datos de Firebase
     */
    private fun guardarSalones() {
        val salon = DatosSalon (cogerContadorId(), nombreSalon)
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = db.getReference("salones")
        reference.child(id.toString()).setValue(salon).addOnSuccessListener {
            Toast.makeText(this, getString(R.string.salon_guardado), Toast.LENGTH_SHORT).show()
            prefs.guardarContadorIdSalon(id)
        }
            .addOnFailureListener {
                Toast.makeText(this, "ERROR:" + it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método creado para poder realizar ids autoincrementados
     * @return id
     */
    private fun cogerContadorId(): Int? {
        id = prefs.leerContadorIdSalon()
        id += 1
        return id
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que comprueba que el campo no esté vacío
     * @return true o false
     */
    private fun comprobarNombreSalon(): Boolean {
        var nombreSalones = binding.edtNombreSalon.text.toString().trim()
        if (nombreSalones.isNullOrEmpty()) {
            binding.edtNombreSalon.setError(getString(R.string.mensaje_error_nombre_salon))
            binding.edtNombreSalon.requestFocus()
            return false
        }
        return true
    }

//---------------------------------------------------------------------------------------------------

}