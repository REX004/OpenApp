package com.example.a01_wskpolice.View.session1.paint

import com.example.a01_wskpolice.View.session1.paint.PaintView.Companion.currentBrush
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a01_wskpolice.View.session1.main.MainGuestActivity
import com.example.a01_wskpolice.databinding.PaintActivityBinding

class PaintActivity : AppCompatActivity() {

    companion object {
        var path = Path()
        var paintBrush = Paint()
    }

    private lateinit var binding: PaintActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PaintActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIMG.setOnClickListener {
            startActivity(Intent(this, MainGuestActivity::class.java))
        }
        binding.balckcolor.setOnClickListener {
            Toast.makeText(this, "Color marker chosen", Toast.LENGTH_SHORT).show()
        }
        binding.pencil.setOnClickListener {
            Toast.makeText(this, "Marker chosen", Toast.LENGTH_SHORT).show()
        }
        binding.sterka.setOnClickListener {
            Toast.makeText(this, "Lastic chosen", Toast.LENGTH_SHORT).show()
            paintBrush.color = Color.WHITE
            currentColor(paintBrush.color)
        }
        binding.shareIMG.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }
    private fun currentColor(color: Int){
        currentBrush = color
        path = Path()
    }
}