package com.example.cado.ui.articleedit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.databinding.FragmentArticleEditBinding

class ArticleEditFragment : Fragment() {
    private lateinit var binding : FragmentArticleEditBinding
    private lateinit var viewModel: ArticleEditViewModele

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

        viewModel = ViewModelProvider(this).get(ArticleEditViewModele::class.java)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item: Article? = args.currentArticle
        if (item != null) {
            viewModel.initCurrentArticle(item)
        }

        binding.checkBoxEtat.setOnClickListener {
           viewModel.OnCheckedChangeAchete()
        }


        binding.imageButtonCancel.setOnClickListener {
            viewModel.undo()
        }

        binding.imageButtonOk.setOnClickListener {
            viewModel.save()
            this@ArticleEditFragment.activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel.observableTopRefresh.observe(viewLifecycleOwner, {
            if (it){
                Log.i("OBSERVER", "ArticleEditFragment")
//                binding.ArticleVM
            }
        })

    }



}