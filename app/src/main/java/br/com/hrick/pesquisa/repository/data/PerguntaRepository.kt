package br.com.hrick.pesquisa.repository.data

import android.content.Context
import br.com.hrick.pesquisa.entity.Opcao

import java.sql.SQLException

import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.entity.PerguntaOpcao
import br.com.hrick.pesquisa.entity.firebase.OpcaoFirebase
import br.com.hrick.pesquisa.entity.firebase.PerguntaFirebase
import br.com.hrick.pesquisa.helper.DatabaseHelper
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.j256.ormlite.misc.TransactionManager
import java.io.IOException

/**
 * Created by henrique.pereira on 12/09/2017.
 */

class PerguntaRepository private constructor(val ctx: Context) {

    private val helper: DatabaseHelper = DatabaseHelper(ctx)

    fun <T> constructUsingGson(type: Class<T>, assetName: String): T {

        val json = loadJSONFromAsset(assetName)

        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

        return gsonBuilder.create().fromJson(json, type)
    }

    fun loadJSONFromAsset(fileName: String): String? {
        return try {
            val stream = ctx.resources.assets.open(fileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()

            String(buffer)


        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

    }


    fun obterPerguntas(): List<Pergunta>? {
        var perguntas = helper.getPerguntaDao()?.queryForAll()
        if (perguntas == null || perguntas.isEmpty()){
            cargaInicialOpcoes()
            cargaInicialPerguntas()
            perguntas = helper.getPerguntaDao()?.queryForAll()
        }
        return perguntas
    }

    fun salvarPergunta(pergunta: Pergunta) {
        helper.getPerguntaDao()?.createOrUpdate(pergunta)
    }

    fun removerPergunta(pergunta: Pergunta) {
        helper.getPerguntaDao()?.delete(pergunta)
    }

    fun cargaInicialPerguntas() {
        val perguntas = constructUsingGson(Array<PerguntaFirebase>::class.java, "perguntas.json")
        TransactionManager.callInTransaction(helper.getPerguntaDao()?.connectionSource, {
            perguntas.forEach {perguntaFirebase ->
                val pergunta =  Pergunta(perguntaFirebase.id,perguntaFirebase.descricao,perguntaFirebase.ordenacao)
                salvarPergunta(pergunta)
                perguntaFirebase.opcoes?.forEach {
                    val opcaoPergunta = PerguntaOpcao("${perguntaFirebase.id}.${it.id}", Pergunta(perguntaFirebase.id),Opcao(it.id))
                    salvarPerguntaOpcao(opcaoPergunta)
                }
            }
            null
        })
    }

    fun cargaInicialOpcoes() {
        val opcoes = constructUsingGson(Array<OpcaoFirebase>::class.java, "opcoes.json")
        TransactionManager.callInTransaction(helper.getPerguntaDao()?.connectionSource, {
            opcoes.forEach {opcoesFirebase ->
                val opcao =  Opcao(opcoesFirebase.id,opcoesFirebase.descricao)
                salvarOpcao(opcao)
            }
            null
        })
    }

    private fun salvarPerguntaOpcao(opcaoPergunta: PerguntaOpcao) {
        helper.getPerguntaOpcaoDao()?.createOrUpdate(opcaoPergunta)
    }

    private fun salvarOpcao(opcao: Opcao) {
        helper.getOpcaoDao()?.createOrUpdate(opcao)
    }

    companion object {
        var instance: PerguntaRepository? = null
            private set
        fun init(ctx: Context) {
            if (null == instance) {
                instance = PerguntaRepository(ctx)
            }
        }
    }

}
