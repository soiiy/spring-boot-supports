package com.soiiy.security.storage

import com.hupoll.platform.util.json.JsonUtil
import com.soiiy.security.SecurityTarget
import com.soiiy.security.support.SecurityTokenUtil
import com.soiiy.utils.lang.TimeUtil

interface SecurityStorageEngine {

    private fun timestamp(day: Long = 0): Long = TimeUtil.timeSecond() + day * 86400

    private fun serialize(user: SecurityTarget): String = JsonUtil.toJSONString(user).orEmpty()

    private fun <T: SecurityTarget> deserialize(user: String?, target: Class<T>): T = JsonUtil.parseObject(user.orEmpty(), target)

    /**
     * 缓存用户登陆TOKEN
     * - 用户信息更新时便于刷新缓存
     * - 单点登陆校验
     */
    private fun record(principal: String, channel: String, token: String, isRemove: Boolean = false) {
        val recordKey = "SecurityPrincipal@${principal}"
        val recordResult = find(recordKey)
        // 如果执行退出，并且没有结果 则直接终止运行
        if (isRemove && recordResult.isNullOrEmpty()) return

        val recordModel = try {
            JsonUtil.parseObject(recordResult.orEmpty(), SecurityStorageRecord::class.java)
        } catch (e: Exception) {
            SecurityStorageRecord()
        }

        // 如果退出操作， 并且存在当前Token，需要清除
        if (isRemove && recordModel.exist(channel, token)) {
            recordModel.remove(channel, token)
            // 单例操作时需要清除
        } else if (isSingle() && recordModel.exist(channel)) {
            recordModel.remove(channel).add(channel, token, timestamp(expireDay()))
        } else {
            recordModel.add(channel, token, timestamp(expireDay()))
        }

        if (recordModel.expire().isEmpty()) {
            remove(recordKey)
        } else {
            save(recordKey, JsonUtil.toJSONString(recordModel).orEmpty())
        }
    }

    fun <T: SecurityTarget> findUserByToken(token: String?, target: Class<T>): T? {
        if (token.isNullOrEmpty()) return null
        return try {
            deserialize(find(token), target)
        } catch (e: Throwable) { null }
    }

    // 登陆 - 保存
    fun saveBy(user: SecurityTarget, channel: String): String {
        val token = SecurityTokenUtil.make(user.principal(), expireDay())
        record(user.principal(), channel, token)
        save(token, serialize(user))
        return token
    }

    // 刷新
    fun refreshBy(token: String, user: SecurityTarget, channel: String) {
        record(user.principal(), channel, token)
        save(token, serialize(user))
    }

    // 移除
    fun removeBy(token: String, channel: String) {
        try {
            val principal = SecurityTokenUtil.take(token)
            record(principal, channel, token, true)
        } catch (e: Exception) {}
        remove(token)
    }

    // 清空
    fun clearBy(principal: String) {
        val recordKey = "SecurityPrincipal@${principal}"
        val recordResult = find(recordKey)
        // 如果执行退出，并且没有结果 则直接终止运行
        if (recordResult.isNullOrEmpty()) return

        // 清除每一个Token记录
        val recordModel = JsonUtil.parseObject(recordResult.orEmpty(), SecurityStorageRecord::class.java)
        for (item in recordModel.items) remove(item.token)

        // 清除用户记录
        remove(recordKey)
    }

    abstract fun isSingle(): Boolean

    abstract fun expireDay(): Long

    abstract fun find(token: String): String?

    abstract fun save(token: String, info: String)

    abstract fun remove(token: String)

}