package br.com.hrick.pesquisa.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

import java.io.Closeable
import java.sql.SQLException
import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.entity.Resposta
import br.com.hrick.pesquisa.entity.SubPergunta

class DatabaseHelper(private val context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), Closeable {


    private var respostaDao: Dao<Resposta, Int>? = null
    private var subPerguntaDao: Dao<SubPergunta, Int>? = null
    private var perguntaDao: Dao<Pergunta, Int>? = null
    private var pesquisaDao: Dao<Pesquisa, Int>? = null

    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            Log.i("DatabaseHelper", "onCreate database")

            TableUtils.createTable<Resposta>(connectionSource, Resposta::class.java)
            TableUtils.createTable<SubPergunta>(connectionSource, SubPergunta::class.java)
            TableUtils.createTable<Pergunta>(connectionSource, Pergunta::class.java)
            TableUtils.createTable<Pesquisa>(connectionSource, Pesquisa::class.java)


        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't create database", e)
            throw RuntimeException(e)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
        try {

            Log.i(DatabaseHelper::class.java.name, "onUpgrade")


            TableUtils.dropTable<Resposta, Any>(connectionSource, Resposta::class.java, true)
            TableUtils.dropTable<SubPergunta, Any>(connectionSource, SubPergunta::class.java, true)
            TableUtils.dropTable<Pergunta, Any>(connectionSource, Pergunta::class.java, true)
            TableUtils.dropTable<Pesquisa, Any>(connectionSource, Pesquisa::class.java, true)

            onCreate(db, connectionSource)
        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }

    }

    fun getRespostaDao(): Dao<Resposta, Int>? {
        if (null == respostaDao) {
            try {
                respostaDao = getDao<Dao<Resposta, Int>, Resposta>(Resposta::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return respostaDao
    }

    fun getPerguntaDao(): Dao<Pergunta, Int>? {
        if (null == perguntaDao) {
            try {
                perguntaDao = getDao<Dao<Pergunta, Int>, Pergunta>(Pergunta::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return perguntaDao
    }

    fun getSubPerguntaDao(): Dao<SubPergunta, Int>? {
        if (null == subPerguntaDao) {
            try {
                subPerguntaDao = getDao<Dao<SubPergunta, Int>, SubPergunta>(SubPergunta::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return subPerguntaDao
    }

    fun getPesquisaDao(): Dao<Pesquisa, Int>? {
        if (null == pesquisaDao) {
            try {
                pesquisaDao = getDao<Dao<Pesquisa, Int>, Pesquisa>(Pesquisa::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return pesquisaDao
    }

    companion object {

        private val DATABASE_NAME = "pesquisa_ceert.db"
        private val DATABASE_VERSION = 1
    }


}
