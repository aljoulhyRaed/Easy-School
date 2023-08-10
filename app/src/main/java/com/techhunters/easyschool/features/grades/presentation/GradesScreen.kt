package com.techhunters.easyschool.features.grades.presentation

import ActionToolbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techhunters.easyschool.R
import com.techhunters.easyschool.core.ext.smallSpacer
import com.techhunters.easyschool.core.ext.toolbarActions
import com.techhunters.easyschool.core.ui.composable.ListCard

@Composable
fun GradesScreen(
    openScreen: (String) -> Unit,
    viewModel: GradeViewModel = hiltViewModel()
) {
    val grades = viewModel.gradesList.collectAsStateWithLifecycle(emptyList())
    var no = 1
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ActionToolbar(
            title = R.string.grades,
            modifier = Modifier.toolbarActions(),
            endActionIcon = R.drawable.ic_settings,
            endAction = { },
        )

        Spacer(modifier = Modifier.smallSpacer())

        if (grades.value.isNotEmpty()) LazyColumn(userScrollEnabled = true) {
            items(grades.value, key = { it.id }) { gradeItem ->
                 ListCard(
                     no = no++.toString() + "-",
                     title = gradeItem.name,
                     open = { viewModel.onGradeClick(
                         openScreen = openScreen,
                         gradeId = gradeItem.id,
                         subIds = gradeItem.subsIds,
                         segmentName = gradeItem.segmentName
                     ) },
                 )
            }
        }
       else Text(text = "لا توجد بيانات")
    }
}