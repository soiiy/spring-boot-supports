package com.soiiy.utils.http

import java.io.Serializable

data class ResponsePage<E> (

    var data: List<E>,

    var total: Number = data.size

): Serializable