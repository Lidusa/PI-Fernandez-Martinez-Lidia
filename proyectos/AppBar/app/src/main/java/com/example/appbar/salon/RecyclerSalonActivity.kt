package com.example.appbar.salon

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
import com.example.appbar.databinding.ActivityRecyclerSalonBinding
import com.example.appbar.recycler_salon.DatosSalon
import com.example.appbar.recycler_salon.SalonAdapter
import com.google.firebase.database.*

class RecyclerSalonActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerSalonBinding

    //Declaramos las variables necesarias
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    lateinit var listaSalones: MutableList<DatosSalon>
    var contador = 0
    var elemento = 0
    lateinit var prefs : Prefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerSalonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Se inicializan las variables necesarias
        listaSalones = mutableListOf<DatosSalon>()
        prefs = Prefs(this)

        //Se utilizan los métodos
        setRecycler()
        swipeRecycler()
        rellenarLista()

    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para guardar el id del salon o mesa
     */
    private fun guardarElementos() {
        prefs.guardarContadorIdSalon(elemento)
    }


//---------------------------------------------------------------------------------------------------

    /**
     * Se establece el recycler
     */
    private fun setRecycler() {
        binding.recyclerViewSalonesTPV.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSalonesTPV.setHasFixedSize(true)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para rellenar la lista de salones o mesas en el recycler a partir de los datos de la base de datos
     */
    private fun rellenarLista() {
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = db.getReference("salones")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaSalones.clear()
                if (snapshot.exists()){
                    for (item in snapshot.children){
                        val salon = item.getValue(DatosSalon::class.java)
                        if (salon != null) {
                            listaSalones.add(salon)
                            elemento = salon?.id!!
                        }
                        guardarElementos()
                    }

                    binding.recyclerViewSalonesTPV.adapter = SalonAdapter(listaSalones)

                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que aplica funcionalidad a cada elemento del recycler al deslizarlo hacia la izquierda y la derecha
     * Hacia la izquierda se elimina el salón o mesa del recycler y de la base de datos
     * Hacia la derecha permite ir a ModificarSalonActivity y se lleva los datos necesarios
     */
    private fun swipeRecycler() {
        val itemTouch = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or (ItemTouchHelper.RIGHT)){
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
                    val alertDialog = AlertDialog.Builder(this@RecyclerSalonActivity)
                        .setTitle(R.string.titulo_borrar_salon)
                        .setMessage(R.string.mensaje_borrar_salon)
                        .setNegativeButton(R.string.no_borrar_salon){v,_->v.dismiss()
                            setRecycler()
                        }
                        .setPositiveButton(R.string.si_borrar_salon) { _, _ ->
                            //Borramos el elemento, hemos realizado el método removeAt en el Adapter
                            val position = viewHolder.adapterPosition
                            val adapter = binding.recyclerViewSalonesTPV.adapter as SalonAdapter
                            adapter.removeAt(position)
                            guardarElementos()
                            setRecycler()



                            if ((listaSalones.isNullOrEmpty() || listaSalones.size == 1) && contador == 0) {
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
                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val elementoLista = listaSalones[position]

                        val i = Intent (this@RecyclerSalonActivity, ModificarSalonActivity::class.java).apply {
                            putExtra("idSalon", elementoLista.id)
                            putExtra("nombreSalon", elementoLista.nombreSalon)
                        }
                        startActivity(i)
                    }

                    else ->{
                }
            }
        }

    }
    val ith = ItemTouchHelper(itemTouch)
    ith.attachToRecyclerView(binding.recyclerViewSalonesTPV)
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
                val i = Intent (this, MenuInicialActivity::class.java)
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
     * Permite confirmar si se quiere borrar el salon
     */
    private fun confirmarBorrado() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.titulo_borrar_salon)
            .setMessage(R.string.mensaje_borrar_salon)
            .setNegativeButton(R.string.no_borrar_salon){v,_->v.dismiss()
            }
            .setPositiveButton(R.string.si_borrar_salon) { _, _ ->
                finishAffinity()
            }
            .setCancelable(false)
            .create()
            .show()
    }

//---------------------------------------------------------------------------------------------------

}