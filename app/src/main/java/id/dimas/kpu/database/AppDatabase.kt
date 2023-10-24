package id.dimas.kpu.database

import android.content.Context
import androidx.room.*
import id.dimas.kpu.Converters
import id.dimas.kpu.model.Voters
import id.dimas.kpu.model.VotersDao

@Database(entities = [Voters::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun votersDao(): VotersDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "KPU.db"
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