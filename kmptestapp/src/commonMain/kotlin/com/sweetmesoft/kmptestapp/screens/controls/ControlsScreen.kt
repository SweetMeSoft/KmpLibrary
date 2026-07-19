package com.sweetmesoft.kmptestapp.screens.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.controls.MoreControl
import com.sweetmesoft.kmpbase.objects.IconAction
import com.sweetmesoft.kmpcontrols.controls.ClickableOutlinedTextField
import com.sweetmesoft.kmpcontrols.controls.PasswordControl
import com.sweetmesoft.kmpcontrols.controls.SearchControl
import com.sweetmesoft.kmpcontrols.controls.TermsAndConditions
import com.sweetmesoft.kmpcontrols.controls.OtpControl
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Edit
import dev.seyfarth.tablericons.outlined.Trash

class ControlsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = remember { ControlsViewModel() }

        BaseScreen(
            title = "Controls Test"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Search Control", style = MaterialTheme.typography.titleMedium)
                    SearchControl(
                        value = viewModel.searchQuery,
                        onValueChange = { viewModel.searchQuery = it },
                        onSearch = { /* Handle search */ },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Password Control", style = MaterialTheme.typography.titleMedium)
                    PasswordControl(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("OTP / PIN Input Control", style = MaterialTheme.typography.titleMedium)
                    OtpControl(
                        value = viewModel.otpValue,
                        onValueChange = { viewModel.otpValue = it },
                        length = 6,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Biometric Authentication", style = MaterialTheme.typography.titleMedium)
                    Button(
                        onClick = { viewModel.testBiometrics() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Test Biometric Prompt")
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Clickable TextField", style = MaterialTheme.typography.titleMedium)
                    ClickableOutlinedTextField(
                        value = viewModel.clickableText,
                        title = "Tap to change",
                        color = MaterialTheme.colorScheme.primary,
                        onClick = {
                            viewModel.clickableText =
                                "Clicked at ${kotlin.random.Random.nextInt(100)}"
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("More Control", style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Item with options")
                        MoreControl(
                            options = listOf(
                                IconAction(TablerIcons.Outlined.Edit, "Edit") {},
                                IconAction(TablerIcons.Outlined.Trash, "Delete") {})
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Terms & Conditions", style = MaterialTheme.typography.titleMedium)
                    TermsAndConditions(
                        urlPrivacy = "https://example.com/privacy",
                        urlTerms = "https://example.com/terms"
                    )
                }
            }
        }
    }
}
