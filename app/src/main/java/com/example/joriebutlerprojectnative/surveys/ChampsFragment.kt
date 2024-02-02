/*
 *     Created by Anirudh Lath in 2021
 *     anirudhlath@gmail.com
 *     Last modified 11/30/23, 6:38 PM
 *     Copyright (c) 2023.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative.surveys

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.joriebutlerprojectnative.R
import com.example.joriebutlerprojectnative.patient.PatientSurveysFragment
import com.example.joriebutlerprojectnative.ui.theme.AppTheme
import com.google.android.material.snackbar.Snackbar

class ChampsFragment : Fragment() {

  @RequiresApi(Build.VERSION_CODES.R)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

      setContent {
        AppTheme {
          val questions = stringArrayResource(id = R.array.CHAMPS_questions_array)
          val timeResponses = stringArrayResource(id = R.array.CHAMPS_time_responses_array)
          val hourResponses = stringArrayResource(id = R.array.CHAMPS_hour_responses_array)
          val results = MutableList(questions.size) { ChampsResponse() }

          ChampsSurvey(
            "CHAMPS Activities",
            "This questionnaire is about activities that you may have done in the past 4 weeks. In a typical week during the past 4 weeks, did you…",
            questions,
            timeResponses,
            hourResponses,
            results,
          )
        }
      }
    }
  }

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    return if (enter) {
      AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_bottom)
    } else {
      AnimationUtils.loadAnimation(activity, R.anim.fade_out)
    }
  }

  private fun submitSurvey(results: MutableList<ChampsResponse>) {
    val sharedPref =
      requireActivity().getSharedPreferences(getString(R.string.patientData), Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    results.forEachIndexed { index, response ->
      val number = index + 1
      if (response.isCompleted()) {
        editor.putString("CHAMPS_q$number.1", response.times)
        editor.putString("CHAMPS_q$number.2", response.hours)
      } else {
        val contextView = requireView()
        Snackbar.make(
            contextView,
            "Please complete all the fields. Looks like you missed question $number.",
            Snackbar.LENGTH_SHORT
          )
          .show()
        return
      }
    }
    editor.putInt("CHAMPSCompleted", 1)

    editor.apply()
    parentFragmentManager
      .beginTransaction()
      .replace(R.id.constraintLayout, PatientSurveysFragment())
      .commit()
    return
  }

  @Composable
  fun ChampsSurvey(
    title: String,
    instructions: String,
    questions: Array<String>,
    timeResponses: Array<String>,
    hourResponses: Array<String>,
    results: MutableList<ChampsResponse>,
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
      items(questions) {
        ChampsQuestionCard(questions.indexOf(it), it, timeResponses, hourResponses, results)
      }
      item {
        Button(
          modifier =
            Modifier.padding(top = 26.dp, start = 24.dp, end = 24.dp, bottom = 50.dp)
              .fillMaxWidth(),
          onClick = { submitSurvey(results) }
        ) {
          Text(text = "Finish")
        }
      }
    }
  }

  @Preview(
    showBackground = true,
    device = "spec:id=reference_tablet,shape=Normal,width=1340,height=800,unit=dp,dpi=179"
  )
  @Composable
  fun SurveyPreview() {
    AppTheme {
      val questions = stringArrayResource(id = R.array.CHAMPS_questions_array)
      val timeResponses = stringArrayResource(id = R.array.CHAMPS_time_responses_array)
      val hourResponses = stringArrayResource(id = R.array.CHAMPS_hour_responses_array)
      val results = MutableList(questions.size) { ChampsResponse() }
      Surface {
        ChampsSurvey(
          "CHAMPS Activities",
          "This questionnaire is about activities that you may have done in the past 4 weeks. In a typical week during the past 4 weeks, did you…",
          questions,
          timeResponses,
          hourResponses,
          results,
        )
      }
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun ChampsQuestionCard(
    questionNumber: Int,
    question: String,
    timeResponses: Array<String>,
    hourResponses: Array<String>,
    results: MutableList<ChampsResponse>
  ) {
    val response = remember { results[questionNumber] }
    var state by remember { mutableStateOf(response.state) }
    val selectedOption1 = remember { mutableStateOf(response.times) }
    val selectedOption2 = remember { mutableStateOf(response.hours) }
    var timesExpanded by remember { mutableStateOf(false) }
    var hoursExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
      modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Question ${questionNumber + 1}", style = MaterialTheme.typography.titleLarge)

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(top = 10.dp)
        ) {
          Text(
            text = question,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f).padding(end = 10.dp)
          )
          Row(
            Modifier.selectableGroup().padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
          ) {
            RadioButton(
              selected = state,
              onClick = {
                state = true
                response.state = true
              }
            )
            Text(text = "Yes")
            RadioButton(
              modifier = Modifier.padding(start = 10.dp),
              selected = !state,
              onClick = {
                state = false
                response.state = false
              }
            )
            Text(text = "No")
          }
        }

        if (state) {
          OutlinedCard(
            modifier = Modifier.padding(top = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(text = "How many TIMES a week?")
              Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp)) {
                ExposedDropdownMenuBox(
                  expanded = timesExpanded,
                  onExpandedChange = { timesExpanded = !timesExpanded },
                  modifier = Modifier.fillMaxWidth()
                ) {
                  TextField(
                    value = selectedOption1.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tap here to select your response") },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                      ExposedDropdownMenuDefaults.TrailingIcon(expanded = timesExpanded)
                    }
                  )
                  DropdownMenu(
                    expanded = timesExpanded,
                    onDismissRequest = { timesExpanded = false },
                    modifier = Modifier.exposedDropdownSize()
                  ) {
                    timeResponses.forEach {
                      DropdownMenuItem(
                        text = { Text(it) },
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                          selectedOption1.value = it
                          timesExpanded = false
                          response.times = it
                        }
                      )
                    }
                  }
                }
              }
            }
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
              Text(text = "How many TOTAL hours a week did you usually do it?")
              Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp)) {
                ExposedDropdownMenuBox(
                  expanded = hoursExpanded,
                  onExpandedChange = { hoursExpanded = !hoursExpanded },
                  modifier = Modifier.fillMaxWidth()
                ) {
                  TextField(
                    value = selectedOption2.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tap here to select your response") },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                      ExposedDropdownMenuDefaults.TrailingIcon(expanded = hoursExpanded)
                    }
                  )
                  DropdownMenu(
                    expanded = hoursExpanded,
                    onDismissRequest = { hoursExpanded = false },
                    modifier = Modifier.exposedDropdownSize()
                  ) {
                    hourResponses.forEach {
                      DropdownMenuItem(
                        text = { Text(it) },
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                          selectedOption2.value = it
                          hoursExpanded = false
                          response.hours = it
                        }
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  class ChampsResponse {
    var state: Boolean = false
    var times: String = ""
    var hours: String = ""

    fun isCompleted(): Boolean {
      return (state && times.isNotEmpty() && hours.isNotEmpty()) || !state
    }

    override fun toString(): String {
      return "$state,$times,$hours"
    }
  }
}
