package com.example.appbar.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.appbar.MainActivity
import com.example.appbar.R
import com.example.appbar.databinding.ActivityModificarSalonBinding
import com.example.appbar.recycler_salon.DatosSalon
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ModificarSalonActivity : AppCompatActivity() {

    //Se declaran todas las variables necesarias
    lateinit var binding: ActivityModificarSalonBinding
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    var idSalon = 0
    var nombreSalon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarSalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Se emplean los métodos
        cogerDatos()
        setListener()
    }

//---------------------------------------------------------------------------------------------------
    /**
     * Método para obtener los datos del salon o mesa seleccionado desde el RecyclerSalonActivity y asignarselos
     * a los campos
     */
    private fun cogerDatos() {
        val bundle = intent.extras
        idSalon  = bundle?.getInt("idSalon", 0)!!
        nombreSalon = bundle?.getString("nombreSalon").toString()

        binding.edtNombreModificarSalon.setText(nombreSalon)
    }

//---------------------------------------------------------------------------------------------------
    /**
     * Método para dar funcionalidad a los botones
     */
    private fun setListener() {
        binding.btnVolverModificarSalon.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        binding.btnModificarSalon.setOnClickListener{
            modificarSalon()
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que cambia los valores del salon o mesa en la base de datos de Firebase
     */
    private fun modificarSalon() {
        if (comprobarNombreSalon()) {
            nombreSalon = binding.edtNombreModificarSalon.text.toString().trim()
            val salon  = DatosSalon(idSalon, nombreSalon)
            db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
            reference = db.getReference("salones")
            reference.child(idSalon.toString()).setValue(salon).addOnSuccessListener {
                Toast.makeText(this, getString(R.string.salon_modificado), Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(this, "ERROR:" + it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Comprueba que el campo no está vacio y asigna los datos a la variables
     * @return true o false
     */
    private fun comprobarNombreSalon(): Boolean {
        var nombreSalon = binding.edtNombreModificarSalon.text.toString().trim()
        if (comprobarView(binding.edtNombreModificarSalon, nombreSalon) == false) {
            return false
        }
        return true
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Mensaje de error que aparece si el campo está vacío
     * @return true o false
     */
    private fun comprobarView(et: EditText, cad: String): Any {
        if (cad.isNullOrEmpty()){
            et.setError(getString(com.example.appbar.R.string.mensaje_error_nombre_salon))
            et.requestFocus()
            return false
        }
        return true
    }

//---------------------------------------------------------------------------------------------------

}