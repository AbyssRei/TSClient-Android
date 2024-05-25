package site.sayaz.ts3client.ui.channel

import android.os.Parcelable
import com.github.manevolent.ts3j.api.Channel
import kotlinx.parcelize.Parcelize

/**
 * Channel in UI
 * @property id: channel id
 * @property order: previous channel order, set 0 on header
 * @property name: channel name
 * @property parent: parent channel id
 * @property pw: channel password
 */
@Parcelize
data class ChannelData(
    val id: Int,
    val order: Int,
    val parent: Int,
    val name: String,
    val pw: String = ""
):Parcelable

fun Channel.toData() = ChannelData(id, order, parentChannelId, name)
