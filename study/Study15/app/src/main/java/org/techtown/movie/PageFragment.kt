package org.techtown.movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_page.view.*

class PageFragment : Fragment() {
    var index:Int = 0
    var imageId: String? = null
    var title: String? = null
    var details1: String? = null
    var details2: String? = null

    var callback: FragmentCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentCallback) {
            callback = context
        } else {
            Log.d(TAG, "Activity is not FragmentCallback.")
        }
    }

    override fun onDetach() {
        super.onDetach()

        if (callback != null) {
            callback = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            index = bundle.getInt("index", 0)
            imageId = bundle.getString("imageId")
            title = bundle.getString("title")
            details1 = bundle.getString("details1")
            details2 = bundle.getString("details2")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_page, container, false)

        if (imageId != null && imageId!!.isNotEmpty()) {
            val url = "http://image.tmdb.org/t/p/w200${imageId}"

            // CenterCrop 삭제
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(rootView.posterImageView)
        }

        rootView.titleTextView.text = title
        rootView.details1TextView.text = Utils.addComma(details1?.toInt()) + "명"
        rootView.details2TextView.text = details2

        rootView.posterImageView.setOnClickListener {
            showDetailsPage()
        }

        rootView.infoLayout.setOnClickListener {
            showDetailsPage()
        }

        return rootView
    }

    fun showDetailsPage() {
        if (callback != null) {
            val bundle = Bundle()
            bundle.putInt("index", index)
            bundle.putString("listType", "boxOffice")

            callback!!.onFragmentSelected(FragmentCallback.FragmentItem.ITEM_DETAILS, bundle)
        }
    }

    companion object {
        private const val TAG = "PageFragment"

        fun newInstance(index:Int, imageId: String?, title: String?, details1: String?, details2: String?): PageFragment {
            val fragment = PageFragment()

            val bundle = Bundle()
            bundle.putInt("index", index)
            bundle.putString("imageId", imageId)
            bundle.putString("title", title)
            bundle.putString("details1", details1)
            bundle.putString("details2", details2)
            fragment.arguments = bundle

            return fragment
        }
    }

}