package site.sayaz.ts3client.settings

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class SettingsData (
    @PrimaryKey(autoGenerate = true) val id : Int = 1,
    val preventSleepDuringConnection : Boolean = false,
) : Parcelable

@Dao
interface SettingsDataDao {
    @Query("SELECT * FROM settingsdata")
    fun getSettingsData() : SettingsData

    @Update
    fun setSettingsData(settingsData: SettingsData)
}