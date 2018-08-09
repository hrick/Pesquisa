package br.com.hrick.pesquisa.repository.firebase

import android.content.Context
import android.util.Log
import br.com.hrick.pesquisa.entity.Opcao
import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.entity.PerguntaOpcao
import br.com.hrick.pesquisa.entity.firebase.PerguntaFirebase
import br.com.hrick.pesquisa.helper.DatabaseHelper
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class PerguntaListener constructor(val context: Context): ChildEventListener {

    val helper: DatabaseHelper = DatabaseHelper(context)

    override fun onCancelled(p0: DatabaseError) {
        Log.i("onCancelled",p0.details)
    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
        Log.i("onChildMoved",p1)
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
        salvar(p0)
    }

    fun salvar(p0: DataSnapshot?) {
        val perguntaFirebase = p0?.getValue(PerguntaFirebase.FireBaseData::class.java)
        val pergunta =  Pergunta(perguntaFirebase?.idPergunta,perguntaFirebase?.descricao,perguntaFirebase?.tipo,perguntaFirebase?.ordenacao)
        helper.getPerguntaDao()?.createOrUpdate(pergunta)
        perguntaFirebase?.opcoes?.forEach {
            val opcaoPergunta = PerguntaOpcao("${perguntaFirebase.idPergunta}.${it.id}", Pergunta(perguntaFirebase.idPergunta), Opcao(it.id))
            helper.getPerguntaOpcaoDao()?.createOrUpdate(opcaoPergunta)
        }

    }

    fun remover(p0: DataSnapshot?) {
        val perguntaFirebase = p0?.getValue(PerguntaFirebase.FireBaseData::class.java)
        val pergunta =  Pergunta(perguntaFirebase?.idPergunta,perguntaFirebase?.descricao,perguntaFirebase?.tipo,perguntaFirebase?.ordenacao)
        helper.getPerguntaDao()?.delete(pergunta)
        perguntaFirebase?.opcoes?.forEach {
            val opcaoPergunta = PerguntaOpcao("${perguntaFirebase.idPergunta}.${it.id}", Pergunta(perguntaFirebase.idPergunta), Opcao(it.id))
            helper.getPerguntaOpcaoDao()?.delete(opcaoPergunta)
        }

    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        salvar(p0)
    }

    override fun onChildRemoved(p0: DataSnapshot) {
        remover(p0)
    }
}