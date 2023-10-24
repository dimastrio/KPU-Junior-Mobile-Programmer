package id.dimas.kpu.model

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "voters_table")
data class Voters(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "nik") var nik: String,
    @ColumnInfo(name = "nama") var nama: String,
    @ColumnInfo(name = "noHp") var noHp: String,
    @ColumnInfo(name = "jenisKelamin") var jenisKelamin: String,
    @ColumnInfo(name = "tglPendataan") var tglPendataan: String,
    @ColumnInfo(name = "lokasi") var location: String,
    @ColumnInfo(name = "gambar") var img: Bitmap
)
