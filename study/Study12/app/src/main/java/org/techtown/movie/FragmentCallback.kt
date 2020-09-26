package org.techtown.movie

import android.os.Bundle

interface FragmentCallback {

    enum class FragmentItem {
        ITEM_LIST, ITEM_DETAILS, ITEM_RESERVATION,
        ITEM_PLACE, ITEM_FAVORITE, ITEM_FAVORITE_DETAILS
    }

    fun onFragmentSelected(item: FragmentItem, bundle: Bundle?)

}