package site.sayaz.ts3client.client

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.github.manevolent.ts3j.identity.Identity
import com.github.manevolent.ts3j.identity.LocalIdentity

@Entity
data class IdentityData (
    @PrimaryKey(autoGenerate = true) val id : Int,
    val identity : ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IdentityData

        if (id != other.id) return false
        if (!identity.contentEquals(other.identity)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + identity.contentHashCode()
        return result
    }
}

@Dao
interface IdentityDataDao {
    @Insert
    fun insert(identityByteArray: IdentityData)

    @Query("SELECT identity FROM IdentityData WHERE id = 1")
    suspend fun getIdentity(): ByteArray?

}