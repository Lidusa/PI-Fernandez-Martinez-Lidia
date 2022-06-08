package com.example.appbar.clases_auxiliares

import android.content.Context

class Prefs (val c: Context) {
    //Se declaran todas las variables necesarias
    val FICHERO = "fichero"
    val CONTADORIDSALON = "contadorIdSalon"
    val CONTADORIDFAMILIA = "contadorIdFamilia"
    val CONTADORIDARTICULO = "contadorIdArticulo"
    val CONTADORIDUSER = "contadorIdUser"
    val CONTADORPEDIDOS = "contadorPedidos"
    val NOMBRESALONENLINEAPEDIDO = "nombresalonenlineapedido"
    val storage = c.getSharedPreferences(FICHERO, 0)

//---------------------------------------------------------------------------------------------------

    /**
     * Método que va guardando los ids
     */
   public fun guardarContadorIdSalon (contadorIdSalon : Int) {
        storage.edit().putInt(CONTADORIDSALON, contadorIdSalon).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que va guardando los ids
     */
    public fun guardarContadorIdFamilia (contadorIdFamilia : Int) {
        storage.edit().putInt(CONTADORIDFAMILIA, contadorIdFamilia).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que va guardando los ids
     */
    public fun guardarContadorIdArticulo(contadorIdArticulo : Int) {
        storage.edit().putInt(CONTADORIDARTICULO, contadorIdArticulo).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que va guardando los ids
     */
    public fun guardarContadorIdUser(contadorIdUser: Int) {
        storage.edit().putInt(CONTADORIDUSER, contadorIdUser).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que va guardando los ids
     */
    public fun guardarContadorPedidos (contadorPedidos : Int) {
        storage.edit().putInt(CONTADORPEDIDOS, contadorPedidos).apply()
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el último id
     */
    public fun leerContadorIdSalon (): Int {
        return storage.getInt(CONTADORIDSALON, 0)!!
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el último id
     */
    public fun leerContadorIdFamilia (): Int {
        return storage.getInt(CONTADORIDFAMILIA, 0)!!
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el último id
     */
    public fun leerContadorIdArticulo (): Int {
        return storage.getInt(CONTADORIDARTICULO, 0)!!
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el último id
     */
    public fun leerContadorIdUser (): Int {
        return storage.getInt(CONTADORIDUSER, 0)!!
    }

//---------------------------------------------------------------------------------------------------

    /**
     * Método que lee el último id
     */
    public fun leerContadorPedidos (): Int {
        return storage.getInt(CONTADORPEDIDOS, 0)!!
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