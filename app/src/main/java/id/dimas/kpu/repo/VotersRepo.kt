package id.dimas.kpu.repo

import android.content.Context
import id.dimas.kpu.database.AppDatabase
import id.dimas.kpu.database.KPUDatabase
import id.dimas.kpu.model.Voters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VotersRepo(context: Context) {

    private val mDb = AppDatabase.getInstance(context)

    suspend fun insertVoters(voters: Voters) = withContext(Dispatchers.IO) {
        mDb?.votersDao()?.insertVoters(voters)
    }

    suspend fun checkNik(nik: String) = withContext(Dispatchers.IO) {
        mDb?.votersDao()?.checkVoters(nik)
    }

    suspend fun getAllVoter() = withContext(Dispatchers.IO) {
        mDb?.votersDao()?.getAllVoters()
    }

    suspend fun getVoterByNik(nik: String) = withContext(Dispatchers.IO) {
        mDb?.votersDao()?.getVoter(nik)
    }

}