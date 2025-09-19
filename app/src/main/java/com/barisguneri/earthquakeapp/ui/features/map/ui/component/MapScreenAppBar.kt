package com.barisguneri.earthquakeapp.ui.features.map.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.barisguneri.earthquakeapp.R
import com.barisguneri.earthquakeapp.domain.model.FilterState
import com.barisguneri.earthquakeapp.ui.components.FilterCollapsed
import com.barisguneri.earthquakeapp.ui.main.SharedContract.UiAction
import com.barisguneri.earthquakeapp.ui.navigation.BottomScreen
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.colors
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.dimens
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.fontSize
import com.barisguneri.earthquakeapp.ui.theme.AppTheme.padding
import com.barisguneri.earthquakeapp.ui.theme.poppinsFontFamily

@Composable
fun MapScreenAppBar(
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit,
    filterState: FilterState,
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.dimension8)
    ) {
        Surface(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(dimens.dp12))
                .blur(radius = 10.dp),
            color = colors.background.copy(alpha = 0.6f),
        ) {}
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding.dimension12, vertical = padding.dimension8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.map),
                    fontSize = fontSize.body,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily,
                    color = colors.text
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = colors.text,
                    )
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                FilterCollapsed(
                    onAction = onAction,
                    filterState = filterState,
                    screen = BottomScreen.Map
                )
            }
        }
    }
}