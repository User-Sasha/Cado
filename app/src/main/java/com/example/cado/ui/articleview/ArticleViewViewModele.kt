package com.example.cado.ui.articleview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cado.bo.Article
import com.example.cado.repository.ArticleRepository
import java.time.LocalDate

class ArticleViewViewModele: ViewModel() {

    val observalbleCurrentArticle: MutableLiveData<Article?> = MutableLiveData()
    private var currentArticle: Article? = null

    fun getCurrentArticle(): Article? = currentArticle


    init {
        currentArticle = ArticleRepository.getArticle(1)
        observalbleCurrentArticle.value = currentArticle
    }

    fun OnCheckedChangeAchete(isChecked: Boolean){
        currentArticle?.achete = isChecked
        currentArticle?.dateAchat = if (currentArticle?.achete == true) LocalDate.now() else null
        observalbleCurrentArticle.value = currentArticle
    }

    fun getMessageSMS(): String {
        val contenuMessage = StringBuffer()
        contenuMessage.append("Idée de cadeau :")
            .append(currentArticle?.intitule)
            .append("\nIl est dispo sur : ")
            .append(currentArticle?.url)
        return contenuMessage.toString()
    }


//    private fun getIntitule(): String? {
//        return currentArticle.value?.intitule
//    }
//
//    private fun getDescription(): String? {
//        return currentArticle.value?.description
//    }
//
//    private fun getPrix(): Double? {
//        return currentArticle.value?.prix
//    }
//
//    private fun getNiveau(): Byte? {
//        return currentArticle.value?.niveau
//    }
//
//    private fun getUrl(): String? {
//        return currentArticle.value?.url
//    }
//
//    private fun getAchete(): Boolean? {
//        return currentArticle.value?.achete
//    }
//
//    private fun getDateAchat(): LocalDate? {
//        return currentArticle.value?.dateAchat
//    }
}