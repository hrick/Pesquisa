package br.com.hrick.pesquisa.views.main

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import br.com.hrick.pesquisa.R
import br.com.hrick.pesquisa.entity.Pesquisa
import br.com.hrick.pesquisa.repository.data.PesquisaRepository
import br.com.hrick.pesquisa.repository.firebase.OpcaoListener
import br.com.hrick.pesquisa.repository.firebase.PerguntaListener
import br.com.hrick.pesquisa.views.main.fragments.InformacoesFragment
import br.com.hrick.pesquisa.views.main.fragments.PesquisasRealizadasFragment
import br.com.hrick.pesquisa.views.pesquisa.PesquisaActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.ArrayList
import me.zhanghai.android.effortlesspermissions.EffortlessPermissions
import android.support.annotation.NonNull
import android.widget.Toast
import com.ajts.androidmads.library.SQLiteToExcel
import me.zhanghai.android.effortlesspermissions.AfterPermissionDenied
import me.zhanghai.android.effortlesspermissions.OpenAppDetailsDialogFragment
import pub.devrel.easypermissions.AfterPermissionGranted
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.net.Uri
import java.io.File
import java.net.URI


class MainActivity : AppCompatActivity(), OnExportExcelListener, OnItemSelectedListener {

    private val REQUEST_NOVA_PESQUISA: Int = 1
    private val PERMISSIONS_SAVE_FILE = mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var informacoesFragment: InformacoesFragment? = null
    private var pesquisasRealizadasFragment: PesquisasRealizadasFragment? = null
    var pesquisasRealizadas: List<Pesquisa>? = null

    companion object {
        const val REQUEST_CODE_SAVE_FILE_PERMISSION = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMain)
        title = "CEERT Pesquisa"
        configurarFirebase()
        configurarFragments()

        setupViewPager(viewpager)
        fabNovaPesquisa.setOnClickListener {
            irParaPesquisa()
        }
        PesquisaRepository.init(this)
        carregarPesquisas()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EffortlessPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,
                this)
    }


    fun carregarPesquisas() {
        pesquisasRealizadas = PesquisaRepository.instance?.obterPesquisas()
    }

    override fun onResume() {
        super.onResume()
        carregarPesquisas()
    }

    private fun configurarFirebase() {
        val database = FirebaseDatabase.getInstance()
        val opcaoListener = OpcaoListener(this)
        val perguntaListener = PerguntaListener(this)
        val opcoesRef = database.reference.child("opcoes")
        val perguntasRef = database.reference.child("perguntas")
        opcoesRef.addChildEventListener(opcaoListener)
        perguntasRef.addChildEventListener(perguntaListener)
    }

    fun atualizarLista() {
        pesquisasRealizadasFragment?.showData(pesquisasRealizadas)
        informacoesFragment?.showData(pesquisasRealizadas)
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

    @AfterPermissionDenied(REQUEST_CODE_SAVE_FILE_PERMISSION)
    private fun onSaveFilePermissionDenied() {
        Toast.makeText(this, "Para continuar precisamos dessa permissão", Toast.LENGTH_SHORT).show()
    }

    private fun criarExcel() {
        val dialog = ProgressDialog.show(this@MainActivity, "Gerando excel",
                "Aguarde ....", true)

        val sqliteToExcel = SQLiteToExcel(this, "pesquisa_ceert.db")
        sqliteToExcel.exportSingleTable("TB_RESPOSTA", "respostas.xls", object : SQLiteToExcel.ExportListener {
            override fun onStart() {
            }

            override fun onCompleted(filePath: String) {
                if (dialog.isShowing)
                    dialog.dismiss()
                val intent = Intent(Intent.ACTION_VIEW)

                intent.setDataAndType(Uri.fromFile(File(filePath)), "application/vnd.ms-excel")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this@MainActivity, "Nenhum aplicativo encontrado para abrir o excel", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(e: Exception) {
                if (dialog.isShowing)
                    dialog.dismiss()
                showToastErrorExcel()
            }
        })


    }

    fun showToastErrorExcel() {
        Toast.makeText(this, "Erro ao criar excel", Toast.LENGTH_SHORT).show()

    }

    @AfterPermissionGranted(REQUEST_CODE_SAVE_FILE_PERMISSION)
    override fun exportExcel() {
        if (EffortlessPermissions.hasPermissions(MainActivity@ this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            criarExcel()
        } else if (EffortlessPermissions.somePermissionPermanentlyDenied(MainActivity@ this,
                        PERMISSIONS_SAVE_FILE)) {
            OpenAppDetailsDialogFragment.show(R.string.msg_precisa_permissao,
                    R.string.msg_ir_configuracao, this)
        } else {
            EffortlessPermissions.requestPermissions(MainActivity@ this,
                    R.string.msg_premissao_pedindo,
                    REQUEST_CODE_SAVE_FILE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun abrirPesquisa(item: Pesquisa) {

        val intent = Intent(this, PesquisaActivity::class.java)
        intent.putExtra("EXTRA_PESQUISA_ID", item.idPesquisa)
        startActivityForResult(intent, REQUEST_NOVA_PESQUISA)

    }

    override fun excluirPesquisa(item: Pesquisa) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.warning)
                .setMessage("Tem certeza que deseja excluir essa pesquisa ?")
                .setCancelable(false)
                .setPositiveButton("SIM") { dialog, id ->
                    dialog.dismiss()
                    PesquisaRepository.instance?.removerPesquisa(item)
                    carregarPesquisas()
                    atualizarLista()
                }
                .setNegativeButton("NÃO") { dialog, id ->
                    dialog.dismiss()
                }
                .setIcon(R.drawable.ic_alert_grey600_24dp)
                .show()

    }
}
