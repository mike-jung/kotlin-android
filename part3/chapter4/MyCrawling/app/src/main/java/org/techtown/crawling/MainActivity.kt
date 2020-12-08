package org.techtown.crawling

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    val url = "https://movie.naver.com/movie/running/current.nhn?order=reserve"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            doTask()
        }

    }

    fun doTask() {
        showData("요청 url -> ${url}")

        var itemList: ArrayList<MovieItem> = arrayListOf()
        var documentTitle:String = ""


        Single.fromCallable {

            try {
                val doc = Jsoup.connect(url).get()

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

                documentTitle = doc.title()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@fromCallable documentTitle
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ text ->
                    showData("onSuccess called.")

                    showData(itemList.joinToString())
                }, {
                    showData("onError called.")
                    it.printStackTrace()
                })

    }


    fun showData(message:String) {
        output1.append(message + "\n")
    }

}