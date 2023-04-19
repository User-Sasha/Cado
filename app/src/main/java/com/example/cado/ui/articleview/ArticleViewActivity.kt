package com.example.cado.ui.articleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.repository.ArticleRepository

class ArticleViewActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String="Cado_TP"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_view)
    }

    val article: Article? = ArticleRepository.getArticle(1)
}