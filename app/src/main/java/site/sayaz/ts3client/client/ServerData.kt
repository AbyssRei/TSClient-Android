package site.sayaz.ts3client.client

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.github.manevolent.ts3j.api.IconFile
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ServerData (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val hostname:String,
    val password:String,
    val nickname:String,

) : Parcelable

@Dao
interface ServerDataDao {
    @Query("SELECT * FROM ServerData")
    suspend fun getAll(): List<ServerData>

    @Query("SELECT * FROM ServerData WHERE id = :id")
    suspend fun getById(id: Int): ServerData

    @Query("SELECT * FROM ServerData WHERE hostname = :hostname")
    suspend fun getByHostname(hostname: String): ServerData

    @Query("SELECT * FROM ServerData WHERE nickname = :nickname")
    suspend fun getByNickname(nickname: String): ServerData

    @Query("SELECT * FROM ServerData WHERE password = :password")
    suspend fun getByPassword(password: String): ServerData

    @Query("DELETE FROM ServerData WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Insert
    suspend fun insert(serverData: ServerData)

}