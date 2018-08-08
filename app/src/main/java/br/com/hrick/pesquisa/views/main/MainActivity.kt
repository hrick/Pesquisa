package br.com.hrick.pesquisa.views.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import br.com.hrick.pesquisa.R
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.views.main.fragments.InformacoesFragment
import br.com.hrick.pesquisa.views.main.fragments.PesquisasRealizadasFragment
import br.com.hrick.pesquisa.views.pesquisa.PesquisaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnExportExcelListener,OnItemSelectedListener {

    private val REQUEST_NOVA_PESQUISA: Int = 1


    private var informacoesFragment: InformacoesFragment? = null
    private var pesquisasRealizadasFragment: PesquisasRealizadasFragment? = null
    private var pesquisasRealizadas: List<Pesquisa>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        title = "CEERT Pesquisa"
        configurarFragments()

        setupViewPager(viewpager)
        fabNovaPesquisa.setOnClickListener {
            irParaPesquisa()
        }
    }


    private fun irParaPesquisa() {
        val intent = Intent(this, PesquisaActivity::class.java)
        startActivityForResult(intent, REQUEST_NOVA_PESQUISA)
    }

    private fun configurarFragments() {
        informacoesFragment = InformacoesFragment.newInstance()
        pesquisasRealizadasFragment = PesquisasRealizadasFragment.newInstance()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        tabs.setupWithViewPager(viewpager)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        pesquisasRealizadasFragment?.let { adapter.addFragment(it, getString(R.string.titulo_pesquisas_realizadas)) }
        informacoesFragment?.let { adapter.addFragment(it, getString(R.string.titulo_informacao)) }
        viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        atualizarLista()

    }

    fun atualizarLista() {
        pesquisasRealizadasFragment?.showData(pesquisasRealizadas)
        informacoesFragment?.showData(pesquisasRealizadas)
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun exportExcel() {
    }

    override fun abrirPesquisa(item: Pesquisa) {

        val intent = Intent(this, PesquisaActivity::class.java)
        intent.putExtra("EXTRA_PESQUISA_ID", item.idPesquisa)
        startActivityForResult(intent, REQUEST_NOVA_PESQUISA)

    }

    override fun excluirPesquisa(item: Pesquisa) {
    }
}
