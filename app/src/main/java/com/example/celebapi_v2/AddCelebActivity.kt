package com.example.celebapi_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.celebapi_v2.RetrofitInstance.api
import com.example.celebapi_v2.databinding.ActivityAddCelebBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCelebActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCelebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCelebBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAddName.setOnClickListener {
            postData()
           binding.etName.text.clear()
         binding.etTaboo1.text.clear()
          binding.etTaboo2.text.clear()
            binding.etTaboo3.text.clear()

        }
        binding.btnCancel.setOnClickListener {
            val intent = Intent(this@AddCelebActivity,MainActivity::class.java )
            startActivity(intent)
        }
    }

    private fun postData(){
        val name = binding.etName.text.toString()
        val taboo1 = binding.etTaboo1.text.toString()
        val taboo2 = binding.etTaboo2.text.toString()
        val taboo3 = binding.etTaboo3.text.toString()

        api.postData(Data(1, name, taboo1, taboo2, taboo3)).enqueue(object :
            Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>){
                Log.d("Main-success", "Data Added")
            }
            override fun onFailure(call: Call<Data>, t: Throwable){
                Log.d("Main-Failure", "Data Not Added")
            }
        })

    }
}