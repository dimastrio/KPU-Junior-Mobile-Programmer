package id.dimas.kpu.database

import android.content.Context
import androidx.room.*
import id.dimas.kpu.Converters
import id.dimas.kpu.model.Voters
import id.dimas.kpu.model.VotersDao

@Database(entities = [Voters::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KPUDatabase : RoomDatabase() {

    abstract fun votersDao(): VotersDao

    companion object {

        private var INSTANCE: KPUDatabase? = null

        fun getInstance(context: Context): KPUDatabase? {
            if (INSTANCE == null) {
                synchronized(KPUDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        KPUDatabase::class.java, "KPU.db"
                    ).build()
                }
            }
            return INSTANCE
        }


        fun destroyInstance() {
            INSTANCE = null
        }
    }
}