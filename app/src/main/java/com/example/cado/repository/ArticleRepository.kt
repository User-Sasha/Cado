package com.example.cado.repository

import com.example.cado.bo.Article
import com.example.cado.dao.ArticleDAO
import com.example.cado.dao.DAOFactory
import com.example.cado.dao.DAOType


object ArticleRepository {
    val articleDao: ArticleDAO? = DAOFactory.createArticleDAO(DAOType.MEMORY)

    /**
     * Retourne le premier article exposé par la source de données
     * @return
     */
    fun getArticle(id: Long): Article? {
        return articleDao?.selectById(id)
    }

    /**
     * Met à jour l'article à la position courante
     * @param article
     */
    fun replace(article: Article){
        articleDao?.update(article)
    }

}