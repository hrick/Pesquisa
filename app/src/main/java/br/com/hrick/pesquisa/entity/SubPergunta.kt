package br.com.hrick.pesquisa.entity

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "TB_SUB_PERGUNTA")
data class SubPergunta (@DatabaseField(columnName = SubPergunta.Fields.ID_SUB_PERGUNTA, id = true)
                        val idPergunta: Int? = null,
                        @DatabaseField(columnName = SubPergunta.Fields.DESCRICAO)
                        val descricao: String? = null,
                        @DatabaseField(columnName = SubPergunta.Fields.ORDENACAO)
                        val ordenacao: Int? = null,
                        @DatabaseField(foreign = true, foreignAutoRefresh = false, columnName = SubPergunta.Fields.ID_PERGUNTA)
                        val pergunta: Pergunta? = null,
                        @ForeignCollectionField(eager = false)
                        val opcoes: ForeignCollection<String>? = null) {

        object Fields {
            const val ID_SUB_PERGUNTA = "ID_SUB_PERGUNTA"
            const val DESCRICAO = "DESCRICAO"
            const val ORDENACAO = "ORDENACAO"
            const val ID_PERGUNTA = "ID_PERGUNTA"
        }
}