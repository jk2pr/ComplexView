package com.jk.mindvalley.db.converters

import androidx.room.TypeConverter
import com.jk.mindvalley.data.new_episode.Data
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

 class NewEpisodeDataConverter {
	@TypeConverter
	fun toString(value: Data): String {

		val jsonAdapter: JsonAdapter<Data> =
			Moshi.Builder().build().adapter<Data>(Data::class.java)

		return jsonAdapter.toJson(value)


	}

	@TypeConverter
	fun toData(value: String): Data? {
		val adapter: JsonAdapter<Data> = Moshi.Builder().build().adapter(Data::class.java)
		return adapter.fromJson(value)

	}


}