package com.example.verduleriacrud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Register : AppCompatActivity() {
    var txtDni:EditText?=null
    var txtCelular:EditText?=null
    var txtPassword:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val btn_Login: Button = findViewById(R.id.btn_Login)
        btn_Login.setOnClickListener{
            val intent: Intent = Intent(this, Login:: class.java)
            startActivity(intent)
        }
        txtDni=findViewById(R.id.txtDni)
        txtCelular=findViewById(R.id.txtCelular)
        txtPassword=findViewById(R.id.txtPassword)
    }

    fun CLickBtnInsertar(view:View) {
        var textoDNI = txtDni?.text.toString()
        var correo = textoDNI+"@gmail.com"
        val urele =
            "https://dniruc.apisperu.com/api/v1/dni/$textoDNI?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1" +
                    "NiJ9.eyJlbWFpbCI6ImphbG9uc29hYTIwMDRAZ21haWwuY29tIn0.pwhMFkSfGhnmUhUCeZzkMPpv27ihBDxUFErt1WErW6s"
        val url = "http://192.168.1.26:9060/Empleado/Insertar"
        val queue = Volley.newRequestQueue(this)

        val queu = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, urele, null,
            Response.Listener { responses ->
                if (responses.has("nombres")) {
                    val txtNombre = responses.getString("nombres")
                    val txtApellidos =
                        responses.getString("apellidoPaterno") + " " + responses.getString("apellidoMaterno")

                    if (!txtNombre.isNullOrEmpty() && !txtApellidos.isNullOrEmpty()) {
                        var resultadoPost = object : StringRequest(Method.POST, url,
                            Response.Listener<String> { response ->
                                Toast.makeText(
                                    this,
                                    "Usuario insertado exitosamente",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent: Intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            },
                            Response.ErrorListener { volleyError ->
                                var errorMessage = "Error desconocido"

                                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                                    val errorData = String(volleyError.networkResponse.data)

                                    if (errorData.contains("Duplicate entry")) {
                                        errorMessage = "Error: Duplicación de clave primaria"
                                    } else {
                                        errorMessage = "Error: $errorData"
                                    }
                                }

                                Log.e("VolleyError", errorMessage)

                                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                            }) {
                            override fun getParams(): MutableMap<String, String> {
                                val parametros = HashMap<String, String>()
                                parametros.put("dni", txtDni?.text.toString())
                                parametros.put("nombre", txtNombre)
                                parametros.put("apellidos", txtApellidos)
                                parametros.put(
                                    "correo",
                                    correo
                                )
                                parametros.put("celular", txtCelular?.text.toString())
                                parametros.put("contrasena", txtPassword?.text.toString())
                                return parametros
                            }
                        }

                        queue.add(resultadoPost)
                    } else {
                        Toast.makeText(this, "Nombre o apellidos vacíos", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Dni inexistente", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", "Error en la solicitud JSON: ${error.message}")
                Toast.makeText(this, "Error al obtener los datos del servidor", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonObjectRequest)



    }
}