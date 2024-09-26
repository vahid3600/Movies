package com.vahid.tvshows.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String? = null
)