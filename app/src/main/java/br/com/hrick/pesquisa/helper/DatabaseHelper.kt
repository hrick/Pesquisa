package br.com.hrick.pesquisa.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.hrick.pesquisa.entity.*

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

import java.io.Closeable
import java.sql.SQLException

class DatabaseHelper(private val context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), Closeable {


    private var respostaDao: Dao<Resposta, Int>? = null
    private var perguntaDao: Dao<Pergunta, Int>? = null
    private var pesquisaDao: Dao<Pesquisa, String>? = null
    private var opcaoDao: Dao<Opcao, Int>? = null
    private var perguntaOpcaoDao: Dao<PerguntaOpcao, String>? = null

    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTable<Resposta>(connectionSource, Resposta::class.java)

            TableUtils.createTable<PerguntaOpcao>(connectionSource, PerguntaOpcao::class.java)

            TableUtils.createTable<Opcao>(connectionSource, Opcao::class.java)

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
            TableUtils.dropTable<Pergunta, Any>(connectionSource, Pergunta::class.java, true)
            TableUtils.dropTable<Pesquisa, Any>(connectionSource, Pesquisa::class.java, true)
            TableUtils.dropTable<Opcao, Any>(connectionSource, Opcao::class.java, true)
            TableUtils.dropTable<PerguntaOpcao, Any>(connectionSource, PerguntaOpcao::class.java, true)


            onCreate(db, connectionSource)
        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }

    }

    fun getRespostaDao(): Dao<Resposta, Int>? {
        if (null == respostaDao) {
            try {
                respostaDao = getDao(Resposta::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return respostaDao
    }


    fun getPerguntaOpcaoDao(): Dao<PerguntaOpcao, String>? {
        if (null == perguntaOpcaoDao) {
            try {
                perguntaOpcaoDao = getDao(PerguntaOpcao::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return perguntaOpcaoDao
    }

    fun getOpcaoDao(): Dao<Opcao, Int>? {
        if (null == opcaoDao) {
            try {
                opcaoDao = getDao(Opcao::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return opcaoDao
    }

    fun getPerguntaDao(): Dao<Pergunta, Int>? {
        if (null == perguntaDao) {
            try {
                perguntaDao = getDao(Pergunta::class.java)
            } catch (e: java.sql.SQLException) {
                e.printStackTrace()
            }

        }
        return perguntaDao
    }

    fun getPesquisaDao(): Dao<Pesquisa, String>? {
        if (null == pesquisaDao) {
            try {
                pesquisaDao = getDao(Pesquisa::class.java)
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
