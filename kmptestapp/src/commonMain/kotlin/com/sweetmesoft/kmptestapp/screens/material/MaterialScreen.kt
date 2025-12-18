package com.sweetmesoft.kmptestapp.screens.material

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.Check
import compose.icons.tablericons.Plus
import compose.icons.tablericons.Settings
import compose.icons.tablericons.Star
import compose.icons.tablericons.User

class MaterialScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        remember { MaterialViewModel() }
        BaseScreen(
            title = "Default M3 Components",
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Buttons
                Section("Buttons") {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {}) { Text("Button") }
                            ElevatedButton(onClick = {}) { Text("Elevated") }
                            FilledTonalButton(onClick = {}) { Text("Tonal") }
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = {}) { Text("Outlined") }
                            TextButton(onClick = {}) { Text("Text") }
                        }
                    }
                }

                // FABs
                Section("Floating Action Buttons") {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SmallFloatingActionButton(onClick = {}) {
                            Icon(TablerIcons.Plus, "Small FAB")
                        }
                        FloatingActionButton(onClick = {}) {
                            Icon(TablerIcons.Plus, "FAB")
                        }
                        LargeFloatingActionButton(onClick = {}) {
                            Icon(TablerIcons.Plus, "Large FAB")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    ExtendedFloatingActionButton(
                        onClick = {},
                        icon = { Icon(TablerIcons.Plus, "Extended FAB") },
                        text = { Text("Extended FAB") })
                }

                // Cards
                Section("Cards") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Card(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                            Text("Filled Card", modifier = Modifier.padding(16.dp))
                        }
                        ElevatedCard(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                            Text("Elevated Card", modifier = Modifier.padding(16.dp))
                        }
                        OutlinedCard(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                            Text("Outlined Card", modifier = Modifier.padding(16.dp))
                        }
                    }
                }

                // Text Inputs
                Section("Text Inputs") {
                    var text by remember { mutableStateOf("") }
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("TextField") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("OutlinedTextField") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Selection Controls
                Section("Selection Controls") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        var checked by remember { mutableStateOf(true) }
                        Checkbox(checked = checked, onCheckedChange = { checked = it })
                        Text("Checkbox")

                        Spacer(Modifier.width(16.dp))

                        var switched by remember { mutableStateOf(true) }
                        Switch(checked = switched, onCheckedChange = { switched = it })
                        Text("Switch")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        var selected by remember { mutableStateOf(true) }
                        RadioButton(selected = selected, onClick = { selected = true })
                        Text("Radio 1")
                        Spacer(Modifier.width(8.dp))
                        RadioButton(selected = !selected, onClick = { selected = false })
                        Text("Radio 2")
                    }
                }

                // Chips
                Section("Chips") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(
                            onClick = {},
                            label = { Text("Assist") },
                            leadingIcon = { Icon(TablerIcons.Settings, null) })
                        FilterChip(
                            selected = true,
                            onClick = {},
                            label = { Text("Filter") },
                            leadingIcon = { Icon(TablerIcons.Check, null) })
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InputChip(
                            selected = false,
                            onClick = {},
                            label = { Text("Input") },
                            avatar = { Icon(TablerIcons.User, null) })
                        SuggestionChip(onClick = {}, label = { Text("Suggestion") })
                    }
                }

                // Progress Indicators
                Section("Progress Indicators") {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                        CircularProgressIndicator(progress = { 0.7f })
                    }
                }

                // Sliders
                Section("Sliders") {
                    var sliderPosition by remember { mutableStateOf(0.5f) }
                    Slider(
                        value = sliderPosition, onValueChange = { sliderPosition = it })

                    var rangeSliderPosition by remember { mutableStateOf(0.2f..0.8f) }
                    RangeSlider(
                        value = rangeSliderPosition, onValueChange = { rangeSliderPosition = it })
                }

                // Navigation (Visual only)
                Section("Navigation Examples") {
                    NavigationBar {
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            icon = { Icon(TablerIcons.Star, null) },
                            label = { Text("Item 1") })
                        NavigationBarItem(
                            selected = false,
                            onClick = {},
                            icon = { Icon(TablerIcons.User, null) },
                            label = { Text("Item 2") })
                    }

                    Spacer(Modifier.height(8.dp))

                    var selectedTab by remember { mutableStateOf(0) }
                    TabRow(selectedTabIndex = selectedTab) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Tab 1") })
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Tab 2") })
                    }
                }

                // Badges
                Section("Badges") {
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        BadgedBox(badge = { Badge { Text("8") } }) {
                            Icon(TablerIcons.Star, contentDescription = "Favorite")
                        }

                        BadgedBox(badge = { Badge() }) {
                            Icon(TablerIcons.Settings, contentDescription = "Settings")
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Section(title: String, content: @Composable () -> Unit) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                title, style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider()
            content()
        }
    }
}
