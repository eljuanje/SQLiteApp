package mx.edu.utng.sqliteapp

import android.app.AppComponentFactory
import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAlta.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "UserBD", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("firstName", et1_firstName.getText().toString())
            registro.put("lastName", et2_lastName.getText().toString())
            bd.insert("users", null, registro)
            bd.close()
            et1_firstName.setText("")
            et2_lastName.setText("")
            Toast.makeText(this, "Se cargaron los datos del usuario", Toast.LENGTH_SHORT).show()
        }

        btnMostrar.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "UserBD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select * from users", null)
            if (fila.moveToFirst()) {
                et2_lastName.setText(fila.getString(0))
            } else
                Toast.makeText(this, "No existen usuarios", Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btnConsulta.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "UserBD", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery(
                "select * from users where firstName='${et1_firstName.text}'",
                null
            )
            if (fila.moveToFirst()) {
                et1_firstName.setText(fila.getString(0))
                et2_lastName.setText(fila.getString(1))
            } else
                Toast.makeText(
                    this,
                    "No existe un usuario con dicha nombre",
                    Toast.LENGTH_SHORT
                ).show()
            bd.close()
        }

        btnEliminar.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "UserBD", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("users", "firstName=${et1_firstName.text}", null)
            bd.close()
            et1_firstName.setText("")
            et2_lastName.setText("")
            if (cant == 1)
                Toast.makeText(
                    this,
                    "Se borró el usuario con dicho nombre",
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(
                    this,
                    "No existe un usuario con dicho nombre",
                    Toast.LENGTH_SHORT
                ).show()
        }

    }
}