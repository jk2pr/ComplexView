package com.jk.mindvalley.data.categories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoriesData (
	@PrimaryKey
	val data : Data
)