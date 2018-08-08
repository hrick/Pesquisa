package br.com.hrick.pesquisa.entity.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OpcaoFirebase (
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("descricao")
        val descricao: String? = null) : Serializable {

    @IgnoreExtraProperties
    data class FireBaseData(
            val id: Int? = null,
            val descricao: String? = null)
}