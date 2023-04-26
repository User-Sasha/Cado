package com.example.cado.ui.articleview

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.cado.R
import com.example.cado.databinding.FragmentArticleViewBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ArticleViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleViewFragment : Fragment() {
    private lateinit var binding: FragmentArticleViewBinding
    private lateinit var viewModel: ArticleViewViewModele

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                envoyerSms()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                Toast.makeText(this@ArticleViewFragment.context, "Autorisation refusée !", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentArticleViewBinding>(inflater, R.layout.fragment_article_view, container, false)

        viewModel = ViewModelProvider(this).get(ArticleViewViewModele::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        binding.checkBoxEtat.setOnClickListener {
           viewModel.OnCheckedChangeAchete((it as CheckBox).isChecked)
        }


        binding.imageButtonInternet.setOnClickListener {
            accederUrlArticle()
        }


        binding.imageButtonEdit.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage("Etes vous sûr de vouloir modifier cet article ?")
                .setCancelable(true)
                .setPositiveButton("Oui") { dialog, id ->
//                    val action = ArticleViewFragmentDirections
                    val action = ArticleViewFragmentDirections.actionArticleViewFragmentToArticleEditFragment(viewModel.getCurrentArticle()!!)
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
            preparerSms()
        }


        viewModel.observalbleCurrentArticle.observe(viewLifecycleOwner, {
            Log.i("OBSERVER", "ArticleViewFragment")
            binding.article = it
        })
    }

    private fun envoyerSms() {
        var numero = "0666666666"
        try {
            val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                binding.root.context.getSystemService(SmsManager::class.java)
            } else {
                SmsManager.getDefault()
            }
            smsManager.sendTextMessage(
                numero,
                null,
                viewModel.getMessageSMS(),
                null,
                null
            )
            Toast.makeText(this@ArticleViewFragment.context, "SMS Envoyé", Toast.LENGTH_SHORT)
                .show()
        }catch (e: java.lang.Exception){
            Toast.makeText(this@ArticleViewFragment.context, "Erreur lors de l'envoi du message", Toast.LENGTH_SHORT).show()
        }
    }


    private fun preparerSms(){
        var numero = "0666666666"
        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SENDTO
//            type ="text/plain"
//            data = Uri.parse("sms:"+numero)
            setDataAndType(Uri.parse("sms:"+numero), "text/plain")
            putExtra("sms_body", viewModel.getMessageSMS())
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this@ArticleViewFragment.context, "Erreur lors de l'envoi du message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun accederUrlArticle() {
        val url: String? = viewModel.observalbleCurrentArticle.value?.url
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            val toast = Toast.makeText(
                this@ArticleViewFragment.context,
                "Error lors de la redirection vers la page internet",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }


}