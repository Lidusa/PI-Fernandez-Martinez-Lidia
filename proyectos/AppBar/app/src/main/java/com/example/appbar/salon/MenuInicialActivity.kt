package com.example.appbar.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbar.MainActivity
import com.example.appbar.R
import com.example.appbar.databinding.ActivityMenuInicialBinding


class MenuInicialActivity : AppCompatActivity() {
    lateinit var binding: com.example.appbar.databinding.ActivityMenuInicialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setTitle(getString(R.string.titulo_menu_inicial))
    }

    /**
     * Método para dar funcionalidad a los botones
     */
    private fun setListeners() {
        binding.btnCrear.setOnClickListener {
            val i = Intent(this, CrearSalonActivity::class.java)
            startActivity(i)
        }

        binding.btnAcceder.setOnClickListener {
            listaSalones()
        }

        binding.btnGestSalonSalir.setOnClickListener {
            irMenuInicial()
        }
    }

    /**
     * Método que nos lleva al menú principal de la aplicación
     */
    private fun irMenuInicial() {
        val i = Intent (this, MainActivity::class.java)
        startActivity(i)
    }

    /**
     * Método que nos lleva al Activity donde se ve la lista de los salones mesas creadas
     */
    private fun listaSalones() {
        val i = Intent (this, RecyclerSalonActivity::class.java)
        startActivity(i)
    }

}