package org.techtown.push.send

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    companion object {
        var requestQueue: RequestQueue? = null
        var regId = "c14xpyVRQmW-Ty1pNcg_Wo:APA91bEKedtTQ9WjY6FXkrwigQFFAZmSfemhEGYHV7uLlcdWp2OlcP7mtOGPGR_lt33o4BxqQ-CirsxBN2-THUY2ycY0AQFGowPSiWZIU6kKApU1t-SYCbkIfxqdov6Vj3BhAgbFpnDA"
        var apiKey = "AAAApb9OtBo:APA91bEHErXdlDIKUmLyPNEi-7VlYVJsox831B8mvqNe73a6hgHQOKkvHsuS11y7Q6F107e3jHCxlWXpddwXPFLSqAGjFLodbpo8o4QPEuJSxSDMfAVKyVASVL3RQP6uxrtCPtb15XCa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(applicationContext);

        sendButton.setOnClickListener {
            val title = "Notice"
            val contents = input1.text.toString()

            send(title, contents)
        }
    }

    fun send(title:String, contents:String) {
        val requestData = JSONObject()
        requestData.put("priority", "high")

        val dataObj = JSONObject()
        dataObj.put("title", title)
        dataObj.put("contents", contents)
        requestData.put("data", dataObj)

        val idArray = JSONArray()
        idArray.put(0, regId)
        requestData.put("registration_ids", idArray)

        val url = "https://fcm.googleapis.com/fcm/send"
        val request = object: JsonObjectRequest(
            Request.Method.POST,
            url,
            requestData,
            {
                output1.append("메시지 송신에 대한 응답 : ${it}\n")
            },
            {
                output1.append("메시지 송신 시 에러 : ${it}\n")
            }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    return params
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Authorization", "key=${apiKey}")
                    return headers
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
        }

        request.setShouldCache(false)
        requestQueue?.add(request)
        output1.append("메시지 송신함\n")
    }

}