package com.example.appbar.articulos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.appbar.R
import com.example.appbar.databinding.ActivityModificarArticulosBinding
import com.example.appbar.recycler_articulos.DatosArticulo
import com.example.appbar.recycler_familias.DatosFamilia
import com.google.firebase.database.*

class ModificarArticulosActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityModificarArticulosBinding

    //Se declaran todas las variables necesarias
    private lateinit var db: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    lateinit var adapterTipoArticulo: ArrayAdapter<String>
    lateinit var listaFamiliasArticulo : MutableList<String>
    lateinit var listaImagenes : MutableList<Int>
    lateinit var listaIdFamilia : MutableList<Int>

    var idArticulo = 0
    var nombreArticulo = ""
    var precioArticulo = 0.0
    var idFamilia = 0
    var urlFamiliaParaArticulo = 0
    var urlImagenFamilia = 0
    var contador = 0
    var sw = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarArticulosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Se inicializan las variables necesarias
        listaFamiliasArticulo = mutableListOf<String>()
        listaImagenes = mutableListOf<Int>()
        listaIdFamilia = mutableListOf<Int>()
        //Se emplean los métodos
        cogerDatos()
        setListeners()
        cargarFamilias()
//---------------------------------------------------------------------------------------------------

        //Se inicializa el spinner con la familia a la que pertenece el artículo
        adapterTipoArticulo = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, listaFamiliasArticulo
            )
    }

//---------------------------------------------------------------------------------------------------

    /**
     * /Método que se utiliza para poder recoger los datos de las familias en la base de datos y poder asignar la imagen
     * de esta familia al artículo
     */
    private fun cargarFamilias() {
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = db.getReference()
        reference.child("familias").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ds in snapshot.children) {
                        var idDeFamilia = ds.child("id").getValue().toString().toInt()
                        var nombreFamilia = ds.child("nombreFamilia").getValue().toString()
                        urlImagenFamilia = ds.child("urlImagenFamilia").getValue().toString().toInt()
                        var familia = DatosFamilia(idDeFamilia, nombreFamilia, urlImagenFamilia)
                        listaFamiliasArticulo.add(nombreFamilia)
                        listaImagenes.add(urlImagenFamilia)
                        listaIdFamilia.add(idDeFamilia)
                        if (contador == 0) {
                            familia.urlImagenFamilia?.let {
                                binding.imgFamiliaArticuloModificar.setImageResource(
                                    it
                                )
                                binding.imgFamiliaArticuloModificar.isVisible = true
                                contador ++
                            }
                        }

                    }

                  binding.spArticuloModificar.adapter = adapterTipoArticulo
                  binding.spArticuloModificar.onItemSelectedListener = this@ModificarArticulosActivity
               }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


//---------------------------------------------------------------------------------------------------

    /**
     * Método para obtener los datos del artículo seleccionado desde el RecyclerArticulosActivity y asignarselos
     * a los campos
     */
    private fun cogerDatos() {
        val bundle = intent.extras
        idArticulo = bundle?.getInt("idArticulo", 0)!!
        nombreArticulo = bundle?.getString("nombreArticulo").toString()
        precioArticulo = bundle?.getDouble("precioArticulo", 0.0)!!
        idFamilia = bundle?.getInt("idFamilia", 0)!!
        urlFamiliaParaArticulo = bundle?.getInt("urlFamiliaParaArticulo", 0)!!

        binding.edtNombreArticuloModificar.setText(nombreArticulo)
        binding.edtPrecioArticuloModificar.setText(precioArticulo.toString())
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para dar funcionalidad a los botones
     */
    private fun setListeners() {
        binding.btnGuardarArticuloModificar.setOnClickListener {
            modificarArticulo()
        }
        binding.btnVolverArticuloModificar.setOnClickListener {
            onBackPressed()
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que comprueba que los campos no estan vacíos , asigna los nuevos datos al artículo y los modifica
     * en la base de datos
     */
    private fun modificarArticulo() {
        if (comprobarNombreArticulo() && comprobarPrecioArticulo()) {
            nombreArticulo = binding.edtNombreArticuloModificar.text.toString().trim()
            precioArticulo = binding.edtPrecioArticuloModificar.text.toString().toDouble()

            val articulo = DatosArticulo(
                idArticulo,
                idFamilia,
                nombreArticulo,
                precioArticulo,
                urlFamiliaParaArticulo
            )
            db =
                FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
            reference = db.getReference("articulos")
            reference.child(idArticulo.toString()).setValue(articulo).addOnSuccessListener {
                Toast.makeText(this, getString(R.string.articulo_modificado), Toast.LENGTH_SHORT)
                    .show()
            }
                .addOnFailureListener {
                    Toast.makeText(this, "ERROR:" + it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Comprueba que el campo no está vacio
     * @return true o false
     */
    private fun comprobarNombreArticulo(): Boolean {
        var nombreArticulo = binding.edtNombreArticuloModificar.text.toString().trim()
        if (!comprobarView(binding.edtNombreArticuloModificar, nombreArticulo)) {
            return false
        }
        return true
    }


//---------------------------------------------------------------------------------------------------

    /**
     * Comprueba que el campo no está vacio
     * @return true o false
     */
    private fun comprobarPrecioArticulo(): Boolean {
        var precioArticulo = binding.edtPrecioArticuloModificar.text.toString().trim()
        if (precioArticulo.isNullOrEmpty()) {
            binding.edtPrecioArticuloModificar.setError(getString(R.string.mensaje_error_precio_articulo_modificar))
            binding.edtPrecioArticuloModificar.requestFocus()
            return false
        }
        return true
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Mensaje de error que aparece si el campo está vacío
     * @return true o false
     */
    private fun comprobarView(et: EditText, cad: String): Boolean {
        if (cad.isNullOrEmpty()) {
            et.setError(getString(com.example.appbar.R.string.mensaje_error_nombre_articulo_modificar))
            et.requestFocus()
            return false
        }
        return true
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método del spinner que permite colocar desde un inicio la imagen y la familia a la que pertence el artículo
     * antes de su modificación y colocar la nueva familia e imagen
     */
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0) {
            binding.spArticuloModificar -> {
                for (i in listaImagenes.indices) {
                        if (listaImagenes[i] == urlFamiliaParaArticulo && sw == false) {
                            binding.imgFamiliaArticuloModificar.setImageResource(urlFamiliaParaArticulo)
                            binding.imgFamiliaArticuloModificar.isVisible = true
                            p0.setSelection(i)
                            sw = true
                        }

                        if (p0.selectedItemPosition == i && sw == true) {
                            var imagen = listaImagenes.get(i)
                            binding.imgFamiliaArticuloModificar.setImageResource(imagen)
                            idFamilia = listaIdFamilia.get(i)
                            urlFamiliaParaArticulo = listaImagenes.get(i)
                            p0.setSelection(i)
                        }
                    }
            }
       }
    }

//---------------------------------------------------------------------------------------------------

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

//---------------------------------------------------------------------------------------------------

}