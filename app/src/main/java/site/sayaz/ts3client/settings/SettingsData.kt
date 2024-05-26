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
    //def,en,zh
    val language : String = "zh",
    // darkMode:system dark light
    val appearance : String = "system",
    // def,dynamic,apple
    val theme : String = "def"
) : Parcelable

@Dao
interface SettingsDataDao {
    @Query("SELECT * FROM settingsdata")
    suspend fun getSettingsData() : SettingsData

    @Update
    suspend fun setSettingsData(settingsData: SettingsData)

}