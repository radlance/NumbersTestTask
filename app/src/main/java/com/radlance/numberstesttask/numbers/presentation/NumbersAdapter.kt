package com.radlance.numberstesttask.numbers.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radlance.numberstesttask.R
import com.radlance.numberstesttask.databinding.NumberLayoutBinding

class NumbersAdapter(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<NumberViewHolder>(), Mapper.Unit<List<NumberUi>> {

    private val list = mutableListOf<NumberUi>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.number_layout, parent, false),
            clickListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun map(source: List<NumberUi>) {
        val diff = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diff)

        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class NumberViewHolder(
    view: View,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(view) {
    private val binding = NumberLayoutBinding.bind(view)

    fun bind(model: NumberUi) {
        model.map(ListItemUi(binding.titleTextView, binding.subTitleTextView))
        itemView.setOnClickListener { clickListener.click(model) }
    }
}

interface ClickListener {
    fun click(item: NumberUi)
}

class DiffUtilCallback(
    private val oldList: List<NumberUi>,
    private val newList: List<NumberUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].map(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}