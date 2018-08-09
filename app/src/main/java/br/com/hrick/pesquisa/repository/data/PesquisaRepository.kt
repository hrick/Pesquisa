package br.com.hrick.pesquisa.repository.data

import android.content.Context

import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.entity.Resposta
import br.com.hrick.pesquisa.helper.DatabaseHelper

/**
 * Created by henrique.pereira on 12/09/2017.
 */

class PesquisaRepository private constructor(ctx: Context) {

    private val helper: DatabaseHelper = DatabaseHelper(ctx)

    fun obterPesquisa(idPesquisa: String): Pesquisa? {
        return helper.getPesquisaDao()?.queryForId(idPesquisa)
    }

    fun criarPesquisa(pesquisa: Pesquisa){
        helper.getPesquisaDao()?.createOrUpdate(pesquisa)
    }

    fun obterPesquisas(): List<Pesquisa>? {
        return helper.getPesquisaDao()?.queryForAll()
    }

    fun removerPesquisa(item: Pesquisa) {
        helper.getPesquisaDao()?.delete(item)
    }

    companion object {
        var instance: PesquisaRepository? = null
            private set
        fun init(ctx: Context) {
            if (null == instance) {
                instance = PesquisaRepository(ctx)
            }
        }
    }

}
