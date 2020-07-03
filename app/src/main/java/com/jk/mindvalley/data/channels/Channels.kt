package com.jk.mindvalley.data.channels

data class Channels (

	val title : String,
	val series : List<Series>,
	val mediaCount : Int,
	val latestMedia : List<LatestMedia>,
	val id : Int,
	val iconAsset : IconAsset?,
	val coverAsset : CoverAsset
)