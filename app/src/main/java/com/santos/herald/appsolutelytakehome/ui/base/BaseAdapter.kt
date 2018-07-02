package com.santos.herald.appsolutelytakehome.ui.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.santos.herald.appsolutelytakehome.common.widget.EndlessRecyclerViewScrollListener

abstract class BaseAdapter<T>(var data: List<T>?, private var mWithHeader: Boolean, private var mWithFooter: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun ismWithHeader(): Boolean {
        return mWithHeader
    }

    fun setmWithHeader(mWithHeader: Boolean) {
        this.mWithHeader = mWithHeader
    }

    fun ismWithFooter(): Boolean {
        return mWithFooter
    }

    fun setmWithFooter(mWithFooter: Boolean) {
        this.mWithFooter = mWithFooter
    }

    //region Get View
    protected abstract fun getItemView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun getHeaderView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun getFooterView(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder
    //endregion

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent)
        } else if (viewType == TYPE_HEADER) {
            return getHeaderView(inflater, parent)
        } else if (viewType == TYPE_FOOTER) {
            return getFooterView(inflater, parent)
        }

        return getHeaderView(inflater, parent)
        //  throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    override fun getItemCount(): Int {
        var itemCount = if (data == null) 0 else data!!.size
        if (mWithHeader)
            itemCount++
        if (mWithFooter)
            itemCount++
        return itemCount
    }


    override fun getItemViewType(position: Int): Int {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER
        return if (mWithFooter && isPositionFooter(position)) TYPE_FOOTER else TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1
    }


    fun getItem(position: Int): T {
        return if (mWithHeader) data!![position - 1] else data!![position]
    }

    interface EndlessScroll {
        fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)
    }

    fun setEndlessScroll(recyclerView: RecyclerView, linearLayoutManager: LinearLayoutManager, endlessScroll: EndlessScroll) {
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                endlessScroll.onLoadMore(page, totalItemsCount, view)
            }
        })
    }

    companion object {

        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }
}