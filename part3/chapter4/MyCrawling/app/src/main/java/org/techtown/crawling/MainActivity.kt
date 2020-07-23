package org.techtown.crawling

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {
    val url = "https://movie.naver.com/movie/running/current.nhn?order=reserve"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            MyAsyncTask().execute(url)
        }

    }

    inner class MyAsyncTask: AsyncTask<String, String, String>() {
        var itemList: ArrayList<MovieItem> = arrayListOf()

        override fun onPreExecute() {
            super.onPreExecute()

            showData("요청 url -> ${url}")
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val doc = Jsoup.connect("${params[0]}").get()

                val elems: Elements = doc.select("ul.lst_detail_t1 li")
                run elemsLoop@{
                    elems.forEachIndexed { index, elem ->
                        val title = elem.select("dt.tit a").text()
                        val score1 = elem.select("dl.info_star div.star_t1 span.num").text()
                        val score2 = elem.select("span.num2").text()
                        val reserve = elem.select("dl.info_exp div.star_t1 span.num").text()

                        var item = MovieItem(title, score1, score2, reserve)
                        itemList.add(item)

                        if (index == 9) {
                            return@elemsLoop
                        }
                    }
                }

                return doc.title()

            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        override fun onPostExecute(result: String?) {
            showData(itemList.joinToString())
        }
    }

    fun showData(message:String) {
        output1.append(message + "\n")
    }

}