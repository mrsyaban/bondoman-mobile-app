package com.pbd.psi.ui.settings

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.pbd.psi.LoginActivity
import com.pbd.psi.databinding.FragmentSettingsBinding
import com.pbd.psi.room.TransactionEntity
import com.pbd.psi.ui.transaction.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedpreferences: SharedPreferences
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedpreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKeluar.setOnClickListener {
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            Toast.makeText(requireActivity(), "Success logout", Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnUploadHistory.setOnClickListener {
            val email = sharedpreferences.getString(EMAIL, "")
            val intentEmail = Intent(Intent.ACTION_SEND)
            intentEmail.type = "text/plain"
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Upload History")
            intentEmail.putExtra(Intent.EXTRA_TEXT, "Berikut ini laporan hasil transaksi akun $email :\n")
            startActivity(Intent.createChooser(intentEmail, "Send Email"))
        }

        binding.btnSettings.setOnClickListener {
            Log.d("button_export", "MASUKKK")
//            lifecycleScope.launch {
//                    Log.d("button_export", "DALEMM")
//                    val transactions = viewModel.fetchAllTransactions()
//                    Log.d("button_export", "DALEM1")
//                    exportTransactionsToExcel(transactions)
//                    Log.d("button_export", "DALEM2")
//                    Log.d("button_export", "KELUAR")
//            }
            viewModel.transactionList.observe(viewLifecycleOwner) { transItems ->
                Log.d("button_export", "MASUKKK")
                val transactions = requireNotNull(transItems) { "Transaction list is null" }
                val transList = ArrayList(transactions)
                Log.d("button_export", "MASUKKK")
                Log.d("TransactionList", "TransactionList: $transList")
                exportTransactionsToExcel(transList)
            }

        }
    }

    private fun exportTransactionsToExcel(transactions: List<TransactionEntity>) {
        val sdf = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())
        val excelFileName = "Transaction_Report_$currentDateTime.xlsx"

        val excelFilePath = "${requireContext().externalCacheDir}/$excelFileName"

        // Write transactions data to Excel file
        val outputStream = FileOutputStream(excelFilePath)

        outputStream.use { fileOut ->
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Transactions")

            // Create header row
            val headerRow = sheet.createRow(0)
            headerRow.createCell(0).setCellValue("Name")
            headerRow.createCell(1).setCellValue("Category")
            headerRow.createCell(2).setCellValue("Amount")
            headerRow.createCell(3).setCellValue("Date")
            headerRow.createCell(4).setCellValue("Location")
            headerRow.createCell(5).setCellValue("Longitude")
            headerRow.createCell(6).setCellValue("Latitude")

            // Fill data rows
            var rowNum = 1
            for (transaction in transactions) {
                val row = sheet.createRow(rowNum++)
                row.createCell(0).setCellValue(transaction.name)
                row.createCell(1).setCellValue(transaction.category.toString())
                row.createCell(2).setCellValue(transaction.amount.toDouble())
                row.createCell(3).setCellValue(sdf.format(transaction.date))
                row.createCell(4).setCellValue(transaction.location)
                row.createCell(5).setCellValue(transaction.longitude)
                row.createCell(6).setCellValue(transaction.latitude)
            }

            workbook.write(fileOut)
            workbook.close()
        }

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.fromFile(File(excelFilePath))
        val request = DownloadManager.Request(uri).apply {
            setTitle("Transaction Report")
            setDescription("Downloading transaction report")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, excelFileName)
        }
        downloadManager.enqueue(request)

        Toast.makeText(requireContext(), "Transaction report is being downloaded", Toast.LENGTH_SHORT).show()
    }
}
