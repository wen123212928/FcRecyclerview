package com.example.wen.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fcbox.uilib.listaction.ActionSheetListAdapter
import com.fcbox.uilib.listaction.ListActionSheet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_hello.setOnClickListener {
            var listActionSheet = ListActionSheet.Builder(this)
                    .setAdapter(ActionSheetListAdapter(this, listOf("1", "2")))
                    .setOnItemClickListener(object : ListActionSheet.OnItemClickListener {
                        override fun onItemClick(actionSheet: ListActionSheet, view: View, position: Int) {
                            when (position) {
                                0 -> {
                                    Toast.makeText(this@MainActivity, "111111", Toast.LENGTH_SHORT).show()
                                }
                                1 -> {
                                    Toast.makeText(this@MainActivity, "2222222", Toast.LENGTH_SHORT).show()
                                }

                            }

                        }
                    }).build()
            listActionSheet.show()

        }
    }
}
