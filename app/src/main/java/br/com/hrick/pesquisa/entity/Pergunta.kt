package br.com.hrick.pesquisa.entity

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "TB_PERGUNTA")
data class Pergunta(@DatabaseField(columnName = Pergunta.Fields.ID_PERGUNTA, id = true)
                    val idPergunta: Int? = null,
                    @DatabaseField(columnName = Pergunta.Fields.DESCRICAO)
                    val descricao: String? = null,
                    @DatabaseField(columnName = Pergunta.Fields.TIPO)
                    val tipo: String? = null,
                    @DatabaseField(columnName = Pergunta.Fields.ORDENACAO)
                    val ordenacao: Int? = null,
                    @ForeignCollectionField(eager = false)
                    val opcoes: ForeignCollection<PerguntaOpcao>? = null): Serializable {

    object Fields {
        const val ID_PERGUNTA = "ID_PERGUNTA"
        const val DESCRICAO = "DESCRICAO"
        const val ORDENACAO = "ORDENACAO"
        const val TIPO = "TIPO"
    }
}