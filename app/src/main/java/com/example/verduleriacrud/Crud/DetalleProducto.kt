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

class DetalleProducto : AppCompatActivity() {
    lateinit var edtId: EditText
    lateinit var edtNombre: EditText
    lateinit var edtTipo: EditText
    lateinit var edtPrecio: EditText
    lateinit var edtStock: EditText
    lateinit var edtRuc: EditText
    lateinit var btnModificar: Button
    lateinit var btnEliminar: Button
    lateinit var btnCerrarDetalle: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        edtId = findViewById(R.id.edtId)
        edtNombre = findViewById(R.id.edtNombre)
        edtTipo = findViewById(R.id.edtTipo)
        edtPrecio = findViewById(R.id.edtPrecio)
        edtStock = findViewById(R.id.edtStock)
        edtRuc = findViewById(R.id.edtRuc)
        btnModificar = findViewById(R.id.btnModificar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnCerrarDetalle = findViewById(R.id.btnCerrarDetalle)

        val id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        val tipo = intent.getStringExtra("tipo")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val stock = intent.getDoubleExtra("kilos", 0.0)
        val ruc = intent.getStringExtra("ruc")

        edtId.setText(id)
        edtNombre.setText(nombre)
        edtTipo.setText(tipo)
        edtPrecio.setText(precio.toString())
        edtStock.setText(stock.toString())
        edtRuc.setText(ruc)
        btnModificar.setOnClickListener {
            val idProducto = edtId.text.toString()
            val nuevoNombre = edtNombre.text.toString()
            val nuevoTipo = edtTipo.text.toString()
            val nuevoPrecio = edtPrecio.text.toString().toDouble()
            val nuevoStock = edtStock.text.toString().toDouble()
            val nuevoRuc = edtRuc.text.toString()

            val requestQueue by lazy { Volley.newRequestQueue(this) }
            val modificarUrl = "http://192.168.1.26:9060/Producto/Modificar"

            val request = object : StringRequest(Method.PUT, modificarUrl,
                Response.Listener<String> {
                    Toast.makeText(this, "Producto modificado correctamente", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error al modificar el producto", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["id"] = idProducto
                    params["nombre"] = nuevoNombre
                    params["tipo"] = nuevoTipo
                    params["precio"] = nuevoPrecio.toString()
                    params["kilos"] = nuevoStock.toString()  // Asegúrate de ajustar el nombre del parámetro según tu backend
                    params["ruc"] = nuevoRuc
                    return params
                }
            }

            requestQueue.add(request)
        }





        btnEliminar.setOnClickListener {
            val idProducto = edtId.text.toString()

        }
        btnEliminar.setOnClickListener {
            val idProducto = edtId.text.toString()

            val requestQueue by lazy { Volley.newRequestQueue(this) }
            val eliminarUrl = "http://192.168.1.26:9060/Producto/eliminar?idproducto=$idProducto"

            val request = object : StringRequest(Method.DELETE, eliminarUrl,
                Response.Listener<String> {
                    Toast.makeText(this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this, CrudVerduras:: class.java)
                    startActivity(intent)
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
                }) {
            }

            requestQueue.add(request)
        }
        btnCerrarDetalle.setOnClickListener {
            val idProducto = edtId.text.toString()
            val intent: Intent = Intent(this, CrudVerduras:: class.java)
            startActivity(intent)
        }
    }
}
