package com.radlance.numberstesttask.numbers.data

data class NumberData(
    private val id: String,
    private val fact: String
) {
    interface Mapper<T : Any> {
        fun map(id: String, fact: String): T

        class Matches(private val id: String) : Mapper<Boolean> {
            override fun map(id: String, fact: String): Boolean = this.id == id
        }
    }

    fun <T : Any> map(mapper: Mapper<T>): T = mapper.map(id, fact)
}