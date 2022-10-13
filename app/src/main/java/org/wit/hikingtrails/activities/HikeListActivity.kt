package org.wit.hikingtrails.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.hikingtrails.R
import org.wit.hikingtrails.databinding.ActivityHikeListBinding
import org.wit.hikingtrails.databinding.CardHikeBinding
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

class HikeListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityHikeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = HikeAdapter(app.hikes)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, HikeActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class HikeAdapter constructor(private var hikes: List<HikeModel>) :
    RecyclerView.Adapter<HikeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardHikeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hike = hikes[holder.adapterPosition]
        holder.bind(hike)
    }

    override fun getItemCount(): Int = hikes.size

    class MainHolder(private val binding : CardHikeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hike: HikeModel) {
            binding.hikeName.text = hike.name
            binding.description.text = hike.description
        }
    }
}