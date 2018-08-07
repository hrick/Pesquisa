package br.com.hrick.pesquisa.entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "TB_RESPOSTA")
data class Resposta(@DatabaseField(columnName = Resposta.Fields.ID_RESPOSTA, generatedId = true)
                    val idResposta: Int? = null,
                    @DatabaseField(columnName = Resposta.Fields.ID_PERGUNTA)
                    val idPergunta: Int? = null,
                    @DatabaseField(columnName = Resposta.Fields.ID_SUB_PERGUNTA)
                    val idSubPergunta: Int? = null,
                    @DatabaseField(columnName = Resposta.Fields.OPCAO_PERGUNTA)
                    val opcaoPergunta: String? = null,
                    @DatabaseField(columnName = Resposta.Fields.OPCAO_SUB_PERGUNTA)
                    val opcaoSubPergunta: String? = null,
                    @DatabaseField(foreign = true, foreignAutoRefresh = false, columnName = Resposta.Fields.ID_PESQUISA)
                    val pergunta: Pesquisa? = null) {

    object Fields {
        const val ID_RESPOSTA = "ID_RESPOSTA"
        const val ID_PESQUISA = "ID_PESQUISA"
        const val ID_PERGUNTA = "ID_PERGUNTA"
        const val OPCAO_PERGUNTA = "OPCAO_PERGUNTA"
        const val OPCAO_SUB_PERGUNTA = "OPCAO_SUB_PERGUNTA"
        const val ID_SUB_PERGUNTA = "ID_SUB_PERGUNTA"
    }

}