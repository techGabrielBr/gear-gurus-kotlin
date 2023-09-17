package br.com.fiap.geargurus.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.fiap.geargurus.MainActivity
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.ActivityUnlockBinding
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import java.util.Date

class UnlockActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: ActivityUnlockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnlockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = binding.root
        val goNextBtn = root.findViewById<TextView>(R.id.go_next)
        val goBackBtn = root.findViewById<TextView>(R.id.go_back)


        goBackBtn.setOnClickListener {
            val i = Intent(this@UnlockActivity, MainActivity::class.java)
            startActivity(i)
        }

        goNextBtn.setOnClickListener {
            val i = Intent(this@UnlockActivity, MainActivity::class.java)
            i.putExtra("ShowModal", "true")
            startActivity(i)
        }

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val sharedPref = this.getSharedPreferences("initDate", Context.MODE_PRIVATE)
                with(sharedPref.edit()){
                    putLong("date", System.currentTimeMillis())
                    apply()
                }

                val i = Intent(this@UnlockActivity, MainActivity::class.java)
                i.putExtra("ShowModal", "true")
                startActivity(i)
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                if (this?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.CAMERA
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
                    } else {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
                    }
                } else {
                    Log.i("QRCode", "Permissões garantidas")
                    Toast.makeText(
                        this, "Erro ao iniciar a câmera",
                        Toast.LENGTH_LONG
                    ).show()
                    val i = Intent(this@UnlockActivity, MainActivity::class.java)
                    startActivity(i)
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0] != -1){
            return;
        }else{
            Toast.makeText(this, "Você deve permitir o acesso a sua camera para ler o QrCode", Toast.LENGTH_SHORT).show()
            val i = Intent(this@UnlockActivity, MainActivity::class.java)
            startActivity(i)

        }
    }
}