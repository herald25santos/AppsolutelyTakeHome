package com.santos.herald.appsolutelytakehome.common

import android.view.View

interface OnItemClickListener {

    fun onItemClick(view: View, position: Int)

    fun onLongItemClick(v: View, position: Int)

}