package com.example.stride.tensorFlow.ml

import android.graphics.Bitmap
import com.example.stride.tensorFlow.data.Person

interface PoseDetector : AutoCloseable {

    fun estimatePoses(bitmap: Bitmap): List<Person>

    fun lastInferenceTimeNanos(): Long
}