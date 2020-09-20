package com.soiiy.platform.security.storage

import com.soiiy.platform.utils.lang.TimeUtil

class SecurityStorageRecord {

    data class SecurityStorageRecordItem(
        // 通道
        var channel: String = "",
        // 凭证
        var token: String = "",
        // 过期时间
        var expire: Long = 0
    )

    var items: MutableList<SecurityStorageRecordItem> = mutableListOf()

    fun isEmpty(): Boolean = items.isEmpty()

    fun exist(channel: String): Boolean {
        return items.any { it.channel == channel }
    }

    fun exist(channel: String, token: String): Boolean {
        return items.any { it.token == token}
    }

    fun remove(channel: String): SecurityStorageRecord {
        items = items.filterNot { it.channel == channel }.toMutableList()
        return this
    }

    fun remove(channel: String, token: String): SecurityStorageRecord {
        items = items.filterNot { it.token == token }.toMutableList()
        return this
    }

    fun add(channel: String, token: String, expireDay: Long): SecurityStorageRecord {
        val time = TimeUtil.timeSecond() + expireDay * 86400
        items.add(
            SecurityStorageRecordItem(
                channel,
                token,
                time
            )
        )
        return this
    }

    fun expire(): SecurityStorageRecord {
        val time = TimeUtil.timeSecond()
        items = items.filter { it.expire > time }.toMutableList()
        return this
    }

}