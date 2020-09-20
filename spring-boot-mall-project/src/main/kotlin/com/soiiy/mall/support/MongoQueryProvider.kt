package com.soiiy.mall.support

import com.mongodb.client.result.UpdateResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.updateMulti
import org.springframework.data.repository.support.PageableExecutionUtils
import org.springframework.stereotype.Service

@Service
class MongoQueryProvider {

    @Autowired
    lateinit var template: MongoTemplate

    fun <T> selectAll(where: Criteria, clazz: Class<T>): List<T> {
        val doc = clazz.getAnnotation(Document::class.java)
        return template.find(Query(where), clazz, doc.collection)
    }

    fun <T> selectAll(where: Criteria, pageable: Pageable, clazz: Class<T>): Page<T> {
        val doc = clazz.getAnnotation(Document::class.java)
        val query = Query(where).with(pageable)
        val list = template.find(query, clazz, doc.collection)
        return PageableExecutionUtils.getPage(list, pageable) {
            template.count(Query.of(query).limit(-1).skip(-1), clazz, doc.collection)
        }
    }

    fun updateMulti(data: Update, where: Criteria, collectionName: String): Long {
        return template.updateMulti(Query(where), data, collectionName).modifiedCount
    }

    fun aggregate(params: Aggregation, collectionName: String): MutableList<Map<*, *>> {
        return template.aggregate(params, collectionName, Map::class.java).mappedResults
    }

}