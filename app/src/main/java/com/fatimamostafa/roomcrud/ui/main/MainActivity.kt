package com.fatimamostafa.roomcrud.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatimamostafa.roomcrud.R
import com.fatimamostafa.roomcrud.base.BaseActivity
import com.fatimamostafa.roomcrud.database.EmployeeModel
import com.fatimamostafa.roomcrud.di.ViewModelInjectionField
import com.fatimamostafa.roomcrud.di.qualifiers.ViewModelInjection
import com.fatimamostafa.roomcrud.ui.update.UpdateEmployeeActivity
import com.fatimamostafa.roomcrud.utils.FileUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class MainActivity : BaseActivity(), Toolbar.OnMenuItemClickListener {
    lateinit var employeeList: List<EmployeeModel>

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MainVM>
    private lateinit var adapter: EmployeeAdapter

    override fun layoutRes() = R.layout.activity_main

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add -> {
                navigateUpdate(null)
                true
            }
            else -> false
        }
    }

    private fun navigateUpdate(item: EmployeeModel?) {
        UpdateEmployeeActivity.employee = item
        val intent = Intent(this, UpdateEmployeeActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.inflateMenu(R.menu.menu_details)
        toolbar.setOnMenuItemClickListener(this)

        btn_export.setOnClickListener {
            exportEmployeeList()
        }

        btn_import.setOnClickListener {
            importEmployeeList()
        }


        recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity, RecyclerView
                    .VERTICAL
            )
        )
        adapter = EmployeeAdapter(
            mutableListOf(),
            application,
            object : EmployeeAdapter.ItemClickListener {
                override fun clickEdit(item: EmployeeModel) {
                    navigateUpdate(item)
                }

                override fun clickDelete(item: EmployeeModel) {
                    viewModel.get().delete(item)
                }

            })
        recyclerView.adapter = adapter

        viewModel.get().response.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.get().getAllEmployees().observe(this, Observer<List<EmployeeModel>> {
            employeeList = it
            adapter.swapData(it)
        })


    }

    private fun exportEmployeeList() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    var path = "/storage/emulated/0/Download"
                    viewModel.get().exportJSON(employeeList, path)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_LONG).show()
                }
            }
            )
            .check()
    }

    private fun importEmployeeList() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)
                    startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {}
            }
            )
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data

            selectedFile?.let {
                Log.d("File: ", FileUtils.getPath(this, it))

                val inputStream: InputStream? = contentResolver.openInputStream(it)
                val r = BufferedReader(InputStreamReader(inputStream))
                val jsonString = r.readText()
                Log.d("File JSON: ", jsonString)

                viewModel.get().importJSON(jsonString)
            }
        }
    }

}