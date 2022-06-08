package com.example.appbar.clases_auxiliares

import android.content.Context

class PrefsLogin (val c: Context) {

    //Se declaran todas las variables necesarias
    val FICHERO = "ficheroDatos"
    val EMAIL = "email"
    val ROL = "rol"
    val storage  = c.getSharedPreferences(FICHERO, 0)

//---------------------------------------------------------------------------------------------------

    /**
     * Método que guarda el email con el que se accede a la aplicación
     */
    public fun guardarEmail(email: String){
        storage.edit().putString(EMAIL, email).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el email con el que se accede a la aplicación
     */
    public fun leerEmail() : String?{
        return storage.getString(EMAIL, null)
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que guarda el rol con el que se accede a la aplicación
     */
    public fun guardarRol(rol: Int){
        storage.edit().putInt(ROL, rol).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el rol con el que se accede a la aplicación
     */
    public fun leerRol() : Int?{
        return storage.getInt(ROL, 0)!!
    }
//---------------------------------------------------------------------------------------------------

    /**
     * Método que borra todas las preferencias
     */
    public fun borrarTodo(){
        storage.edit().clear().apply()
    }

//---------------------------------------------------------------------------------------------------

}