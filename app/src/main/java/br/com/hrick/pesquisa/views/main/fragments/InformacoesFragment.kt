package br.com.hrick.pesquisa.views.main.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.hrick.pesquisa.R
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.views.main.OnExportExcelListener

class InformacoesFragment : Fragment() {
    private var listener: OnExportExcelListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_informacoes, container, false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnExportExcelListener

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun showData(pesquisasRealizadas: List<Pesquisa>?) {
//        tvQuantidade.tvQuantidade.text = pesquisasRealizadas?.size.toString()
//        ibExportExcel.setOnClickListener { listener?.exportExcel() }
    }

    companion object {
        fun newInstance(): InformacoesFragment {
            val frag = InformacoesFragment()
            return frag
        }
    }
}
