package com.towich.vktest.ui.screen.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.towich.vktest.R

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
            itemsIndexed(items = listOfCategories) { index: Int, item: String ->
                CategoryChip(
                    title = item,
                    isSelected = selectedChipIndex == index + 1
                ) {
                    onSelectChip(item, index + 1)
                }
            }
        }
    }
}