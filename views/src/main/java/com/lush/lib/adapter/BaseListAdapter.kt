package com.lush.lib.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import com.lush.lib.listener.OnListItemClickListener
import com.lush.view.holder.BaseViewHolder

abstract class BaseListAdapter<T, VH: BaseViewHolder<T>>(callback: DiffUtil.ItemCallback<T>, private val listener: OnListItemClickListener<T>? = null): ListAdapter<T, VH>(callback)
{
	override fun onBindViewHolder(holder: VH, position: Int)
	{
		val item = getItem(position)
		holder.bind(item)
		if (listener != null) { holder.setOnClickListener { v -> listener.onItemClick(item, v) } }
	}

	override fun onViewRecycled(holder: VH)
	{
		super.onViewRecycled(holder)
		holder.recycle()
	}

	override fun submitList(list: MutableList<T>?)
	{
		super.submitList(list?.filter { it != null })
	}
}