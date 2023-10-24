package id.dimas.kpu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import id.dimas.kpu.adapter.MenuAdapter
import id.dimas.kpu.databinding.ActivityMainBinding
import id.dimas.kpu.model.Menu

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var list: ArrayList<Menu> = arrayListOf()

    private val listTitleMenu = arrayListOf("Informasi", "Form Entry")
    private val listImageMenu = arrayListOf(R.drawable.ic_info, R.drawable.ic_info)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showListView()
    }


    private fun showListView() {
        list.addAll(MenuData.listData)
        val menuAdapter = MenuAdapter(this@MainActivity, list)
        binding.listView.adapter = menuAdapter

        binding.listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                when (adapterView.getItemIdAtPosition(i)) {
                    0L -> {
                        startActivity(Intent(this, InfoActivity::class.java))
                    }
                    1L -> {
                        startActivity(Intent(this, AddActivity::class.java))
                    }
                    2L -> {
                        startActivity(Intent(this, ViewActivity::class.java))
                    }
                    3L -> {
                        finish()
                    }
                }
            }
    }


}