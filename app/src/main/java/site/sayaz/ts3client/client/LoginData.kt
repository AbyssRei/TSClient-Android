package site.sayaz.ts3client.client

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class LoginData (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val hostname:String,
    val password:String,
    val nickname:String
)

@Dao
interface LoginDataDao {
    @Query("SELECT * FROM LoginData")
    suspend fun getAll(): List<LoginData>

    @Query("SELECT * FROM LoginData WHERE id = :id")
    suspend fun getById(id: Int): LoginData

    @Query("SELECT * FROM LoginData WHERE hostname = :hostname")
    suspend fun getByHostname(hostname: String): LoginData

    @Query("SELECT * FROM LoginData WHERE nickname = :nickname")
    suspend fun getByNickname(nickname: String): LoginData

    @Query("SELECT * FROM LoginData WHERE password = :password")
    suspend fun getByPassword(password: String): LoginData

    @Query("DELETE FROM LoginData WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Insert
    suspend fun insert(loginData: LoginData)

}