package com.example.verduleriacrud.Crud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.verduleriacrud.Inicio
import com.example.verduleriacrud.R
import org.json.JSONArray

class CrudVerduras : AppCompatActivity() {
    private val url = "http://192.168.1.26:9060/Producto/Listar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_verduras)
        val listView: ListView = findViewById(R.id.listView)

        val btnInsertar: Button = findViewById(R.id.btnInsertar)
        val btnCerrar: Button = findViewById(R.id.btnCerrar)

        btnInsertar.setOnClickListener{
            val intent: Intent = Intent(this, InsertarVerdura::class.java)
            startActivity(intent)
        }

        btnCerrar.setOnClickListener{
            val intent: Intent = Intent(this, Inicio::class.java)
            startActivity(intent)
        }

        val productosList = mutableListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productosList)
        listView.adapter = adapter

        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                productosList.clear() //

                for (i in 0 until response.length()) {
                    val producto = response.getJSONObject(i)
                    val id = producto.getString("idproducto")
                    val nombre = producto.getString("nombre")
                    val tipo = producto.getString("tipo")
                    val precio = producto.getDouble("precio")
                    val kilos = producto.getDouble("kilos")
                    val ruc = producto.getString("ruc")

                    productosList.add("ID: $id\nNombre: $nombre\nTipo: $tipo\nPrecio: $precio\nKilos: $kilos\nRUC: $ruc")
                }

                adapter.notifyDataSetChanged()
                listView.setOnItemClickListener { _, _, position, _ ->
                    val selectedProduct = response.getJSONObject(position)
                    val id = selectedProduct.getString("idproducto")
                    val nombre = selectedProduct.getString("nombre")
                    val tipo = selectedProduct.getString("tipo")
                    val precio = selectedProduct.getDouble("precio")
                    val ruc = selectedProduct.getString("ruc")

                    val stock = if (selectedProduct.has("kilos")) {
                        selectedProduct.getDouble("kilos")
                    } else {
                        0.0 //
                    }

                    val intent = Intent(this, DetalleProducto::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("nombre", nombre)
                    intent.putExtra("tipo", tipo)
                    intent.putExtra("precio", precio)
                    intent.putExtra("kilos", stock)
                    intent.putExtra("ruc", ruc)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Log.e("APIError", error.toString())
            }
        )



        requestQueue.add(jsonArrayRequest)
    }
}
