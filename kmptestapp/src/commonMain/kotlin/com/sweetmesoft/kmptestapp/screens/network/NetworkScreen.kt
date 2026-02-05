package com.sweetmesoft.kmptestapp.screens.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.objects.IconAction
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Trash

class NetworkScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = remember { NetworkViewModel() }

        BaseScreen(
            title = "Network Utils", showTop = true, iconActions = listOf(
                IconAction(TablerIcons.Outlined.Trash, "Clear Logs") { viewModel.clearLogs() })
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Methods Test", style = MaterialTheme.typography.titleMedium
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.testGet() }, modifier = Modifier.weight(1f)
                        ) { Text("GET") }

                        Button(
                            onClick = { viewModel.testPost() }, modifier = Modifier.weight(1f)
                        ) { Text("POST") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.testPut() }, modifier = Modifier.weight(1f)
                        ) { Text("PUT") }

                        Button(
                            onClick = { viewModel.testPatch() }, modifier = Modifier.weight(1f)
                        ) { Text("PATCH") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.testDelete() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) { Text("DELETE") }

                        Button(
                            onClick = { viewModel.testPostFormUrlEncoded() },
                            modifier = Modifier.weight(1f)
                        ) { Text("POST (Form)") }
                    }
                }

                Text(
                    "Logs", style = MaterialTheme.typography.titleMedium
                )

                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f).background(
                        MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium
                    ).padding(8.dp).verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = viewModel.logs.ifEmpty { "Ready to test..." },
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
