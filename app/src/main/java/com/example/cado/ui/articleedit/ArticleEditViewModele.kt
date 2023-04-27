package com.example.cado.ui.articleedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cado.bo.Article
import java.time.LocalDate
import com.example.cado.repository.ArticleRepository
import kotlinx.coroutines.launch


class ArticleEditViewModele: ViewModel() {

    var intitule: String = ""
    var description: String = ""
    var prix: Double = 0.0
    var niveau: Byte = 0
    var url:String = ""
    var achete:Boolean = false
    var dateAchat: LocalDate? = null

    val observableTopRefresh: MutableLiveData<Boolean> = MutableLiveData()

    private var currentArticle: Article? = null
    private var cloneArticle: Article? = null

    fun initCurrentArticle(article: Article) {
        currentArticle = article
        if (currentArticle != null){
            cloneArticle = currentArticle?.copy()
            displayData()
        }
    }

    fun OnCheckedChangeAchete(){
        this.dateAchat = if (this.achete == true) LocalDate.now() else null
        observableTopRefresh.value = true
    }

    private fun displayData() {
        currentArticle?.let {
            this.intitule = it.intitule
            this.description = it.description
            this.prix = it.prix
            this.niveau = it.niveau
            this.url = it.url
            this.achete = it.achete
            this.dateAchat = it.dateAchat
        }
        observableTopRefresh.value = true
    }

    private fun collectData(){
        if (currentArticle == null) {
            currentArticle = Article(0,this.intitule, this.description, this.prix, this.niveau, this.url, this.achete, this.dateAchat )
        } else {
            currentArticle!!.intitule = this.intitule
            currentArticle!!.description = this.description
            currentArticle!!.prix = this.prix
            currentArticle!!.achete = this.achete
            currentArticle!!.dateAchat = this.dateAchat
            currentArticle!!.niveau = this.niveau
            currentArticle!!.url = this.url
        }
    }

    fun undo(){
        if(cloneArticle != null){
            currentArticle = cloneArticle
            displayData()
        }
    }

    fun save() {
        viewModelScope.launch {
            if (currentArticle!=null){
                collectData()
                ArticleRepository.replace(currentArticle!!)
            }
        }
    }
}