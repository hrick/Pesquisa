package br.com.hrick.pesquisa.entity

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "TB_PERGUNTA")
data class Pergunta(@DatabaseField(columnName = Pergunta.Fields.ID_PERGUNTA, id = true)
                    val idPergunta: Int? = null,
                    @DatabaseField(columnName = Pergunta.Fields.DESCRICAO)
                    val descricao: String? = null,
                    @DatabaseField(columnName = Pergunta.Fields.ORDENACAO)
                    val ordenacao: Int? = null,
                    @ForeignCollectionField(eager = false)
                    val opcoes: ForeignCollection<String>? = null,
                    @ForeignCollectionField(eager = false)
                    val subPerguntas: ForeignCollection<SubPergunta>? = null) {

    object Fields {
        const val ID_PERGUNTA = "ID_PERGUNTA"
        const val DESCRICAO = "DESCRICAO"
        const val ORDENACAO = "ORDENACAO"
    }
}