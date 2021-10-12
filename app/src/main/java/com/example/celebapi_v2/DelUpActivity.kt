package com.example.celebapi_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.celebapi_v2.RetrofitInstance.api
import com.example.celebapi_v2.databinding.ActivityDelUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DelUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityDelUpBinding

    var celebID = ""
    var celebName = ""
    var taboo1 = ""
    var taboo2 = ""
    var taboo3 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDelUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            update()
            clear()
        }
        binding.btnDel.setOnClickListener {
            delete()
            clear()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this@DelUpActivity, MainActivity::class.java)
            startActivity(intent)
        }




    }

    private fun update(){
        celebID= binding.etPk.text.toString()
        celebName= binding.etName.text.toString()
        taboo1= binding.etTaboo1.text.toString()
        taboo2= binding.etTaboo2.text.toString()
        taboo3= binding.etTaboo3.text.toString()
        api.update(
            celebID,
            Data(celebID.toInt(), celebName, taboo1, taboo2, taboo3)
        ).enqueue(object : Callback<Data?> {
            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
                Toast.makeText(applicationContext, "User was updated Successfully", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Data?>, t: Throwable) {
                Toast.makeText(applicationContext, "User was not updated Successfully", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun delete(){



        api?.delete(
            celebID
        )?.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                Toast.makeText(applicationContext, "User was deleted Successfully", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Toast.makeText(applicationContext, "User was not deleted Successfully", Toast.LENGTH_LONG).show()
            }
        })

    }


    private fun clear(){
        binding.etName.text.clear()
        binding.etTaboo1.text.clear()
        binding.etTaboo2.text.clear()
        binding.etTaboo3.text.clear()

    }
}