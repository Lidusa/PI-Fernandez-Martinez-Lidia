package com.example.appbar.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appbar.MainActivity
import com.example.appbar.R
import com.example.appbar.clases_auxiliares.Prefs
import com.example.appbar.clases_auxiliares.PrefsLogin
import com.example.appbar.databinding.ActivityLoginBinding
import com.example.appbar.recycler_user.DatosUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {
    //Declaración de todas las variables necesarias
    lateinit var binding: ActivityLoginBinding
    private lateinit var db : FirebaseDatabase
    private lateinit var reference : DatabaseReference
    lateinit var idLista: MutableList<Int>
    lateinit var emailLista: MutableList<String>
    var email = ""
    var contrasena = ""
    lateinit var prefs : PrefsLogin
    lateinit var prefs2 : Prefs
    var id = 0
    var rol = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Inicializamos las variables necesarias
        prefs = PrefsLogin(this)
        prefs2 = Prefs(this)
        emailLista = mutableListOf()
        idLista = mutableListOf()
        //Colocamos los métodos
        setUp()
        comprobarSesion()
        title = "Autentificación"

    }

//---------------------------------------------------------------------------------------------

    /**
     * Método para guardar el usuario en la base de datos de Firebase
     */
    private fun saveuser() {
            val user = DatosUser (cogerContadorId(), email, contrasena, rol)
            db = FirebaseDatabase.getInstance("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app/")
            reference = db.getReference("usuarios")
            reference.child(id.toString()).setValue(user).addOnSuccessListener {
                Toast.makeText(this, getString(R.string.user_guardado), Toast.LENGTH_SHORT).show()
                prefs2.guardarContadorIdUser(id)
            }
                .addOnFailureListener {
                    Toast.makeText(this, "ERROR:" + it.message.toString(), Toast.LENGTH_SHORT).show()
                }
    }

//---------------------------------------------------------------------------------------------

    /**
     * Método creado para poder realizar ids autoincrementados
     * @return id
     */
    private fun cogerContadorId(): Int {
        id = prefs2.leerContadorIdUser()
        id += 1
        return id
    }

//---------------------------------------------------------------------------------------------

    /**
     * Método que lee con que usuario accedemos a la aplicación
     */
    private fun comprobarSesion() {
        val e = prefs.leerEmail()


        if (!e.isNullOrEmpty()) {
            irApp(e)
        }
    }
//---------------------------------------------------------------------------------------------------

    /**
     * Método para dar funcionalidad a los botones
     */
    private fun setUp() {
        binding.btnRegistrarse.setOnClickListener {
            registrar()
        }

        binding.btnAcceder.setOnClickListener {
            acceder()
        }

        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que registra en el Authentication de Firebase al usuario
     */
    private fun registrar() {
        if (!cogerDatos()) return
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener {
            if (it.isSuccessful) {
                irApp(it.result?.user?.email?:"")
                saveuser()
            } else {
                Log.d("::::ERRORRegistrar", it.exception.toString())
                mostrarError()
            }
        }
    }
//---------------------------------------------------------------------------------------------------

    /**
     * Método que nos lleva al menú principal con los datos del usuario
     */
    private fun irApp(email: String) {
        val i = Intent(this, MainActivity::class.java).apply {
            putExtra("EMAIL",email)
            putExtra("ROL", rol)
        }
        startActivity(i)
    }
//-----------------------------------------------------------------------------------------------

    /**
     * Método que coloca los campos por defecto
     */
    private fun limpiar() {
        binding.etMail.setText("")
        binding.etContrasena.setText("")
        binding.etMail.requestFocus()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que permite acceder a la aplicación a los usuarios ya registrados
     */
    private fun acceder() {
        if (!cogerDatos()) return
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contrasena).addOnCompleteListener {
            if (it.isSuccessful) {
                irApp(it.result?.user?.email?:"")
            } else {
                Log.d("::::ERRORAcceder", it.exception.toString())
                mostrarErrorAcceso()
            }
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Comprueba que los campos no estan vacíos
     * @return true o false
     */
    private fun cogerDatos(): Boolean {
        email = binding.etMail.text.toString().trim()
        contrasena = binding.etContrasena.text.toString().trim()

        if (email.length == 0) {
            binding.etMail.setError(getString(R.string.email_error))
            return false
        }

        if (contrasena.length == 0) {
            binding.etContrasena.setError(getString(R.string.contrasena_error))
            return false
        }
        return true
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Mensaje de error que se muestra si los datos introducidos no son correctos
     */
    private fun mostrarError() {
        val alerta = AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(getString(R.string.error_mensaje))
            .setPositiveButton(getString(R.string.aceptar), null)
            .create()
            .show()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Mensaje de error que se muestra si ha habido un error al identificarse
     */
    private fun mostrarErrorAcceso() {
        val alerta = AlertDialog.Builder(this)
            .setTitle(getString(R.string.error_autenticacion))
            .setMessage(getString(R.string.error_identificacion))
            .setPositiveButton(getString(R.string.aceptar), null)
            .create()
            .show()
    }

//---------------------------------------------------------------------------------------------------

}