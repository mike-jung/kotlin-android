package org.techtown.movie

import android.os.Bundle

interface FragmentCallback {

    enum class FragmentItem {
        ITEM_LIST, ITEM_DETAILS, ITEM2, ITEM3, ITEM4
    }

    fun onFragmentSelected(item: FragmentItem, bundle: Bundle?)

}