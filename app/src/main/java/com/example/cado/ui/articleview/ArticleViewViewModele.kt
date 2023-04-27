package com.example.cado.ui.articleview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cado.bo.Article
import com.example.cado.repository.ArticleRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class ArticleViewViewModele: ViewModel() {

    val observalbleCurrentArticle: MutableLiveData<Article?> = MutableLiveData()
    private var currentArticle: Article? = null

    fun getCurrentArticle(): Article? = currentArticle


    fun initCurrentArticle() {
        viewModelScope.launch {
            currentArticle = ArticleRepository.getArticle(1)
            observalbleCurrentArticle.value = currentArticle
        }
    }

    fun OnCheckedChangeAchete(isChecked: Boolean){
        currentArticle?.achete = isChecked
        currentArticle?.dateAchat = if (currentArticle?.achete == true) LocalDate.now() else null
        viewModelScope.launch {
            ArticleRepository.replace(currentArticle!!)
            observalbleCurrentArticle.value = currentArticle
        }
    }

    fun getMessageSMS(): String {
        val contenuMessage = StringBuffer()
        contenuMessage.append("Idée de cadeau :")
            .append(currentArticle?.intitule)
            .append("\nIl est dispo sur : ")
            .append(currentArticle?.url)
        return contenuMessage.toString()
    }

}