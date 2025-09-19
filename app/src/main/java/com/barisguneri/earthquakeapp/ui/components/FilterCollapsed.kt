package com.barisguneri.earthquakeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.domain.model.SortOption
import com.barisguneri.earthquakeapp.domain.model.TimeRange
import com.barisguneri.earthquakeapp.ui.main.SharedContract
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiAction
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.fontSize
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun FilterCollapsed(
    modifier: Modifier = Modifier,
    backgroundColor: Color = colors.onBackground,
    onAction: (UiAction) -> Unit,
    filterState: FilterState,
    screen: BottomScreen = BottomScreen.HomeList
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MagnitudeContent(onAction = onAction, filterState = filterState)
        TimeRangeContent(onAction = onAction, filterState = filterState)
        if (screen !is BottomScreen.Map) {
            SortFilterContent(onAction = onAction, filterState = filterState)
        } else {
            SearchContent(onAction = onAction, filterState = filterState)
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = backgroundColor,
            thickness = 0.6.dp
        )
    }
}

@Composable
fun SearchContent(onAction: (UiAction) -> Unit, filterState: FilterState) {
    var searchText by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = filterState.searchQuery,
        onValueChange = {
            searchText = it
            onAction(UiAction.UpdateSearchQuery(searchText))

        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = colors.text,
            )
        },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.searchEarthquake),
                fontSize = fontSize.medium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                color = colors.text
            )
        },
        textStyle = TextStyle(
            color = colors.text,
            fontSize = fontSize.medium,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFontFamily
        ),
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = colors.lightGray.copy(
                alpha = 0.3f
            ),
            focusedContainerColor = colors.lightGray.copy(
                alpha = 0.3f
            ),
            focusedBorderColor = colors.lightGray.copy(
                alpha = 0.6f
            ),
            unfocusedBorderColor = colors.lightGray.copy(
                alpha = 0.6f
            ),
            cursorColor = colors.text,
        ),
    )
}

@Composable
fun SortFilterContent(onAction: (UiAction) -> Unit, filterState: FilterState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(padding.dimension8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var selectedOption by remember { mutableStateOf(SortOption.DATE) }
        Text(
            stringResource(R.string.sorted_by),
            fontFamily = poppinsFontFamily,
            fontSize = fontSize.medium,
            color = colors.text,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
        )

        SortOption.entries.forEach { option ->
            val isSelected = filterState.sortBy == option
            Button(
                onClick = {
                    selectedOption = option
                    onAction(UiAction.UpdateSortBy(option))
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) colors.primary.copy(alpha = 0.4f) else colors.lightGray.copy(
                        alpha = 0.3f
                    ),
                    contentColor = if (isSelected) colors.primaryBlue else colors.text
                )
            ) {
                Text(text = stringResource(option.title))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangeContent(onAction: (UiAction) -> Unit, filterState: FilterState) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(TimeRange.LAST_7_DAYS) }

    Text(
        stringResource(R.string.time_range),
        fontFamily = poppinsFontFamily,
        fontSize = fontSize.medium,
        color = colors.text,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = padding.dimension8)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .fillMaxWidth(),
            readOnly = true,
            value = stringResource(filterState.timeRange.titleResId),
            onValueChange = {},
            shape = CircleShape,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    fontSize = fontSize.medium,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = colors.text
                )
            },
            textStyle = TextStyle(
                color = colors.text,
                fontSize = fontSize.medium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily
            ),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedContainerColor = colors.lightGray.copy(
                    alpha = 0.3f
                ),
                focusedContainerColor = colors.lightGray.copy(
                    alpha = 0.3f
                ),
                focusedBorderColor = colors.lightGray.copy(
                    alpha = 0.6f
                ),
                unfocusedBorderColor = colors.lightGray.copy(
                    alpha = 0.6f
                ),
                focusedTrailingIconColor = colors.text,
                unfocusedTrailingIconColor = colors.text,
            ),

            )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            TimeRange.entries.forEach { selectionTime ->
                DropdownMenuItem(
                    text = { Text(stringResource(selectionTime.titleResId)) },
                    onClick = {
                        selectedOption = selectionTime
                        onAction(UiAction.UpdateTimeRange(selectedOption))
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun MagnitudeContent(onAction: (UiAction) -> Unit, filterState: FilterState) {
    Text(
        stringResource(R.string.magnitude),
        fontFamily = poppinsFontFamily,
        fontSize = fontSize.medium,
        color = colors.text,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )
    var sliderPosition by remember { mutableFloatStateOf(4f) }

    Column(modifier = Modifier.padding(horizontal = padding.dimension8)) {
        Slider(
            value = filterState.minMagnitude,
            onValueChange = {
                sliderPosition = it
                onAction(UiAction.UpdateMagnitude(sliderPosition))
            },
            valueRange = 0f..10f,
            steps = 4,
            colors = SliderDefaults.colors(
                thumbColor = colors.primary,
                activeTrackColor = colors.primary,
                inactiveTrackColor = colors.inactiveColor,
                activeTickColor = colors.white.copy(alpha = 0.5f),
                inactiveTickColor = Color.Transparent
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..10 step 2) {
                Text(text = i.toString(), color = Color.Gray)
            }
        }
    }
}

@Composable
@Preview
fun FilterCollapsedPreview() {
    AppTheme {
        FilterCollapsed(
            onAction = {},
            filterState = FilterState(),
            screen = BottomScreen.Map
        )
    }
}