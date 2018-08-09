package br.com.hrick.pesquisa.views.pesquisa

import android.Manifest
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.*
import br.com.hrick.pesquisa.R
import br.com.hrick.pesquisa.entity.Pergunta
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.entity.Resposta
import br.com.hrick.pesquisa.repository.data.PerguntaRepository
import br.com.hrick.pesquisa.repository.data.PesquisaRepository
import br.com.hrick.pesquisa.repository.data.RespostaRepository
import kotlinx.android.synthetic.main.activity_pesquisa.*
import java.util.*
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.toolbar.*


class PesquisaActivity : AppCompatActivity() {

    var listaPerguntas: List<Pergunta>? = null
    var pesquisa: Pesquisa? = null
    var listarRespostas: MutableList<Resposta>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        setSupportActionBar(toolbar)
        title = "Nova Pesquisa"
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
        PerguntaRepository.init(this)
        RespostaRepository.init(this)
        PesquisaRepository.init(this)
        val pesquisaId = intent.getStringExtra("EXTRA_PESQUISA_ID")
        pesquisa = PesquisaRepository.instance?.obterPesquisa(idPesquisa = pesquisaId ?: "")
        listaPerguntas = PerguntaRepository.instance?.obterPerguntas()
        listarRespostas = RespostaRepository.instance?.respostasPorPesquisa(pesquisaId ?: "")
        if (listarRespostas == null) {
            listarRespostas = mutableListOf()
        }
        carregarTelaDinamica()
        btSalvar.setOnClickListener {
            salvarPesquisa()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun carregarTelaDinamica() {
        if (listaPerguntas != null && listaPerguntas?.isNotEmpty() == true) {
            listaPerguntas?.forEach { pergunta ->

                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.bottomMargin = 8

                val resposta = listarRespostas?.find { it.idPergunta == pergunta.idPergunta }

                if (pergunta.tipo == "rg") {

                    val textView = TextView(this)
                    textView.textSize = 16f
                    textView.text = pergunta.descricao
                    llLayoutPesquisa.addView(textView, params)

                    if (pergunta.opcoes?.isNotEmpty() == true) {
//                        radioButton.isChecked = opcao.opcao?.descricao == resposta?.opcaoPergunta

                        val radioGroup = RadioGroup(this)
                        radioGroup.orientation = LinearLayout.VERTICAL
                        radioGroup.tag = pesquisa

                        for (opcao in pergunta.opcoes) {
                            val radioButton = RadioButton(this)
                            radioButton.text = opcao.opcao?.descricao
                            radioButton.tag = opcao.id
                            val paramsRadio = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT)
                            paramsRadio.bottomMargin = 16
                            paramsRadio.topMargin = 16
                            radioGroup.addView(radioButton, paramsRadio)
                        }
                        radioGroup.setOnCheckedChangeListener { radio, idRadioButton ->
                            val radioButton2 = radio.findViewById<RadioButton>(idRadioButton)
                            salvarResposta(radioButton2.text, pergunta)
                        }
                        llLayoutPesquisa.addView(radioGroup)

                        for (opcao in pergunta.opcoes) {
                            val radioButton = radioGroup.findViewWithTag<RadioButton>(opcao.id)
                            if (radioButton != null)
                                radioButton.isChecked = opcao.opcao?.descricao == resposta?.opcaoPergunta
                        }
                    }

                } else if (pergunta.tipo == "input") {
                    val params2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    params2.bottomMargin = 8
                    params2.topMargin = 8

                    val textView = TextView(this)
                    textView.textSize = 14f
                    textView.text = pergunta.descricao
                    textView.setTypeface(null, Typeface.BOLD)
                    llLayoutPesquisa.addView(textView, params2)

                    val editText = EditText(this)
                    editText.textSize = 16f
                    editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    editText.maxLines = 1
                    editText.setText(resposta?.opcaoPergunta ?: "")
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {
                            salvarResposta(p0.toString(), pergunta)

                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        }

                    })
                    llLayoutPesquisa.addView(editText, params)
                } else {
                    val textView = TextView(this)
                    textView.textSize = 16f
                    textView.text = pergunta.descricao
                    llLayoutPesquisa.addView(textView, params)
                }
            }
        }
    }


    private fun salvarResposta(text: CharSequence?, pergunta: Pergunta) {
        val resposta = listarRespostas?.find { it.idPergunta == pergunta.idPergunta }
        if (resposta != null) {
            resposta.opcaoPergunta = text.toString()
        } else {
            listarRespostas?.add(Resposta(idPergunta = pergunta.idPergunta, opcaoPergunta = text.toString()))
        }
    }

    private fun salvarPesquisa() {
        if (pesquisa == null) {
            pesquisa = Pesquisa(idPesquisa = UUID.randomUUID().toString())
            PesquisaRepository.instance?.criarPesquisa(pesquisa!!)
        }
        listarRespostas?.forEach { resposta ->
            if (resposta.pesquisa == null) {
                resposta.pesquisa = pesquisa
            }
            RespostaRepository.instance?.salvarResposta(resposta)
        }
        finish()
    }
}
