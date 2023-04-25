package com.example.cado.ui.articleview

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.databinding.FragmentArticleViewBinding
import com.example.cado.repository.ArticleRepository
import java.time.LocalDate


/**
 * A simple [Fragment] subclass.
 * Use the [ArticleViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleViewFragment : Fragment() {
    private lateinit var binding: FragmentArticleViewBinding

    private lateinit var currentArticle: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentArticleViewBinding>(inflater, R.layout.fragment_article_view, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        binding.checkBoxEtat.setOnClickListener {
            currentArticle.achete = (it as CheckBox).isChecked
            currentArticle.dateAchat = if (currentArticle.achete) LocalDate.now() else null
            displayData()
            ArticleRepository.replace(currentArticle)
        }


        binding.imageButtonInternet.setOnClickListener {
            accederUrlArticle()
        }



        binding.imageButtonEdit.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Etes vous sûr de vouloir modifier cet article ?")
                .setCancelable(true)
                .setPositiveButton("Oui") { dialog, id ->
//                    val action = ArticleViewFragmentDirections
                    val action = ArticleViewFragmentDirections.actionArticleViewFragmentToArticleEditFragment(currentArticle)
                    Navigation.findNavController(it).navigate(action)
                }
                .setNegativeButton("Non") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }



        binding.imageButtonSMS.setOnClickListener {
            envoyerSms()
        }


        //monter un article en mémoire pour le test
        val item: Article? = ArticleRepository.getArticle(1)
        if (item != null){
            currentArticle = item
            //afficher les données
            displayData()
        }
    }

    private fun envoyerSms() {
        var numero = "0666666666"
        val intent = Intent(Intent.ACTION_SENDTO)
        try {
            intent.setData(Uri.parse("smsto:$numero"))
            intent.putExtra("sms_body", "Idée de cadeau : " + currentArticle.url)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.i("EnvoiSms", "Erreur lors de l'envoi du sms")
            val toast = Toast.makeText(
                context,
                "Erreur lors de l'envoi du sms",
                Toast.LENGTH_SHORT
            )
            toast.show()

        }
    }

    private fun accederUrlArticle() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.url)))
        } catch (e: ActivityNotFoundException) {
            val toast = Toast.makeText(
                context,
                "Url du cadeau : " + currentArticle.url,
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

/*    private fun modifierArticle() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Etes vous sûr de vouloir modifier cet article ?")
            .setCancelable(true)
            .setPositiveButton("Oui") { dialog, id ->
//                    val action = ArticleViewFragmentDirections
                val action = ArticleViewFragmentDirections.actionArticleViewFragmentToArticleEditFragment(currentArticle)
                Navigation.findNavController(it).navigate(action)
            }
            .setNegativeButton("Non") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }*/

    private fun displayData() {
        binding.article = currentArticle

    }

}