package com.example.simpleworker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private lateinit var simpleWorker1: SimpleWorker
    private lateinit var simpleWorker2: SimpleWorker
    private lateinit var okHttpClient: OkHttpClient

    //returns random image everytime
    private val imageUrl1 = "https://picsum.photos/200"

    //returns random image everytime
    private val imageUrl2 = "https://picsum.photos/201"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        okHttpClient = OkHttpClient()
        simpleWorker1 = SimpleWorker(this, "1")
        simpleWorker2 = SimpleWorker(this, "2")

        btn1.setOnClickListener { fetchImage1AndSet() }
        btn2.setOnClickListener { fetchImage2AndSet() }
    }

    private fun fetchImage1AndSet() {
        simpleWorker1.addTask(object : Task<Bitmap> {
            override fun onExecuteTask(): Bitmap {
                val request = Request.Builder().url(imageUrl1).build()
                val response = okHttpClient.newCall(request).execute()
                return BitmapFactory.decodeStream(response.body?.byteStream())
            }

            override fun onCompleteTask(result: Bitmap) {
                image1.setImageBitmap(result)
            }

        })
    }

    private fun fetchImage2AndSet() {
        simpleWorker2.addTask(object : Task<Bitmap> {
            override fun onExecuteTask(): Bitmap {
                val request = Request.Builder().url(imageUrl2).build()
                val response = okHttpClient.newCall(request).execute()
                return BitmapFactory.decodeStream(response.body?.byteStream())
            }

            override fun onCompleteTask(result: Bitmap) {
                image2.setImageBitmap(result)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleWorker1.quit()
        simpleWorker2.quit()
    }
}

