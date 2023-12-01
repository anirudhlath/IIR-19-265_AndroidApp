package com.example.joriebutlerprojectnative.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.joriebutlerprojectnative.R

@Composable
fun Survey(
  title: String,
  instructions: String,
  questions: Array<String>,
  responses: Array<String>
) {

  LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
    item {
      Column {
        Text(
          text = title,
          textAlign = TextAlign.Center,
          fontFamily = FontFamily.SansSerif,
          fontSize = 60.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = instructions,
          textAlign = TextAlign.Center,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 50.dp, end = 50.dp),
        )
      }
    }
    items(questions) { QuestionCard(questions.indexOf(it), it) }
  }
}

@Preview(
  showBackground = true,
  device = "spec:id=reference_tablet,shape=Normal,width=1340,height=800,unit=dp,dpi=179"
)
@Composable
fun SurveyPreview() {
  val responses = stringArrayResource(id = R.array.LonelinessScale_responses_array)
  val questions = stringArrayResource(id = R.array.LonelinessScale_responses_array)
  MaterialTheme {
    Survey(
      "Loneliness Scale",
      "This scale measures the degree to which an individual feels lonely.",
      questions,
      responses
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(questionNumber: Int, question: String) {
  val selectedOption = remember { mutableStateOf("") }
  var expanded by remember { mutableStateOf(false) }

  ElevatedCard(
    modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = "Question ${questionNumber + 1}", style = MaterialTheme.typography.titleLarge)

      Text(
        text = question,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 10.dp)
      )

      Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp)) {
        ExposedDropdownMenuBox(
          expanded = expanded,
          onExpandedChange = { expanded = !expanded },
          modifier = Modifier.fillMaxWidth()
        ) {
          TextField(
            value = selectedOption.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tap here to select your response") },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
          )
          DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
          ) {
            val responses = stringArrayResource(id = R.array.LonelinessScale_responses_array)
            responses.forEach {
              DropdownMenuItem(
                text = { Text(it) },
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                  selectedOption.value = it
                  expanded = false
                }
              )
            }
          }
        }
      }
    }
  }
}
