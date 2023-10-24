package id.dimas.kpu

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.kpu.model.Voters
import id.dimas.kpu.repo.VotersRepo
import kotlinx.coroutines.launch

class AddViewModel(private val votersRepo: VotersRepo) : ViewModel() {

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun saveVotersToDb(
        nik: String,
        nama: String,
        noHp: String,
        jenkel: String,
        tanggal: String,
        lokasi: String,
        gambar: Bitmap
    ) {
        val voters = Voters(null, nik, nama, noHp, jenkel, tanggal, lokasi, gambar)
        viewModelScope.launch {
            val checkNik = votersRepo.checkNik(nik)
            if (checkNik == null) {
                val result = votersRepo.insertVoters(voters)
                if (result != 0L) {
                    _successMessage.value = "Berhasil Menambahkan"
                }
            } else {
                _errorMessage.value = "Pemilih Sudah Didata"
            }

        }
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
    }
}