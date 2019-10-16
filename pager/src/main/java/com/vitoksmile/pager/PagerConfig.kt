package com.vitoksmile.pager

class PagerConfig(
    val prefetchDistance: Int
) {

    class Builder {

        private var prefetchDistance = 0

        fun setPrefetchDistance(distance: Int) = apply {
            prefetchDistance = distance
        }

        fun build() = PagerConfig(prefetchDistance)
    }
}