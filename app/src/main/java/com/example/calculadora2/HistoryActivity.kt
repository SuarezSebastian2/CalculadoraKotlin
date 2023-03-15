package com.example.calculadora2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyList = findViewById<ListView>(R.id.listView)
        val operationHistory = intent.getStringArrayListExtra("operationHistory") ?: listOf<String>()
        val adapter = HistoryAdapter(this, R.layout.history_item, operationHistory)
        historyList.adapter = adapter
    }
}
