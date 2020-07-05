package com.jk.mindvalley.data.channels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ChannelData (
	@PrimaryKey
	val data : Data
)
