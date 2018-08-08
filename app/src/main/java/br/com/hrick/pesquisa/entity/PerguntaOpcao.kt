package br.com.hrick.pesquisa.entity

import com.j256.ormlite.field.DatabaseField
import java.io.Serializable
/*
id = "$idPergunta.$idOpcao"
 */
data class PerguntaOpcao (@DatabaseField(id = true, columnName = Fields.ID_PERGUNTA_OPCAO)
                                val id: String? = null,

                                @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Fields.ID_PERGUNTA)
                                val pergunta: Pergunta? = null,

                                @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = Fields.ID_OPCAO)
                                val opcao: Opcao? = null

) : Serializable {


    object Fields {
        const val ID_PERGUNTA_OPCAO = "ID_PERGUNTA_OPCAO"
        const val ID_PERGUNTA = "ID_PERGUNTA"
        const val ID_OPCAO = "ID_OPCAO"
    }
}