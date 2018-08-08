package br.com.hrick.pesquisa.views.main.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.hrick.pesquisa.R
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.views.main.OnItemSelectedListener
import kotlinx.android.synthetic.main.fragment_pesquisas_realizadas_list.*
import kotlinx.android.synthetic.main.fragment_pesquisas_realizadas_list.view.*
import kotlinx.android.synthetic.main.list_pesquisas_realizadas_row.view.*

class PesquisasRealizadasFragment : Fragment() {


    private var listener: OnItemSelectedListener? = null

    companion object {
        fun newInstance(): PesquisasRealizadasFragment {
            val frag = PesquisasRealizadasFragment()
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pesquisas_realizadas_list, container, false)
        val context = view?.context
        view.rvPesquisasRealizadas.layoutManager = LinearLayoutManager(context)
        return view
    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnItemSelectedListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun showData(list: List<Pesquisa>?) {
//        rvPesquisasRealizadas.adapter = PesquisasRealizasViewAdapter(list)
//        rvPesquisasRealizadas.adapter.notifyDataSetChanged()
    }


    inner class PesquisasRealizasViewAdapter(var pesquisas: List<Pesquisa>?) : RecyclerView.Adapter<PesquisasRealizasViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_pesquisas_realizadas_row, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pesquisa = pesquisas!![position]
            holder.bindPesquisa(pesquisa)
        }


        override fun getItemCount(): Int {
            if (pesquisas == null) return 0
            return pesquisas!!.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var mItem: Pesquisa? = null

            fun bindPesquisa(item: Pesquisa) {
                mItem = item
                mItem?.let { pesquisa -> itemView.setOnClickListener { listener?.abrirPesquisa(pesquisa) } }
                mItem?.let { pesquisa -> itemView.ibExcluir.setOnClickListener { listener?.excluirPesquisa(pesquisa) } }
            }
        }
    }
}
