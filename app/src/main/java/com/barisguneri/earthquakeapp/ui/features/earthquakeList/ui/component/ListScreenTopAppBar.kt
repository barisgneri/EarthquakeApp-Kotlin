package com.barisguneri.earthquakeapp.ui.features.earthquakeList.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.ui.components.FilterCollapsed
import com.barisguneri.earthquakeapp.ui.theme.AppTheme
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.fontSize
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiAction

@Composable
fun ListScreenTopAppBar(
    modifier: Modifier = Modifier, onAction: (UiAction) -> Unit, filterState: FilterState,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }

    Column(modifier.fillMaxWidth()) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                fontSize = fontSize.large,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                color = colors.text
            )
            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Search",
                    tint = colors.text,
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(vertical = padding.dimension12)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(padding.dimension4)
        ) {
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
                modifier = Modifier
                    .padding(
                        vertical = padding.dimension2,
                        horizontal = padding.dimension12
                    )
                    .weight(1f),
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
            Button(
                modifier = Modifier
                    .padding(end = padding.dimension12)
                    .fillMaxHeight(),
                onClick = {
                    isExpanded = !isExpanded
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.lightGray.copy(alpha = 0.3f)
                ),
            ) {

                Icon(
                    modifier = Modifier.size(height = dimens.dp40, width = dimens.dp20),
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Search",
                    tint = colors.text,
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.filters),
                    fontFamily = poppinsFontFamily,
                    fontSize = fontSize.medium,
                    color = colors.text,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            FilterCollapsed(
                modifier = Modifier,
                backgroundColor = colors.onBackground,
                onAction = onAction,
                filterState = filterState
            )
        }
    }

}


@Composable
@Preview
fun ListScreenTopAppBarPreview() {
    AppTheme {
        ListScreenTopAppBar(
            onAction = {},
            filterState = FilterState()
        )
    }
}