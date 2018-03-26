package com.lush.views.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lush.lib.adapter.BaseSelectableListAdapter
import com.lush.lib.listener.OnListItemClickListener
import com.lush.lib.model.BaseModel
import com.lush.view.holder.BaseSelectableViewHolder
import com.lush.view.holder.BaseViewHolder
import com.lush.views.sample.R.id.list
import com.lush.views.sample.base.BaseSampleActivity
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_person.view.*

class ListSampleActivity: BaseSampleActivity(), OnListItemClickListener<Person>
{
	lateinit var adapter: PersonAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_list)

		val items = arrayListOf(
				Person("Agent", "Smith"),
				Person("James", "Bond"),
				Person("Darth", "Vader"),
				Person("Mary", "Poppins"),
				Person("John", "McClane"),
				Person("Ace", "Ventura"),
				Person("Jack", "Sparrow"),
				Person("Frodo", "Baggins"),
				Person("Rocky", "Balboa"),
				Person("Forrest", "Gump"),
				Person("Indiana", "Jones")
		)

		adapter = PersonAdapter(, items, this)
		list.adapter = adapter
		list.layoutManager = LinearLayoutManager(this)
	}

	override fun onItemClick(item: Person, view: View)
	{
		if (adapter.getSelected().contains(item))
		{
			adapter.removeSelected(item)
		}
		else
		{
			adapter.addSelected(item)
		}
	}
}

class PersonAdapter(itemCallback: DiffUtil.ItemCallback<Person>, listener: OnListItemClickListener<Person>? = null): BaseSelectableListAdapter<Person, PersonViewHolder>(listener, itemCallback)
{
	override fun getLayout(): Int = R.layout.item_person
	override fun createViewHolder(view: View): PersonViewHolder = PersonViewHolder(view)
}

class PersonViewHolder(view: View): BaseSelectableViewHolder<Person>(view)
{
	override fun bind(model: Person)
	{
		itemView.person_name.text = "${model.name} ${model.surname}"
	}

	override fun setSelected(selected: Boolean)
	{
		val backgroundColor = if (selected) Color.BLACK else Color.WHITE
		val textColor = if (selected) Color.WHITE else Color.BLACK
		itemView.setBackgroundColor(backgroundColor)
		itemView.person_name.setTextColor(textColor)
	}
}

data class Person(val name: String, val surname: String): BaseModel<Person>()
{
	override fun areItemsTheSame(oldItem: Person?, newItem: Person?): Boolean
	{
		return oldItem?.name == newItem?.name
	}

	override fun areContentsTheSame(oldItem: Person?, newItem: Person?): Boolean
	{
		return oldItem == newItem
	}
}