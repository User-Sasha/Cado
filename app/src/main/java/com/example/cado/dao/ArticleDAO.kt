package com.example.cado.dao

import com.example.cado.bo.Article

interface ArticleDAO {

    fun selectById(id: Long): Article?

}