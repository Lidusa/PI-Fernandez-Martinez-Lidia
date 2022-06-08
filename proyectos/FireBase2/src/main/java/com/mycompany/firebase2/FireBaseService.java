/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.firebase2;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase con la que se realiza la conexión a la base de datos RealTimeDatabase de Firebase
 * @author Lidia Fernández Martínez
 */
public class FireBaseService {
    FirebaseDatabase db;

    /**
     * Método que realiza la conexión utilizando la ruta del archivo JSON de Firebase,
     * pasando a las credenciales la dirección de la base de datos y abriendo una instancia a esta.
     * @throws IOException
     */
    public FireBaseService() throws IOException {
        
        String url = "/datos/proyectofinal.json";
        
        InputStream serviceAccount = null;
            serviceAccount = InterfazPrincipal.class.getResourceAsStream(url);

 
       FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://aplicacion-proyecto-e79a2-default-rtdb.europe-west1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance();
    }

    /**
     * Método get de la variable db
     * @return la base de datos
     */
    public FirebaseDatabase getDb() {
        return db;
    }
}
