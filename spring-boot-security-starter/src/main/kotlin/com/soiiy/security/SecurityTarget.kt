package com.soiiy.security

interface SecurityTarget {

    fun principal(): String

    fun roles(): List<String>

    fun permissions(): List<String>

}