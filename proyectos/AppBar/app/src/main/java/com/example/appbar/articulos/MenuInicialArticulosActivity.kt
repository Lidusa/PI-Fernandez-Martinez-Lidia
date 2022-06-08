package com.example.appbar.articulos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbar.MainActivity
import com.example.appbar.R
import com.example.appbar.databinding.ActivityMenuInicialArticulosBinding

class MenuInicialArticulosActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuInicialArticulosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInicialArticulosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle(R.string.titulo_menu_inicial_articulos)
        setListeners()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método para dar funcionalidad a los botones
     */
    private fun setListeners() {
        binding.btnAccederArticulos.setOnClickListener {
            listaArticulos()
        }

        binding.btnCrearArticulos.setOnClickListener {
            crearArticulos()
        }

        binding.btnArticulosSalir.setOnClickListener {
            irMenuInicial()
        }
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que nos lleva al Activity donde se crean los artículos
     */
    private fun crearArticulos() {
        val i = Intent (this, CrearArticulosActivity::class.java)
        startActivity(i)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que nos lleva al Activity donde se encuentran la lista de artículos creados
     */
    private fun listaArticulos() {
        val i = Intent(this,RecyclerArticulosActivity::class.java)
        startActivity(i)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que nos lleva al menú principal de la aplicación
     */
    private fun irMenuInicial() {
        val i = Intent (this, MainActivity::class.java)
        startActivity(i)
    }

//---------------------------------------------------------------------------------------------------

}