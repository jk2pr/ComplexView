package com.jk.mindvalley.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jk.mindvalley.data.categories.CategoriesData


@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoriesData")
    suspend fun getAllCategoriesAsync(): CategoriesData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: CategoriesData)

}