package com.example.appbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appbar.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Pantalla inicial que no aparecerá mientras carga la aplicación
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}