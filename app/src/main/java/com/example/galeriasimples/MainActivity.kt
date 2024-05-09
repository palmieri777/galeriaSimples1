package com.example.galeria

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.galeriasimples.R

class MainActivity : AppCompatActivity() {

    //lateinit significa que eu declaro a variável antes...
//mas dou o valor para ela mais tarde, quando for usar
    lateinit var ivexemplo: ImageView
    lateinit var btgaleria: Button

    //variável que se comporta como função, e retorna a imagem selecionada da galeria
    val resultGaleria =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> val bitmap: Bitmap = if(Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, result.data?.data)
        } else {
            val source = ImageDecoder.createSource(this.contentResolver, result.data?.data!!)
            ImageDecoder.decodeBitmap(source)
        }
            ivexemplo.setImageBitmap(bitmap)
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivexemplo = findViewById(R.id.img)
        btgaleria = findViewById(R.id.btnfoto)

        btgaleria.setOnClickListener { //chamada para resultGaleria, que lança a activity (tela) da galeria de imagens do dispositivo
//a intenção declarada é selecionar uma imagem da galeria
            resultGaleria.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        } }
}