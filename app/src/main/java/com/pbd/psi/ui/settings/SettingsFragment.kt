package com.pbd.psi.ui.settings

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pbd.psi.LoginActivity
import com.pbd.psi.databinding.FragmentSettingsBinding
import com.pbd.psi.room.TransactionEntity
import com.pbd.psi.ui.transaction.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var fileName: String
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
            val exportOptions = arrayOf("Export as XLSX", "Export as XLS")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Export File Type")
            builder.setItems(exportOptions) { _: DialogInterface?, which: Int ->
                when (which) {
                    0 ->  {
                        fileName = "transaction_data.xlsx"
                        launchFilePicker()
                    }
                    1 -> {
                        fileName = "transaction_data.xls"
                        launchFilePicker()
                    }
                }
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun launchFilePicker() {
        filePickerLauncher.launch(null)
    }

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        if (uri != null) {
            val documentFile = DocumentFile.fromTreeUri(requireContext(), uri)
            if (documentFile != null && documentFile.isDirectory) {
                viewModel.transactionList.observe(viewLifecycleOwner) { transItems ->
                    val transactions = requireNotNull(transItems) { "Transaction list is null" }
                    val transList = ArrayList(transactions)
                    Log.d("TransactionList", "TransactionList: $transList")
                    exportTransactionsToExcel(transList, documentFile, fileName)
                }
            } else {
                Toast.makeText(requireContext(), "Invalid directory", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun exportTransactionsToExcel(transactionEntities: List<TransactionEntity>, directory: DocumentFile, fileName: String) {
        if (transactionEntities.isNotEmpty()) {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Transaction Data")

            val headerCellStyle = workbook.createCellStyle()
            headerCellStyle.fillForegroundColor = IndexedColors.GREY_25_PERCENT.getIndex()
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            headerCellStyle.alignment = HorizontalAlignment.CENTER
            headerCellStyle.verticalAlignment = VerticalAlignment.CENTER
            val headerFont = workbook.createFont()
            headerFont.bold = true
            headerCellStyle.setFont(headerFont)

            val headers = arrayOf("Date", "Name", "Category", "Amount", "Location")
            val headerRow = sheet.createRow(0)
            headers.forEachIndexed { index, headerText ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(headerText)
                cell.cellStyle = headerCellStyle
            }

            transactionEntities.forEachIndexed { rowIndex, transaction ->
                val dataRow = sheet.createRow(rowIndex + 1)
                dataRow.createCell(0).setCellValue(transaction.date.toString())
                dataRow.createCell(1).setCellValue(transaction.name ?: "")
                dataRow.createCell(2).setCellValue(transaction.category.toString())
                dataRow.createCell(3).setCellValue(transaction.amount.toDouble())
                dataRow.createCell(4).setCellValue(transaction.location ?: "")
            }

            val file: DocumentFile
            if(fileName === "transaction_data.xls"){
                file = directory.createFile("application/vnd.ms-excel", fileName)!!
            }else{
                file = directory.createFile("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileName)!!
            }
            val outputStream = requireContext().contentResolver.openOutputStream(file!!.uri)
            workbook.write(outputStream)
            workbook.close()
            outputStream?.close()

            Toast.makeText(requireContext(), "Data Successfully Exported!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "No transaction data available", Toast.LENGTH_SHORT).show()
        }
    }
}
