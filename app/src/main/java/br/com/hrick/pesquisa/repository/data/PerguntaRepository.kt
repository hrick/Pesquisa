package br.com.hrick.pesquisa.repository.data

import android.content.Context

import java.sql.SQLException

import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.helper.DatabaseHelper

/**
 * Created by henrique.pereira on 12/09/2017.
 */

class PerguntaRepository private constructor(ctx: Context) {

    private val helper: DatabaseHelper = DatabaseHelper(ctx)

    val perguntas: List<Pergunta>
        @Throws(Exception::class)
        get() = helper.getPerguntaDao()!!.queryForAll()

    @Throws(Exception::class)
    fun salvarPergunta(pergunta: Pergunta) {
        helper.getPerguntaDao()!!.createOrUpdate(pergunta)
    }

    @Throws(Exception::class)
    fun removerPergunta(pergunta: Pergunta) {
        helper.getPerguntaDao()!!.delete(pergunta)
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
