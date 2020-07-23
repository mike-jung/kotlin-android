package org.techtown.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)

        rootView.backButton.setOnClickListener {
            val activity = activity as MainActivity?
            activity?.onFragmentChanged(0)
        }

        return rootView
    }

}