package br.com.hrick.pesquisa.entity

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "TB_OPCAO")
data class Opcao (@DatabaseField(id = true, columnName = Fields.ID_OPCAO)
                  var idOpcao: Int? = null,
                  @DatabaseField(columnName = Fields.DESCRICAO)
                  var descricao: String? = null,
                  @ForeignCollectionField(eager = false)
                  val perguntaOpcao: ForeignCollection<PerguntaOpcao>? = null ): Serializable {


    object Fields {
        const val ID_OPCAO = "ID_OPCAO"
        const val ID_PERGUNTA = "ID_PERGUNTA"
        const val DESCRICAO = "DESCRICAO"
    }

}