package com.example.appbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.appbar.articulos.MenuInicialArticulosActivity
import com.example.appbar.clases_auxiliares.Prefs
import com.example.appbar.clases_auxiliares.PrefsLogin
import com.example.appbar.databinding.ActivityMainBinding
import com.example.appbar.familias.MenuInicialFamiliasActivity
import com.example.appbar.pedido.RecyclerPedidosActivity
import com.example.appbar.salon.MenuInicialActivity
import com.example.appbar.tpv.RecyclerFamiliasTPVActivity
import com.example.appbar.tpv.RecyclerSalonesTPVActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    //Declaración de todas las variables necesarias
   lateinit var binding: ActivityMainBinding
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    var cantidadFamilias = 0
    var email = ""
    var id = 0
    var rol = -1
    var emailAcceso = ""
    lateinit var prefsLogin : PrefsLogin
    lateinit var prefs : Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Colocamos los métodos
        comprobarFamilias()
        prefsLogin = PrefsLogin(this)
        prefs = Prefs(this)
        Thread.sleep(500)
        setListeners()
        cogerRol()
        guardarDatos()
        visibilidad()
        setTitle(getString(R.string.titulo_aplicacion))
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que guarde el email con el que hemos accedido a la aplicación
     */
    private fun guardarDatos() {
        prefsLogin.guardarEmail(email)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que obtiene el rol de la base de datos que posee el usuario que ha accedido a la aplicación
     */
    private fun cogerRol() {
          val bundle = intent.extras
          email = bundle?.getString("EMAIL").toString()
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
            reference = db.getReference()
            reference.child("usuarios").addValueEventListener(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (ds in snapshot.children) {
                            rol = ds.child("rol").getValue().toString().toInt()
                            emailAcceso = ds.child("email").getValue().toString()
                            if (emailAcceso.equals(email)) {
                                prefsLogin.guardarRol(rol)
                                visibilidad()
                                return
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que según el rol que poseea el usuario, permite ver unas funciones de la aplicación u otras
     */
    private fun visibilidad() {
            var a = prefsLogin.leerRol()?.toInt()
            if (a != null) {
                rol = a.toInt()
            }

        if (rol == -1) {
            binding.btnArticulos.isVisible = false
            binding.btnFamilias.isVisible = false
            binding.btnGestSalones.isVisible = false
            binding.btnTpv.isVisible = false
            binding.btnPedido.isVisible = false
            binding.btnSalirInicio.isVisible = true
            binding.txtInicio.isVisible = true
        } else if (rol == 0) {
            binding.btnArticulos.isVisible = false
            binding.btnFamilias.isVisible = false
            binding.btnGestSalones.isVisible = false
            binding.btnTpv.isVisible = true
            binding.btnPedido.isVisible = true
            binding.btnSalirInicio.isVisible = true
            binding.txtInicio.isVisible = false
        } else if (rol == 1) {
            binding.btnArticulos.isVisible = true
            binding.btnFamilias.isVisible = true
            binding.btnGestSalones.isVisible = true
            binding.btnTpv.isVisible = true
            binding.btnPedido.isVisible = true
            binding.btnSalirInicio.isVisible = true
            binding.txtInicio.isVisible = false
        }

    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que comprueba si existen familias
     * De esta manera, si no existen no se permitirá crear ningún artículo
     * @return cantidadFamilias
     */
    private fun comprobarFamilias(): Int {
        db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = db.getReference("familias")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    cantidadFamilias = 1
                } else {
                    cantidadFamilias = -1
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        return cantidadFamilias
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que da funcionalidad a los botones
     */
    private fun setListeners() {
        binding.btnFamilias.setOnClickListener {
            val i = Intent(this, MenuInicialFamiliasActivity::class.java )
            startActivity(i)
        }

        binding.btnArticulos.setOnClickListener {
            if (cantidadFamilias == 1) {
                val i = Intent(this,MenuInicialArticulosActivity::class.java)
                startActivity(i)

            } else{
                noFamilias()
            }

        }

        binding.btnTpv.setOnClickListener {
            val i = Intent(this, RecyclerSalonesTPVActivity::class.java )
            startActivity(i)
        }

        binding.btnGestSalones.setOnClickListener {
             val i = Intent(this, MenuInicialActivity::class.java )
            startActivity(i)
        }

        binding.btnSalirInicio.setOnClickListener {
            prefsLogin.borrarTodo()
            salir()
        }

        binding.btnPedido.setOnClickListener {
            val i = Intent (this, RecyclerPedidosActivity::class.java)
            startActivity(i)
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que pregunta al usuario sei quiere salir de la aplicación
     * Si lo hace se cerrará su sesión
     */
    private fun salir() {
      val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.titulo_salir_app)
            .setMessage(R.string.salir_app)
            .setNegativeButton(R.string.no_borrar_salon){v,_->v.dismiss()
            }
            .setPositiveButton(R.string.si_borrar_salon) { _, _ ->
                FirebaseAuth.getInstance().signOut()
                prefsLogin.borrarTodo()
                finishAffinity()
            }
            .setCancelable(false)
            .create()
            .show()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Mensaje de advertencia que aprece si no existen familias
     */
    private fun noFamilias() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.titulo_apartado_articulos)
            .setMessage(R.string.mensaje_error_no_hay_familias)
            .setNegativeButton(R.string.BotonTipoMesa){v,_ ->v.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }

//---------------------------------------------------------------------------------------------------

}