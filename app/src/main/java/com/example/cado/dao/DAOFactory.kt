package com.example.cado.dao

import com.example.cado.MyApp
import com.example.cado.dao.localdb.AppDatabase
import com.example.cado.dao.memory.ArticleDAOMemoryImpl

abstract class DAOFactory {
    companion object {
        fun createArticleDAO(type: DAOType): ArticleDAO?{
            var dao: ArticleDAO? = null
            when (type) {
                DAOType.MEMORY -> dao = ArticleDAOMemoryImpl()
                DAOType.LOCALDB-> dao =AppDatabase.getInstance(MyApp.context).articleDAO()
            }
            return dao
        }
    }
}