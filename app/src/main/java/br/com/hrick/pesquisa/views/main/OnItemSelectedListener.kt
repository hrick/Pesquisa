package br.com.hrick.pesquisa.views.main

import br.com.hrick.pesquisa.entity.Pesquisa

interface OnItemSelectedListener {

    fun abrirPesquisa(item: Pesquisa)
    fun excluirPesquisa(item: Pesquisa)

}