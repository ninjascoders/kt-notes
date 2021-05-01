package manuelduarte077.notes

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

class ViewActivity : AppCompatActivity() {
    lateinit var  sp  : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val tvTitle = findViewById<TextView>(R.id.title)
        val tvDesc = findViewById<TextView>(R.id.desc)
        val btnEdit = findViewById<TextView>(R.id.btnEdit)
        val btnDelete = findViewById<TextView>(R.id.btnDelete)

        val position = intent.getIntExtra("position",1)
        val sTitle = MainActivity.Data.title1.get(position)
        val sDesc = MainActivity.Data.desc.get(position)
        tvTitle.setText(sTitle)
        tvDesc.setText(sDesc)

        btnEdit.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java).putExtra("isCreate",false).putExtra("position",position))
        }

        btnDelete.setOnClickListener {

            MainActivity.Data.title1.removeAt(position)
            MainActivity.Data.desc.removeAt(position)

            sp = getSharedPreferences("data", MODE_PRIVATE)
            val editor = sp.edit()


            val gson = Gson()
            val jTitle = gson.toJson(MainActivity.Data.title1)
            val jDesc = gson.toJson(MainActivity.Data.desc)
            editor.putString("title",jTitle)
            editor.putString("desc",jDesc)
            editor.apply()


            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()



        }

    }
}