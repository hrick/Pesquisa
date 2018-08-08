package br.com.hrick.pesquisa.repository.data

import android.content.Context

import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.entity.Resposta
import br.com.hrick.pesquisa.helper.DatabaseHelper

/**
 * Created by henrique.pereira on 12/09/2017.
 */

class RespostaRepository private constructor(ctx: Context) {

    private val helper: DatabaseHelper = DatabaseHelper(ctx)

    fun respostasPorPesquisa(idPesquisa: Int): MutableList<Resposta>? {
        return helper.getRespostaDao()?.queryBuilder()?.where()?.eq(Resposta.Fields.ID_PESQUISA, idPesquisa)?.query()
    }

    fun salvarResposta(resposta: Resposta) {
        helper.getRespostaDao()?.createOrUpdate(resposta)
    }

    companion object {
        var instance: RespostaRepository? = null
            private set
        fun init(ctx: Context) {
            if (null == instance) {
                instance = RespostaRepository(ctx)
            }
        }
    }

}
