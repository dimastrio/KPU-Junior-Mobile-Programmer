package id.dimas.kpu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.kpu.model.Voters
import id.dimas.kpu.repo.VotersRepo
import kotlinx.coroutines.launch

class ListViewModel(private val votersRepo: VotersRepo) : ViewModel() {

    private val _voters = MutableLiveData<List<Voters>>()
    val voters: LiveData<List<Voters>> get() = _voters

    private val _votersNIK = MutableLiveData<Voters>()
    val votersNIK: LiveData<Voters> get() = _votersNIK


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getAllVoter() {
        viewModelScope.launch {
            val data = votersRepo.getAllVoter()
            _voters.value = data
        }
    }

    fun getVoterBynik(nik: String) {
        viewModelScope.launch {
            val voters = votersRepo.getVoterByNik(nik)
            _votersNIK.value = voters
        }
    }

}