package com.example.cado.dao

import androidx.room.*
import com.example.cado.bo.Article

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun selectById(id:Long): Article?
    @Query("SELECT * FROM articles")
    suspend fun selectAll(): List<Article>
    @Update
    suspend fun update(article:Article)
    @Delete
    suspend fun delete(article: Article)
    @Insert
    suspend fun insert(article: Article)
}