package com.example.stride.tensorFlow.tracker

data class TrackerConfig(
    val maxTracks: Int = MAX_TRACKS,
    val maxAge: Int = MAX_AGE,
    val minSimilarity: Float = MIN_SIMILARITY,
    val keyPointsTrackerParams: KeyPointsTrackerParams? = null
) {
    companion object {
        private const val MAX_TRACKS = 18
        private const val MAX_AGE = 1000
        private const val MIN_SIMILARITY = 0.15f
    }
}

data class KeyPointsTrackerParams(
    val keypointThreshold: Float = KEYPOINT_THRESHOLD,
    // List of per-keypoint standard deviation `σ`, keypoints on a person's body (shoulders, knees, hips, etc.)
    // tend to have a `σ` much larger than on a person's head (eyes, nose, ears).
    // Read more at: https://cocodataset.org/#keypoints-eval
    val keypointFalloff: List<Float> = KEYPOINT_FALLOFF,
    val minNumKeyPoints: Int = MIN_NUM_KEYPOINT
) {
    companion object {
        // From COCO:
        // https://cocodataset.org/#keypoints-eval
        private val KEYPOINT_FALLOFF: List<Float> = listOf(
            0.026f, 0.025f, 0.025f, 0.035f, 0.035f, 0.079f, 0.079f, 0.072f, 0.072f, 0.062f,
            0.062f, 0.107f, 0.107f, 0.087f, 0.087f, 0.089f, 0.089f
        )
        private const val KEYPOINT_THRESHOLD = 0.3f
        private const val MIN_NUM_KEYPOINT = 4
    }
}