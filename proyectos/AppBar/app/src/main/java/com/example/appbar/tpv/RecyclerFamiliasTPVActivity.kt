package com.example.appbar.tpv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbar.MainActivity
import com.example.appbar.R
import com.example.appbar.databinding.ActivityRecyclerFamiliasTpvactivityBinding
import com.example.appbar.recycler_articulos.DatosArticulo
import com.example.appbar.recycler_familias.DatosFamilia
import com.example.appbar.recycler_familias.FamiliaAdapter
import com.google.firebase.database.*

class RecyclerFamiliasTPVActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerFamiliasTpvactivityBinding

    //Declaramos las variables necesarias
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    lateinit var listaFamilias: MutableList<DatosFamilia>
    var elemento = 0
    var idSalon = 0
    var nombreSalon  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerFamiliasTpvactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Se inicializan las variables necesarias
        listaFamilias = mutableListOf<DatosFamilia>()
        //Se utilizan los métodos
        cogerDatos()
        setRecycler()
        swipeRecycler()
        rellenarLista()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Cogemos los datos necesarios de el anterior RecyclerSalonesTPVActivity
     */
    private fun cogerDatos() {
        val bundle = intent.extras
        idSalon  = bundle?.getInt("idSalon", 0)!!
        nombreSalon = bundle?.getString("nombreSalon").toString()
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
                    }

                    binding.recyclerViewFamiliasTPV.adapter = FamiliaAdapter(listaFamilias)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que aplica funcionalidad a cada elemento del recycler al deslizarlo hacia la izquierda y la derecha
     * Hacia la izquierda nos lleva a RecyclerArticulosTPVActivity y coge los datos necesarios
     * Hacia la derecha nos lleva a RecyclerArticulosTPVActivity y coge los datos necesarios
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
                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val elementoLista = listaFamilias[position]

                        val i = Intent(
                            this@RecyclerFamiliasTPVActivity,
                            RecyclerArticulosTPVActivity::class.java
                        ).apply {
                            putExtra("idFamilia", elementoLista.id)
                            putExtra("idSalon", idSalon)
                            putExtra("nombreSalon", nombreSalon)
                        }
                        startActivity(i)
                    }
                    ItemTouchHelper.LEFT -> {
                        val position = viewHolder.adapterPosition
                        val elementoLista = listaFamilias[position]

                        val i = Intent(
                            this@RecyclerFamiliasTPVActivity,
                            RecyclerArticulosTPVActivity::class.java
                        ).apply {
                            putExtra("idFamilia", elementoLista.id)
                            putExtra("idSalon", idSalon)
                            putExtra("nombreSalon", nombreSalon)
                        }
                        startActivity(i)
                    }

                    else ->{

                    }
                }
            }

        }
        val ith = ItemTouchHelper(itemTouch)
        ith.attachToRecyclerView(binding.recyclerViewFamiliasTPV)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Se establece el recycler
     */
    private fun setRecycler() {
        binding.recyclerViewFamiliasTPV.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFamiliasTPV.setHasFixedSize(true)
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
                val i = Intent (this, RecyclerSalonesTPVActivity::class.java)
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
}