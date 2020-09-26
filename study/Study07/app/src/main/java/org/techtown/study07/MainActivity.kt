package org.techtown.study07

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val adapter = SongAdapter()
        recyclerView.adapter = adapter

        adapter.listener = object : OnSongItemClickListener {
            override fun onItemClick(holder: SongAdapter.ViewHolder?, view: View?, position: Int) {
                val name = adapter.items[position]
                showToast("아이템 선택됨 : $name")
            }
        }

        addButton.setOnClickListener {
            val title = input1.text.toString()
            val singer = input1.text.toString()

            adapter.items.add(Song(title, singer))
            adapter.notifyDataSetChanged()

        }

    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}