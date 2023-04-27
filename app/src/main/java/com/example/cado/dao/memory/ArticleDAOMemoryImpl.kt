package com.example.cado.dao.memory

import android.util.Log
import com.example.cado.bo.Article
import com.example.cado.dao.ArticleDAO


class ArticleDAOMemoryImpl: ArticleDAO {
    companion object {
        private const val TAG: String="Cado_TP"
        private val articlesInMemory: MutableList<Article>  = mutableListOf<Article>(
            Article(1,
                "Des lunettes de soleil (memory)",
                "RAY-BAN RB 4259 601/19 51/20",
                85.0,
                3,
                "https://www.optical-center.fr/lunettes-de-soleil/lunettes-de-soleil-RAY-BAN-RB-4259-60119-5120-25318.html?gclid=EAIaIQobChMIitHizMWe5QIVloXVCh1X6gw_EAQYASABEgLu0PD_BwE")
            ,Article(2,"Glacé (memory)", "Livre, XO éditions, Thriller, Bernard Minier",
                8.25, 4, "https://livre.fnac.com/a10408001/Bernard-Minier-Glace")
            ,Article(3,"Complètement cramé ! (memory)", "Livre, Pocket, Humour, Gilles Legardinier",
                7.60, 5, "https://www.amazon.fr/Compl%C3%A8tement-cram%C3%A9-Gilles-Legardinier/dp/2266246194")
        )
    }

    override suspend fun selectById(id: Long): Article? {
        var article: Article?=null
        try{
            article = articlesInMemory.first() { it.id == id }
        }catch (e: NoSuchElementException){
            Log.i(TAG, "Article non trouvé")
        }
        return article
    }

    override suspend fun update(article: Article) {
        val index: Int?
        index = articlesInMemory.indexOfFirst { it.id == article.id }
        if (index!=-1)
            articlesInMemory.set(index,article)
        else
            Log.i(TAG, "Article non trouvé")

    }

    override suspend fun selectAll(): List<Article> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(article: Article) {
        TODO("Not yet implemented")
    }


}