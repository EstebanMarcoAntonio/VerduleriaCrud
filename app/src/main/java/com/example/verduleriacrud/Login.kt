package com.example.verduleriacrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.verduleriacrud.Crud.CrudVerduras

class Login : AppCompatActivity() {
    var txtUsuario: EditText?=null
    var txtContrasena: EditText?=null
    var prueba: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            val BtnRegistrarse: Button = findViewById(R.id.BtnRegistrarse)
        BtnRegistrarse.setOnClickListener{
            val intent: Intent = Intent(this, Register:: class.java)
            startActivity(intent)
        }
        txtUsuario=findViewById(R.id.txtUsuario)
        txtContrasena=findViewById(R.id.txtContrasena)

    }
    fun BtnIngresar(view:View) {
        val url = "http://192.168.1.26:9060/Login/ConfirmarLogin"
        val queue = Volley.newRequestQueue(this)

        val resultadoPost = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                // Manejar la respuesta exitosa
                Toast.makeText(this, "Login correcto", Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(this, CrudVerduras::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { error ->
                // Manejar el error
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    val errorBody = String(error.networkResponse.data)
                    Toast.makeText(this, "Error, correo o contraseña incorrecto", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_LONG).show()
                }
            }) {
            override fun getParams(): MutableMap<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("correo", txtUsuario?.text.toString())
                parametros.put("contrasena", txtContrasena?.text.toString())
                return parametros
            }
        }

        queue.add(resultadoPost)


    }
}