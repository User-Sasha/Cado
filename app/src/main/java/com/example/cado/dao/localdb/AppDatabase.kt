package com.example.cado.dao.localdb

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cado.R
import com.example.cado.bo.Article
import com.example.cado.dao.ArticleDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.time.LocalDate

@Database(entities = [Article::class], version = 1)
@TypeConverters(*[DateConverters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDAO(): ArticleDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        public fun getInstance(context: Context): AppDatabase{
            Log.i("APP", context.toString())
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "cado-database.db"
                ).addCallback(PopulateLocalDB(context)) //insérer des données à la création de la base
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    class PopulateLocalDB(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            GlobalScope.launch { populate() }
        }

        private fun loadJSONArray(): JSONArray?{

            val inputStream = context.resources.openRawResource(R.raw.articles)

            BufferedReader(inputStream.reader()).use {
                return JSONArray(it.readText())
            }
        }

        private suspend fun populate(){
            val dao = AppDatabase.getInstance(context)?.articleDAO()
            try {
                val articles = loadJSONArray()
                if (articles != null){
                    for (i in 0 until articles.length()){
                        val item = articles.getJSONObject(i)
                        val article = Article(item.getLong("id"),
                            item.getString("intitule"),
                            item.getString("description"),
                            item.getDouble("prix"),
                            item.getInt("niveau").toByte(),
                            item.getString("url"),
                            item.getBoolean("achete"),
                            if (item.getString("dateachat").isEmpty()) null else LocalDate.parse(item.getString("dateachat"))
                        )
                        dao?.insert(article)
                    }
                }
            }catch (e: JSONException){
                e.printStackTrace()
                Log.i("POPULATE", e.message.toString())
            }
        }
    }

}