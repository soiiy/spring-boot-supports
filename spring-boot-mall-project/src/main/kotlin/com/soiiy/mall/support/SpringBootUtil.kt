package com.soiiy.mall.support

import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import kotlin.reflect.full.memberProperties

object SpringBootUtil {

    private fun dbMongoSortedBy(property: String, direction: String): Sort.Order {
        return if ("DESC" == direction) Sort.Order.desc(property) else Sort.Order.asc(property)
    }

    private fun dbMongoSortedBy(sortedBy: String?): Sort {
        return if (sortedBy.isNullOrEmpty()) Sort.unsorted() else {
            val orders = sortedBy.split(",").filter { it.isNotEmpty() }.map {
                val item = it.split(":")
                dbMongoSortedBy(item.first(), item.last())
            }
            Sort.by(orders)
        }
    }

    fun dbMongoPageable(sortedBy: String?, page: Int, size: Int): PageRequest {
        return PageRequest.of(0.coerceAtLeast(page - 1), size, dbMongoSortedBy(sortedBy))
    }

    fun <T: Any> dbMongoExample(probe: T, matchers: Map<String, ExampleMatcher.GenericPropertyMatcher>): Example<T> {
        val matcherKeys = matchers.keys
        val ignores = probe::class.memberProperties.filterNot { matcherKeys.contains(it.name) }
            .map { it.name }.toTypedArray() + "_class"
        val matcher = ExampleMatcher.matching().withIgnorePaths(*ignores)
        for ((key, value) in matchers) matcher.withMatcher(key, value)
        return Example.of(probe, matcher)
    }

}