package com.example.cado.ui.articleview

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.repository.ArticleRepository
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ArticleViewActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String="Cado_TP"
    }

    private lateinit var textViewIntitule: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewPrix: TextView
    private lateinit var textViewDateAchat: TextView
    private lateinit var checkBoxEtat: CheckBox
    private lateinit var ratingBarSatisfaction: RatingBar
    private lateinit var textViewUrl: TextView



    private lateinit var imageButtonInternet: ImageButton
    private lateinit var imageButtonEdit: ImageButton
    private lateinit var imageButtonSMS: ImageButton

    private lateinit var currentArticle: Article



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_view)

//        val article: Article? = ArticleRepository.getArticle(1)
//        Log.i(TAG, article?.toString()?:"Article non trouvé")
        textViewIntitule = findViewById(R.id.textViewIntitule) as TextView
        textViewDescription = findViewById(R.id.textViewDescription) as TextView
        textViewPrix = findViewById(R.id.textViewPrix) as TextView
        textViewDateAchat = findViewById(R.id.textViewDateAchat) as TextView
        checkBoxEtat = findViewById(R.id.checkBoxEtat) as CheckBox
        ratingBarSatisfaction = findViewById(R.id.ratingBarSatisfaction) as RatingBar
        textViewUrl = findViewById(R.id.textViewUrl) as TextView

        imageButtonInternet = findViewById(R.id.imageButtonInternet)
        imageButtonEdit = findViewById(R.id.imageButtonEdit)
        imageButtonSMS = findViewById(R.id.imageButtonSMS)



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
        checkBoxEtat.setOnClickListener {
            currentArticle.achete = (it as CheckBox).isChecked
            currentArticle.dateAchat = if (currentArticle.achete) LocalDate.now() else null
            displayData()
            ArticleRepository.replace(currentArticle)
        }


        imageButtonInternet.setOnClickListener {
            val toast = Toast.makeText(
                this@ArticleViewActivity,
                "Page Internet disponible : " + currentArticle.url,
                Toast.LENGTH_SHORT)
                toast.show()
        }


        imageButtonEdit.setOnClickListener {
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



        imageButtonSMS.setOnClickListener {
            val snack = Snackbar.make(it,"confirmer l'envoi du sms ?",Snackbar.LENGTH_LONG)
            snack.setAction("DISMISS") {
                // executed when DISMISS is clicked
                Log.i("Snackbar", "Snackbar Set Action - OnClick.")
            }
            snack.show()
        }


        //monter un article en mémoire pour le test
        val item: Article? = ArticleRepository.getArticle(1)
        if (item != null){
            currentArticle = item
            //afficher les données
            displayData()
        }

    }

    private fun displayData() {
        val local: Locale = Locale.getDefault()

        textViewIntitule.text = currentArticle.intitule
        textViewDescription.text = currentArticle.description
        val decimalFormat = DecimalFormat("#,###,###.## €")
        textViewPrix.setText(decimalFormat.format(currentArticle.prix))
        if (currentArticle.achete) {
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", local)
            textViewDateAchat.text =
                getString(R.string.article_view_dateachat) + dateFormat.format(
                    currentArticle.dateAchat
                )
        } else {
            textViewDateAchat.text = null
        }
        checkBoxEtat.isChecked = currentArticle.achete
        ratingBarSatisfaction.rating = currentArticle.niveau.toFloat()
        textViewUrl.text = currentArticle.url

    }
}