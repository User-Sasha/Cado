package com.example.cado.ui.articleview

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.databinding.ActivityArticleViewBinding
import com.example.cado.repository.ArticleRepository
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate


class ArticleViewActivity : AppCompatActivity() {
//    companion object {
//        private const val TAG: String="Cado_TP"
//    }

    private lateinit var binding: ActivityArticleViewBinding


    private lateinit var currentArticle: Article


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,R.layout.activity_article_view
        )
//        val article: Article? = ArticleRepository.getArticle(1)
//        Log.i(TAG, article?.toString()?:"Article non trouvé")


//        if (article != null) {
//            textViewIntitule.text = article.intitule
//            textViewDescription.text = article.description
//            textViewPrix.text = article.prix.toString()
//            //Todo factoriser le code (Verification de la check box dans une fonction car utilisée 2x)
//            textViewDateAchat.visibility = if (checkBoxEtat.isChecked) View.VISIBLE else View.INVISIBLE
//            textViewDateAchat.text = article.dateAchat.toString()
//            checkBoxEtat.isChecked = article.achete
//            ratingBarSatisfaction.rating = article.niveau.toFloat()
//            textViewUrl.text = article.url
//
//            checkBoxEtat.setOnClickListener {
//                article.achete = (it as CheckBox).isChecked
//                article.dateAchat = if (article.achete) LocalDate.now() else null
//                var chaine = article.dateAchat.toString()
//                textViewDateAchat.text = "acheté le $chaine"
//                textViewDateAchat.visibility = if ((it as CheckBox).isChecked) View.VISIBLE else View.INVISIBLE
//            }
//        } else {
//            Log.i(TAG, article?.toString()?:"Article non trouvé")
//        }
        binding.checkBoxEtat.setOnClickListener {
            currentArticle.achete = (it as CheckBox).isChecked
            currentArticle.dateAchat = if (currentArticle.achete) LocalDate.now() else null
            displayData()
            ArticleRepository.replace(currentArticle)
        }


        binding.imageButtonInternet.setOnClickListener {
            val toast = Toast.makeText(
                this@ArticleViewActivity,
                "Page Internet disponible : " + currentArticle.url,
                Toast.LENGTH_SHORT)
            toast.show()
        }


        binding.imageButtonEdit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Etes vous sûr de vouloir modifier cet article ?")
                .setCancelable(true)
                .setPositiveButton("Oui") { dialog, id ->
                    Log.i("Modification", "Modifié")
                }
                .setNegativeButton("Non") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }



        binding.imageButtonSMS.setOnClickListener {
            val snack = Snackbar.make(it,"confirmer l'envoi du sms ?",Snackbar.LENGTH_LONG)
            snack.setAction("DISMISS") {
                // executed when DISMISS is clicked
                Log.i("Snackbar", "Snackbar Set Action - OnClick.")
            }
            snack.show()
        }


        //monter un article en mémoire pour le test
        val item: Article? = ArticleRepository.getArticle(2)
        if (item != null){
            currentArticle = item
            //afficher les données
            displayData()
        }

    }

    private fun displayData() {
        binding.setArticle(currentArticle)

    }
}