package manuelduarte077.notes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


class CreateActivity : AppCompatActivity() {
    lateinit var  titleEt : EditText
    lateinit var descEt : EditText
    lateinit var  sp  : SharedPreferences
    var isCreate : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        isCreate = intent.getBooleanExtra("isCreate",false)
        titleEt = findViewById(R.id.etTitle)
        descEt  = findViewById(R.id.etDesc)

        if(!isCreate){
            val position = intent.getIntExtra("position",1)
            val sTitle = MainActivity.Data.title1.get(position)
            val sDesc = MainActivity.Data.desc.get(position)
            titleEt.setText(sTitle)
            descEt.setText(sDesc)

        }




    }

    fun create(view: View) {

        val title: String = titleEt.text.toString()
        val desc: String = descEt.text.toString()

        if(isCreate) {


            MainActivity.Data.title1.add(title)
            MainActivity.Data.desc.add(desc)


        }else{

            val position = intent.getIntExtra("position",1)
            MainActivity.Data.title1.set(position,title)
            MainActivity.Data.desc.set(position,desc)




        }
        sp = getSharedPreferences("data", MODE_PRIVATE)
        val editor = sp.edit()


        val gson = Gson()
        val jTitle = gson.toJson(MainActivity.Data.title1)
        val jDesc = gson.toJson(MainActivity.Data.desc)
        editor.putString("title",jTitle)
        editor.putString("desc",jDesc)
        editor.commit()

        startActivity(Intent(this, MainActivity::class.java))
        finish()



    }


}
