package com.example.flowkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flowkotlin.databinding.ActivitySpalshBinding
import com.example.flowkotlin.flow_operators.MainActivity
import com.example.flowkotlin.flows_comparison.FlowsComparisonActivity

class SpalshActivity : AppCompatActivity() {

    lateinit var binding: ActivitySpalshBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnComparison.setOnClickListener {
            startActivity(Intent(this@SpalshActivity , FlowsComparisonActivity::class.java ))
        }

        binding.btnOperators.setOnClickListener {
            startActivity(Intent(this@SpalshActivity , MainActivity::class.java ))
        }
    }
}