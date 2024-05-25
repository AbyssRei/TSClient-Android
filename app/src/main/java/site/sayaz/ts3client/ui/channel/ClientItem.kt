package site.sayaz.ts3client.ui.channel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeOff
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.outlined.CancelPresentation
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MicNone
import androidx.compose.material.icons.outlined.MicOff
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.VolumeOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClientItem(clientData: ClientData) {Row{
    Spacer(modifier = Modifier.width(24.dp))
    ClientStateIcon(clientData.state)
    Text(
        text = clientData.nickname,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )


}}
@Composable
fun ClientStateIcon(state: ClientState){
    when (state) {
        ClientState.TALKING -> Icon(
            Icons.Outlined.Mic,
            contentDescription = "Talking",
        )

        ClientState.SILENT -> Icon(
            Icons.Outlined.MicNone,
            contentDescription = "Silent",
        )

        ClientState.MUTED -> Icon(
            Icons.Outlined.MicOff,
            contentDescription = "Muted",
        )

        ClientState.DEAFENED -> Icon(
            Icons.AutoMirrored.Outlined.VolumeOff,
            contentDescription = "Deafened",
        )

        ClientState.AWAY -> Icon(
            Icons.Outlined.CancelPresentation,
            contentDescription = "Away",
        )
        else -> Icon(
            Icons.Outlined.QuestionMark,
            contentDescription = "Silent"
        )
    }
}