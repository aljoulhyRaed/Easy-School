package com.techhunters.easyschool.features.subjects.presentation

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techhunters.easyschool.R
import com.techhunters.easyschool.core.ext.smallSpacer
import com.techhunters.easyschool.core.ext.toolbarActions
import com.techhunters.easyschool.core.ui.composable.ListCard
import kotlinx.coroutines.delay

@Composable
fun SubjectsScreens(
    open: (String) -> Unit,
    gradeId: String,
    segmentName: String,
    subIds: List<String>,
    viewModel: SubjectsViewModel = hiltViewModel()
) {
    LaunchedEffect(true){
        viewModel.initialize(subIds)
        delay(3000)
    }

    val subjects = viewModel.getSubjects()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ActionToolbar(
            title = R.string.subjects,
            modifier = Modifier.toolbarActions(),
            endActionIcon = R.drawable.ic_settings,
            endAction = { },
        )

        Spacer(modifier = Modifier.smallSpacer())

        if (subjects.isNotEmpty()) LazyColumn {
            items(subjects, key = { it!!.id }) { subjectItem ->
                ListCard(
                    no = subjectItem!!.id,
                    title = subjectItem.name,
                    open = {
                        viewModel.onSubjectClick(
                            openScreen = open,
                            subId = subjectItem.id,
                            gradeId = gradeId,
                            segmentName = segmentName
                        )
                    })
            }
        }else Text(text = "لا توجد بيانات")
    }

}