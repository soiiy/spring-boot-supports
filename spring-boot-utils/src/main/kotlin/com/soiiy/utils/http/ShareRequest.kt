package com.soiiy.utils.http

import com.hupoll.platform.util.json.JsonUtil
import java.lang.reflect.Type
import javax.validation.Validation
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaType

@Suppress("UNCHECKED_CAST")
interface ShareRequest<E: Any> {

    private fun clazzType(): Type {
        return this::class.supertypes.first {
            it.isSubtypeOf(ShareRequest::class.starProjectedType)
        }.arguments.first().type!!.javaType
    }

    private fun clazzTypeName(): String {
        return clazzType().typeName
    }

    private fun clazz(): Class<E> {
        return Class.forName(clazzTypeName()) as Class<E>
    }

    private fun newInstance(): E {
        return clazz().newInstance() as E
    }

    fun shareEntityBefore(condition: JsonUtil.Conditional) {}

    fun shareEntityAfter(entity: E) {}

    fun entity(): E = entity(newInstance())

    fun entity(original: E): E {
        RequestUtil.validate(this)
        val params = JsonUtil.Conditional()
        shareEntityBefore(params)
        val result = JsonUtil.copy(this, original, params)
        shareEntityAfter(result)
        return result
    }

    fun map(filterNull: Boolean = true): Map<String, Any?> {
        val fromValueMap = mutableMapOf<String, Any?>()
        this::class.memberProperties.forEach { it ->
            it.isAccessible = true
            val field = it.javaField?.get(this)
            if (field !== null) {
                fromValueMap[it.name] = field
            } else if (!filterNull) {
                fromValueMap[it.name] = null
            }
        }
        return fromValueMap
    }

}