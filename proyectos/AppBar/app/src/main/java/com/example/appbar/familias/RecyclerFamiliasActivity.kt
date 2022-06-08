package com.example.appbar.familias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbar.MainActivity
import com.example.appbar.R
import com.example.appbar.clases_auxiliares.Prefs
import com.example.appbar.databinding.ActivityRecyclerFamiliasBinding
import com.example.appbar.recycler_articulos.DatosArticulo
import com.example.appbar.recycler_familias.DatosFamilia
import com.example.appbar.recycler_familias.FamiliaAdapter
import com.google.firebase.database.*

class RecyclerFamiliasActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerFamiliasBinding

    //Declaramos las variables necesarias
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    lateinit var listaFamilias: MutableList<DatosFamilia>
    lateinit var idFamiliaArticuloList: MutableList<Int>
    var contador = 0
    var elemento = 0
    var sw = false
    lateinit var prefs : Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerFamiliasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Se inicializan las variables necesarias
        listaFamilias = mutableListOf<DatosFamilia>()
        idFamiliaArticuloList = mutableListOf<Int>()
        prefs = Prefs(this)
        //Se utilizan los métodos
        cogerIdFamiliaDeArticulo()
        setRecycler()
        swipeRecycler()
        rellenarLista()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para obtener el id de la familia a la que está asociado el artículo
     * Se realiza con el fin de poder comprobar si una familia tiene artículos asociados
     */
    private fun cogerIdFamiliaDeArticulo(): Int {
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = db.getReference("articulos")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (item in snapshot.children){
                        val articulo = item.getValue(DatosArticulo::class.java)
                        if (articulo != null) {
                            idFamiliaArticuloList.add(articulo.idFamilia!!)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return -99
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para guardar el id de la familia
     */
    private fun guardarElementos() {
        prefs.guardarContadorIdFamilia(elemento)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para rellenar la lista de familias en el recycler a partir de los datos de la base de datos
     */
    private fun rellenarLista() {
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = db.getReference("familias")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaFamilias.clear()
                if (snapshot.exists()){
                    //Recorremos los datos
                    for (item in snapshot.children){
                        val familia = item.getValue(DatosFamilia::class.java)
                        if (familia != null) {
                            listaFamilias.add(familia)
                            elemento = familia?.id!!
                        }
                        guardarElementos()
                    }

                    binding.recyclerViewFamilias.adapter = FamiliaAdapter(listaFamilias)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que aplica funcionalidad a cada elemento del recycler al deslizarlo hacia la izquierda y la derecha
     * Hacia la izquierda se elimina la familia del recycler y de la base de datos en el caso de que no tenga
     * artículos asociados, sino no permitirá hacer el borrado
     * Hacia la derecha permite ir a ModificarFamiliaActivity y se lleva los datos necesarios
     */
    private fun swipeRecycler() {
        val itemTouch = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or (ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction){
                    ItemTouchHelper.LEFT -> {
                        val alertDialog = AlertDialog.Builder(this@RecyclerFamiliasActivity)
                            .setTitle(R.string.titulo_borrar_familia)
                            .setMessage(R.string.mensaje_borrar_familia)
                            .setNegativeButton(R.string.no_borrar_salon){v,_->v.dismiss()
                                setRecycler()
                            }
                            .setPositiveButton(R.string.si_borrar_salon) { _, _ ->
                                //Borramos el elemento, hemos realizado el método removeAt en el Adapter
                                val position = viewHolder.adapterPosition
                                val adapter = binding.recyclerViewFamilias.adapter as FamiliaAdapter
                                var idFamilia = adapter.cogerId(position)
                                for (i in idFamiliaArticuloList.indices) {
                                    if (idFamilia == idFamiliaArticuloList[i]) {
                                        sw = true
                                        Atencion()
                                    }
                                }
                                if (sw == false) {
                                    adapter.removeAt(position)
                                }
                                guardarElementos()
                                setRecycler()


                                if ((listaFamilias.isNullOrEmpty() || listaFamilias.size == 1) && contador == 0 && sw == false) {
                                    while (contador == 0) {
                                        startActivity(intent)
                                        contador += 1
                                    }
                                }



                            }
                            .setCancelable(false)
                            .create()
                            .show()
                    }

                    ItemTouchHelper.RIGHT->{
                        val position = viewHolder.adapterPosition
                        val elementoLista = listaFamilias[position]

                        val i = Intent(this@RecyclerFamiliasActivity,ModificarFamiliasActivity::class.java).apply {
                            putExtra("idFamilia",elementoLista.id)
                            putExtra("nombreFamilia",elementoLista.nombreFamilia)
                            putExtra("imagen", elementoLista.urlImagenFamilia)
                        }
                        startActivity(i)
                    }

                    else ->{

                    }
                }
            }

        }
        val ith = ItemTouchHelper(itemTouch)
        ith.attachToRecyclerView(binding.recyclerViewFamilias)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Se establece el recycler
     */
    private fun setRecycler() {
        binding.recyclerViewFamilias.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFamilias.setHasFixedSize(true)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Métodos para crear un menú en la parte superior de la pantalla
     * Se podrá volver a la pantalla anterior o directamente al menú principal
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_recycler_salones, menu)
        return true
    }

//---------------------------------------------------------------------------------------------------

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.volver -> {
                val i = Intent (this, MenuInicialFamiliasActivity::class.java)
                startActivity(i)
                true
            }

            R.id.ir_menu_inicial -> {
                val i = Intent (this, MainActivity::class.java)
                startActivity(i)
                true
            }

            else -> {
                true
            }
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Se actualiza el recycler
     */
    override fun onRestart() {
        super.onRestart()
        rellenarLista()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Mensaje de error que aparece si la familia tiene algún artículo asociado, no permitiendo
     * borrar la familia
     */
    private fun Atencion() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.titulo_atencion_familia)
            .setMessage(R.string.mensaje_atencion_borrar_familia)
            .setNegativeButton(R.string.BotonTipoMesa){v,_->v.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }

//---------------------------------------------------------------------------------------------------
}