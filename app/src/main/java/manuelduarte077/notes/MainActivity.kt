package manuelduarte077.notes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONArray
import manuelduarte077.notes.MainActivity.Data.desc
import manuelduarte077.notes.MainActivity.Data.title1
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var sp: SharedPreferences

    object Data {
        var title1 = mutableListOf<String>()
        var desc = mutableListOf<String>()
    }
    private lateinit var lvNotes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        lvNotes = findViewById(R.id.lvNotes)

        //retrieving data from SharedPref
        sp = getSharedPreferences("data", MODE_PRIVATE)
        val jTitle = sp.getString("title", "")
        val jDesc = sp.getString("desc", "")



        try {

            val oTitle = JSONArray(jTitle)
            val oDesc = JSONArray(jDesc)

            val length: Int = oTitle.length()

            title1 = mutableListOf()
            desc = mutableListOf()

            //

            for (i in 0..length - 1) {
                title1.add(i, oTitle.getString(i))
                desc.add(i, oDesc.getString(i))
            }

        } catch (e: Exception) {

        }

//        for (i in 0 until length) {
//            try {
//                val innerobj = obj.getJSONObject(i)
//                val title: String = innerobj.getString("title")
//                val isCheck: Boolean = innerobj.getBoolean("isChecked")
//                val todo = Todo(title, isCheck)
//                todoAdapter.addTodo(todo, sp)
//                Log.e("TAG", "onCreate: Called ")
//
//            } catch (e: IndexOutOfBoundsException) {
//                break
//            }
//        }


        val create: FloatingActionButton = findViewById(R.id.fab)
        create.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java).putExtra("isCreate", true))

        }

        val myListAdapter = MyListAdapter(this, title1, desc)
        lvNotes.adapter = myListAdapter

        lvNotes.setOnItemClickListener { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            var tvtitle = view.findViewById<TextView>(R.id.title)
            var tvdesc = view.findViewById<TextView>(R.id.description)
            Toast.makeText(this, tvtitle.text.toString() + " " + position, Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, ViewActivity::class.java).putExtra("position", position))

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        MainActivity.Data.title1 = mutableListOf()
        MainActivity.Data.desc = mutableListOf()
        sp = getSharedPreferences("data", MODE_PRIVATE)
        val editor = sp.edit()


        val gson = Gson()
        val jTitle = gson.toJson(MainActivity.Data.title1)
        val jDesc = gson.toJson(MainActivity.Data.desc)
        editor.putString("title",jTitle)
        editor.putString("desc",jDesc)
        editor.apply()


        val myListAdapter = MyListAdapter(this, title1, desc)
        lvNotes.adapter = myListAdapter
        return true
    }


}