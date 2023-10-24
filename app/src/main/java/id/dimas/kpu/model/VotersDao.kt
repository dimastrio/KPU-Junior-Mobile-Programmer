package id.dimas.kpu.model

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface VotersDao {

    @Query("SELECT * FROM voters_table")
    fun getAllVoters(): List<Voters>


    @Query("SELECT nik FROM voters_table WHERE nik = :nik")
    fun checkVoters(nik: String): String

    @Query("SELECT * FROM voters_table WHERE nik = :nik")
    fun getVoter(nik: String): Voters

    @Insert(onConflict = REPLACE)
    fun insertVoters(voters: Voters): Long

    @Update
    fun updateUser(voters: Voters): Int

    @Delete
    fun deleteUser(voters: Voters): Int


}