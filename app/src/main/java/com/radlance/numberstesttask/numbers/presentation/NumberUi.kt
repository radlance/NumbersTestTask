package com.radlance.numberstesttask.numbers.presentation

import android.widget.TextView

data class NumberUi(
    private val id: String,
    private val fact: String
) : Mapper<Boolean, NumberUi> {

    fun <T : Any> map(mapper: Mapper<T>): T = mapper.map(id, fact)

    interface Mapper<T : Any> {
        fun map(id: String, fact: String): T
    }

    override fun map(source: NumberUi): Boolean = source.id == id
}

object DetailsUi : NumberUi.Mapper<String> {
    override fun map(id: String, fact: String): String = "$id\n\n$fact"
}

class ListItemUi(
    private val title: TextView,
    private val subTitle: TextView
) : NumberUi.Mapper<Unit> {
    override fun map(id: String, fact: String) {
        title.text = id
        subTitle.text = fact
    }
}