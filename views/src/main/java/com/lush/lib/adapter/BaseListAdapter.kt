package com.lush.lib.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.lush.lib.listener.OnListItemClickListener
import com.lush.view.holder.BaseViewHolder

abstract class BaseListAdapter<T>(proposedItems: List<T> = ArrayList(), protected val listener: OnListItemClickListener<T>? = null) : RecyclerView.Adapter<BaseViewHolder<T>>()
{
	private val items: ArrayList<T> = ArrayList(proposedItems.filter { it != null })

	override fun getItemCount(): Int
	{
		return items.size
	}

	override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int)
	{
		val item = getItem(position)
		holder.bind(item)
		if (listener != null)
		{
			holder.setOnClickListener(View.OnClickListener { listener.onItemClick(item, it) })
		}
	}

	override fun onViewRecycled(holder: BaseViewHolder<T>)
	{
		super.onViewRecycled(holder)
		holder.recycle()
	}

	fun getItem(position: Int): T
	{
		return items[position]
	}

	fun addItem(item: T)
	{
		items.add(item)
		notifyItemInserted(items.indexOf(item))
	}

	fun removeItem(item: T)
	{
		notifyItemRemoved(items.indexOf(item))
		items.remove(item)
	}

	fun removeItem(position: Int): T
	{
		val item = items.removeAt(position)
		notifyItemRemoved(position)
		return item
	}

	fun addItem(position: Int, item: T)
	{
		items.add(position, item)
		notifyItemInserted(position)
	}

	fun moveItem(fromPosition: Int, toPosition: Int)
	{
		val item = items.removeAt(fromPosition)
		if (toPosition >= items.size)
		{
			items.add(item)
		}
		else
		{
			items.add(toPosition, item)
		}
		notifyItemMoved(fromPosition, toPosition)
	}

	fun setItemsWithAnimation(newItems: List<T>)
	{
		val itemsToSet = newItems.filter { it != null }
		applyAndAnimateRemovals(itemsToSet)
		applyAndAnimateAdditions(itemsToSet)
		applyAndAnimateMovedItems(itemsToSet)
		notifyItemRangeChanged(0, itemsToSet.size)
	}

	fun setItems(newItems: List<T>)
	{
		items.clear()
		items.addAll(newItems.filter { it != null })
		notifyDataSetChanged()
	}

	fun getItems(): List<T> = ArrayList(items)

	protected fun getPosition(item: T): Int
	{
		return if (items.size > 0)
		{
			items.indexOf(item)
		}
		else -1
	}

	private fun applyAndAnimateRemovals(newItems: List<T>)
	{
		for (i in items.size - 1 downTo 0)
		{
			val item = items[i]
			if (!newItems.contains(item))
			{
				removeItem(i)
			}
		}
	}

	private fun applyAndAnimateAdditions(newItems: List<T>)
	{
		var i = 0
		val count = newItems.size
		while (i < count)
		{
			val item = newItems[i]
			if (!items.contains(item))
			{
				addItem(i, item)
			}
			i++
		}
	}

	private fun applyAndAnimateMovedItems(newItems: List<T>)
	{
		for (toPosition in newItems.indices.reversed())
		{
			val model = newItems[toPosition]
			val fromPosition = items.indexOf(model)
			if (fromPosition >= 0 && fromPosition != toPosition)
			{
				moveItem(fromPosition, toPosition)
			}
		}
	}
}