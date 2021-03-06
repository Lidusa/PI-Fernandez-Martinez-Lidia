package com.example.appbar.recycler_pedidos

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
//Datos que queremos almacenar de los pedidos
data class DatosPedido (val id: Int?=null, val nombreSalon: String?=null, val nombreArticulo: String?=null, val precioArticulo: Double?=null, val cantidadArticulo: Int?=null):
    Serializable{

    }
