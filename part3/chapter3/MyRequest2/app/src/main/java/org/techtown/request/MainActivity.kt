package org.techtown.request

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var requestQueue:RequestQueue? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue =  Volley.newRequestQueue(applicationContext)

        requestButton.setOnClickListener {
            send()
        }
    }

    fun send() {
        val url = input1.text.toString()

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                output1.append("\n응답 -> ${it}")

                processResponse(it)
            },
            {
                output1.append("\n에러 -> ${it.message}")
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["userid"] = "john"

                return params
            }
        }

        request.setShouldCache(false)
        requestQueue?.add(request)
        output1.append("요청함")
    }

    fun processResponse(response:String) {
        val gson = Gson()
        val boxOffice = gson.fromJson(response, BoxOffice::class.java)
        output1.append("영화 정보의 수: " + boxOffice.boxOfficeResult.dailyBoxOfficeList.size)
    }

}