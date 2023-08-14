package com.example.verduleriacrud.Crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.verduleriacrud.R

class InsertarVerdura : AppCompatActivity() {
    private lateinit var etNombre: EditText
    private lateinit var etTipo: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etStock: EditText
    private lateinit var etRuc: EditText
    private lateinit var btnInsertar: Button
    private  lateinit var btnVolver:Button

    private val insertUrl = "http://192.168.1.26:9060/Producto/Insertar"
    private val requestQueue by lazy { Volley.newRequestQueue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        etNombre = findViewById(R.id.etNombre)
        etTipo = findViewById(R.id.etTipo)
        etPrecio = findViewById(R.id.etPrecio)
        etStock = findViewById(R.id.etStock)
        etRuc = findViewById(R.id.etRuc)
        btnInsertar = findViewById(R.id.btnInsertar)
        btnVolver = findViewById(R.id.btnVolver)
        btnInsertar.setOnClickListener {
            insertarProducto()
        }
        btnVolver .setOnClickListener{
            val intent = Intent(this, CrudVerduras::class.java)
            startActivity(intent)
        }

    }

    private fun insertarProducto() {
        val nombre = etNombre.text.toString()
        val tipo = etTipo.text.toString()
        val precio = etPrecio.text.toString().toDouble()
        val stock = etStock.text.toString().toDouble()
        val ruc = etRuc.text.toString()

        val insertRequest = object : StringRequest(Method.POST, insertUrl,
            Response.Listener<String> {
                Toast.makeText(this, "Producto insertado correctamente", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al insertar el producto", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nombre"] = nombre
                params["tipo"] = tipo
                params["precio"] = precio.toString()
                params["stock"] = stock.toString()
                params["ruc"] = ruc
                return params
            }
        }

        requestQueue.add(insertRequest)
    }
}