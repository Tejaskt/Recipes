package com.example.recipes.utils

data class PaginationState(
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0
)
