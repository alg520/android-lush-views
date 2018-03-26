package com.lush.lib.adapter

import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lush.lib.listener.OnListItemClickListener
import com.lush.view.holder.BaseSelectableViewHolder
import com.lush.view.holder.BaseViewHolder

/**
 * An extension over the [BaseListAdapter] which allows for items in the list to be
 * marked as selected. This is particularly useful in a situation for selecting multiple things.
 */
abstract class BaseSelectableListAdapter<T, VH: BaseViewHolder<T>>(listener: OnListItemClickListener<T>? = null, itemCallback: DiffUtil.ItemCallback<T>) : BaseListAdapter<T, VH>(itemCallback, listener)
{
	private val selected: ArrayList<T> = ArrayList()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
	{
		val view = LayoutInflater.from(parent.context).inflate(getLayout(), parent, false)
		return createViewHolder(view)
	}

	@LayoutRes abstract fun getLayout(): Int

	abstract fun createViewHolder(view: View): VH

	override fun onBindViewHolder(holder: VH, position: Int)
	{
		super.onBindViewHolder(holder, position)
		(holder as? BaseSelectableViewHolder<*>)?.setSelected(getSelected().contains(getItem(position)))
	}

	fun getSelected(): List<T> = selected

	fun addSelected(item: T)
	{
		selected.add(item)
		// TODO: notifyItemChanged(position)
	}

	fun removeSelected(item: T)
	{
		selected.remove(item)
		// TODO: notifyItemChanged(position)
	}

	fun setSelected(item: T)
	{
		val selectedCopy = ArrayList(selected)
		for (toRemove in selectedCopy)
		{
			removeSelected(toRemove)
		}
		addSelected(item)
	}
}
