package com.example.stride.tensorFlow.tracker

import com.example.stride.tensorFlow.data.Person

data class Track(
    val person: Person,
    val lastTimestamp: Long
)