package br.com.hrick.pesquisa.entity

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "TB_PESQUISA")
data class Pesquisa(@DatabaseField(id = true, columnName = Fields.ID_PESQUISA, generatedId = true)
                    val idPesquisa: Int? = null,

                    @ForeignCollectionField(eager = false)
                    val respostas: ForeignCollection<Resposta>? = null) {


    object Fields {
        const val ID_PESQUISA = "ID_PESQUISA"
    }
}