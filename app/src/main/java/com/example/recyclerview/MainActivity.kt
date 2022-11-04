package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

//конечно добавляем интерфейс с адаптера
class MainActivity : AppCompatActivity(), PlantAdapter.Listener {
    lateinit var binding: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null
    //просит listener, даём
    private val adapter = PlantAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val imageId = it.data?.getIntExtra("Key0", 0)!!
                val name = it.data?.getStringExtra("Key1")!!
                val description = it.data?.getStringExtra("Key2")!!
                adapter.addPlant(PlantModel(imageId, name, description))
            }
        }
    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 3)
            rcView.adapter = adapter

            buttonAdd.setOnClickListener {
                Toast.makeText(this@MainActivity, "suc", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                launcher?.launch(intent)
            }

        }
    }


    //имплементируем тот самый onClick
    override fun onCLick(plantModel: PlantModel) {
        //стандартный интент но более нечитаемо зато коротко
        startActivity(Intent(this, ContentActivity::class.java).apply {
            putExtra("Key", plantModel)
        })
    }
}