package com.example.celebapi_v2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.celebapi_v2.RetrofitInstance.api
import com.example.celebapi_v2.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //lateinit var rvMain: RecyclerView
    lateinit var adapter: RVAdapter

   lateinit var list : ArrayList<Data>
   lateinit var searchList : ArrayList<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        list = arrayListOf()
        searchList = arrayListOf()

        //setRV()
        adapter = RVAdapter(this, searchList)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter


        binding.btnAdd.setOnClickListener {

            val intent = Intent(this@MainActivity, AddCelebActivity::class.java)
            startActivity(intent)

        }

        
        binding.btndel.setOnClickListener {
            //val userinput = binding.etSearch.text.toString()

                    val intent = Intent(this@MainActivity, DelUpActivity::class.java)
                   // intent.putExtra("userinput", userinput.toString())
                    startActivity(intent)
        }


        adapter.notifyDataSetChanged()

        getData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu?.findItem(R.id.sv_search)
        val seachView = item?.actionView as SearchView


        seachView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    list.forEach {
                        if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    adapter.update(searchList)
                }else{
                    searchList.clear()
                    searchList.addAll(list)
                    adapter.notifyDataSetChanged()
                    adapter.update(searchList)
                }

                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun getData(){
        lifecycleScope.launchWhenCreated {
            val response = try {
              api.getData()
            } catch (e: IOException){
                Log.d("Main-error", e.message.toString())
                return@launchWhenCreated

            }catch (e: HttpException){
                Log.d("Main-error", e.message.toString())
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() !=null){
               val data = response.body()!!

                adapter.update(data as ArrayList<Data>)
                for (i in response.body()!!){
                    list.add(Data(i.pk, i.name, i.taboo1, i.taboo2, i.taboo3))
                }
                searchList.addAll(list)

            }else{
                Log.d("Main-Error", "Response was not successful")
            }
        }
    }








}