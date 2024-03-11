package com.towich.vktest.ui.screen.main.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.towich.vktest.R
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.ui.util.shimmerEffect
import java.util.Locale

@Composable
fun CategoriesLazyRow(
    listOfCategories: List<String>,
    isLoading: Boolean,
    selectedChipIndex: Int,
    modifier: Modifier = Modifier,
    onSelectChip: (category: String, index: Int) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        if (isLoading) {
            items(count = 10) {
                CategoryChipWithShimmerEffect(modifier = Modifier.padding(horizontal = 5.dp))
            }
        } else {
            item {
                CategoryChip(
                    title = stringResource(id = R.string.chip_all),
                    isSelected = selectedChipIndex == 0
                ) {
                    onSelectChip("All", 0)
                }
            }
            itemsIndexed(
                items = listOfCategories
            ) { index: Int, item: String ->
                CategoryChip(title = item, isSelected = selectedChipIndex == index + 1) {
                    onSelectChip(item, index + 1)
                }
            }
        }
    }
}


@Composable
fun CategoryChip(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors =
                    if (isSelected)
                        listOf(Color.Yellow, Color.Green)
                    else
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface
                        ),
                    startY = 0f
                )
            )
            .height(50.dp)
            .clickable {
                onSelect()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                .replace('-', ' '),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CategoryChipWithShimmerEffect(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
//            .background(color = MaterialTheme.colorScheme.surface)
            .height(50.dp)
            .width(100.dp)
            .shimmerEffect()
    )
}