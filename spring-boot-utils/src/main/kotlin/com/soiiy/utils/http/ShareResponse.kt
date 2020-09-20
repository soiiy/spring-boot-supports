package com.soiiy.utils.http

import com.hupoll.platform.util.json.JsonUtil
import java.lang.reflect.Type
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaType

@Suppress("UNCHECKED_CAST")
interface ShareResponse<E: Any> {

    private fun clazzType(): Type {
        return this::class.supertypes.first {
            it.isSubtypeOf(ShareResponse::class.starProjectedType)
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

    fun beforeResultConvert(params: JsonUtil.Conditional) {}

    fun afterResultConvert(e: E) {}

    fun result(): E {
        val params = JsonUtil.Conditional()
        beforeResultConvert(params)
        val result = JsonUtil.copy(this, newInstance(), params)
        afterResultConvert(result)
        return result
    }

    fun result(params: JsonUtil.Conditional? = null): E {
        return JsonUtil.copy(this, newInstance(), params)
    }

    fun result(params: (JsonUtil.Conditional) -> JsonUtil.Conditional): E {
        return result(params(JsonUtil.Conditional()))
    }
}