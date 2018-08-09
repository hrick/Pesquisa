package br.com.hrick.pesquisa.entity.firebase

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PerguntaFirebase(
        @SerializedName("idPergunta")
        val id: Int? = null,
        @SerializedName("descricao")
        val descricao: String? = null,
        @SerializedName("tipo")
        val tipo: String? = null,
        @SerializedName("opcoes")
        val opcoes: List<OpcaoFirebase>? = null,
        @SerializedName("ordenacao")
        val ordenacao: Int? = null) : Serializable {

    @IgnoreExtraProperties
    data class FireBaseData(
            val idPergunta: Int? = null,
            val descricao: String? = null,
            val tipo: String? = null,
            val ordenacao: Int? = null,
            val opcoes: List<OpcaoFirebase>? = null)
}