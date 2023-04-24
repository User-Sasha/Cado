package com.example.cado.ui.articleedit

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.databinding.FragmentArticleEditBinding
import com.example.cado.repository.ArticleRepository
import java.time.LocalDate

class ArticleEditFragment : Fragment() {
    private lateinit var currentArticle : Article
    private lateinit var cloneArticle : Article
    private lateinit var binding : FragmentArticleEditBinding

    val args by navArgs<ArticleEditFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //lier le controleur - la vue au binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_edit,container,false)

        //recuperer les valeurs des arguments reçus (les arguments sont définis dans le navigation graph)
        val item: Article? = args.argArticle //un argument complexe (Parcelable)
        if (item != null){
            currentArticle = item
            cloneArticle = currentArticle.copy()
            //afficher les données
            displayData()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkBoxEtat.setOnClickListener {
            currentArticle.achete = (it as CheckBox).isChecked
            currentArticle.dateAchat = if (currentArticle.achete) LocalDate.now() else null
            displayData()
            ArticleRepository.replace(currentArticle)
        }


        binding.imageButtonCancel.setOnClickListener {
            if (cloneArticle != null){
                /*
                récupérer la copie
                 */
                currentArticle = cloneArticle
                displayData()
            }
        }

        binding.imageButtonOk.setOnClickListener {
            collectData()
            //mettre à jour la source de données. A terme....invoquer le manager pour persister les changements
            ArticleRepository.replace(currentArticle)
            //fermer le fragment
            this@ArticleEditFragment.activity?.onBackPressedDispatcher?.onBackPressed()
        }

    }

    private fun displayData() {
        binding.article = currentArticle
    }

    private fun collectData() {
        currentArticle.intitule = binding.editTextIntitule.text.toString()
        currentArticle.description = binding.editTextDescription.text.toString()
        var convert = 0.0
        var value = binding.editTextPrix.text.toString()

        if(!value.isEmpty()) {
            value = value.replace(',', '.')
            value = value.replace("€", "")
            value = value.replace("\\s".toRegex(), "")
            if (!value.isEmpty() && TextUtils.isDigitsOnly(value)) {
                convert = java.lang.Double.valueOf(value)
            }
        }
        currentArticle.prix = convert
        currentArticle.niveau = binding.ratingBarSatisfaction.rating.toInt().toByte()
        currentArticle.achete = binding.checkBoxEtat.isChecked
        if (!currentArticle.achete) {
            currentArticle.dateAchat = null
        }

        currentArticle.url = binding.editTextIntitule.text.toString()
    }


}