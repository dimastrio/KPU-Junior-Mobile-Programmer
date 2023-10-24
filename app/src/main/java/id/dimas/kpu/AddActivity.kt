package id.dimas.kpu

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import id.dimas.kpu.Extension.loadImage
import id.dimas.kpu.databinding.ActivityAddBinding
import id.dimas.kpu.repo.VotersRepo
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

//    private var mdb: KPUDatabase? = null

    private lateinit var bmap: Bitmap

    private var gender: String = ""
    private var address: String? = ""

    private val votersRepo: VotersRepo by lazy { VotersRepo(this@AddActivity) }

    private val viewModel: AddViewModel by lazy { AddViewModel(votersRepo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        db = Room.databaseBuilder(applicationContext, KPUDatabase::class.java, "kpu.db").build()
//        mdb = KPUDatabase.getInstance(applicationContext)

        pickDate()
        addVoters()
        observeAddVoters()
//        requestLocationPermission()


        binding.btnCheckLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 76)
        }

        binding.ivImage.setOnClickListener {
            checkCameraPermission()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.etLocation.setText(address)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 76) {
            if (resultCode === RESULT_OK) {
                address = data!!.getStringExtra("address")
            }
        }
    }

    private fun observeAddVoters() {
        viewModel.successMessage.observe(this) {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
        }
    }


    private fun addVoters() {
        binding.apply {
            btnAdd.setOnClickListener {

                gender = if (rbMale.isChecked) {
                    "Laki-laki"
                } else if (rbFemale.isChecked) {
                    "Perempuan"
                } else {
                    Toast.makeText(this@AddActivity, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val nik = etNik.text.toString()
                val nama = etName.text.toString()
                val noHp = etPhoneNumber.text.toString()
                val jenkel = gender
                val tanggal = etDate.text.toString()
                val lokasi = etLocation.text.toString()
                val gambar = bmap

                viewModel.saveVotersToDb(nik, nama, noHp, jenkel, tanggal, lokasi, gambar)

            }
        }
    }


//    private fun saveVotersToDb(
//        nik: String,
//        nama: String,
//        noHp: String,
//        jenkel: String,
//        tanggal: String,
//        lokasi: String,
//        gambar: Bitmap
//    ) {
//        val voters = Voters(null,nik, nama, noHp, jenkel, tanggal, lokasi, gambar)
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = mdb?.votersDao()?.insertVoters(voters)
//            if (result != 0L) {
//                onBackPressed()
//            }
//        }
//        CoroutineScope(Dispatchers.IO).launch {
////            val emails = mDb?.votersDao()?.checkEmailUser(email)
////            if (emails == null) {
//
//            if (result != 0L) {
//                CoroutineScope(Dispatchers.Main).launch {
//                    onBackPressed()
//                }
//            }
//        }
//        else {
//                CoroutineScope(Dispatchers.Main).launch {
//                    showToast("Email Sudah Terdaftar")
//                }
//            }
//    }


    private fun pickDate() {

        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.etDate.setText(sdf.format(cal.time))
            }
        binding.etDate.isClickable = true
        binding.etDate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    private val launcherCamera =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                try {
                    val bitmap = result.data!!.extras?.get("data") as Bitmap
                    bmap = bitmap
//                    val file = bitmapToFile(bitmap, requireContext())
                    binding.ivImage.loadImage(bitmap)
                    Toast.makeText(this, "$bitmap", Toast.LENGTH_SHORT).show()

//                    bottomSheetCallback?.onSelectImage(bitmap, file)
                } catch (e: Exception) {
//                    Helper.showToast(requireContext(), e.message.toString())
                }
            }
        }


    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcherCamera.launch(intent)
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1){
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                launcherCamera.launch()
//            }else {
//                Toast.makeText(this, "Error",Toast.LENGTH_SHORT)
//            }
//        }
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }
//
//    @AfterPermissionGranted(REQUEST_CODE_PERMISSION)
//    private fun requestCameraPermission() {
//        val perms = arrayOf(Manifest.permission.CAMERA)
//        if (EasyPermissions.hasPermissions(this, *perms)) {
//            openCamera()
//        } else {
//            EasyPermissions.requestPermissions(
//                this,
//                "Please grant the location permission",
//                REQUEST_CODE_PERMISSION,
//                *perms
//            )
//        }
//    }
//
//    private fun openCamera(){
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(cameraIntent, REQUEST_CODE)
//    }
//
//    private val launcherCamera =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Log.i("Permission: ", "Granted")
//            } else {
//                Log.i("Permission: ", "Denied")
//            }
//        }

//    private fun checkPermissionCamera() {
//        if (ContextCompat.checkSelfPermission(
//                this@AddActivity,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) !==
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    this@AddActivity,
//                    Manifest.permission.CAMERA
//                )
//            ) {
//                ActivityCompat.requestPermissions(
//                    this@AddActivity,
//                    arrayOf(Manifest.permission.CAMERA), 1
//                )
//            } else {
//                ActivityCompat.requestPermissions(
//                    this@AddActivity,
//                    arrayOf(Manifest.permission.CAMERA), 1
//                )
//            }
//        }
//
//
//    }

//    private fun checkPermission() {
//        if (checkPermissionsIsGranted(
//                this,
//                Manifest.permission.CAMERA,
//                arrayOf(
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ),
//                REQUEST_CODE_PERMISSION
//            )
//        ){
//            launcherCamera.launch()
//        }
//    }

//    private fun checkPermissionsIsGranted(
//        activity: Activity,
//        permission: String,
//        permissions: Array<String>,
//        requestCode: Int
//    ): Boolean {
//        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
//        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//                showPermissionDeniedDialog()
//            } else {
//                ActivityCompat.requestPermissions(activity, permissions, requestCode)
//            }
//            false
//        } else {
//            true
//        }
//    }
//
//    private fun showPermissionDeniedDialog() {
//        AlertDialog.Builder(this)
//            .setTitle("Permission Denied")
//            .setMessage("Permission is denied, please allow permissions from App Settings.")
//            .setPositiveButton("Pengaturan") { _, _ -> openAppSettings() }
//            .setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
//            .show()
//    }
//
//    private fun openAppSettings() {
//        val intent = Intent()
//        val uri = Uri.fromParts("package", this.packageName, null)
//
//        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//        intent.data = uri
//
//        startActivity(intent)
//    }


    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val REQUEST_CODE_PERMISSION = 100
        private const val REQUEST_CODE = 200
    }

}