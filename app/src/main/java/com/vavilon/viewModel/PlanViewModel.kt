package com.vavilon.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vavilon.model.ItemStatus
import com.vavilon.model.events.PlanEvent
import com.vavilon.model.repositories.PlanRepository
import com.vavilon.model.states.PlanState
import com.vavilon.storage.local.entities.PlanEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class PlanViewModel @Inject constructor(private val planRepository: PlanRepository) : ViewModel() {
    private val _state = MutableStateFlow(PlanState())
    val state: StateFlow<PlanState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            planRepository.getPlanList().collect { planList ->
                val currentPlan =
                    planList.find { plan: PlanEntity -> plan.status == ItemStatus.INPROCESS }
                Log.d("PlanViewModel", "Current Plan [$currentPlan]")
                if (currentPlan != null) {
                    fetchPlannedItems(currentPlan.id)
                    _state.update { state ->
                        state.copy(currentPlan = currentPlan, planList = planList)
                    }
                }
                currentPlan?.let { fetchPlannedItems(it.id) }
            }
        }
    }

    private fun fetchPlannedItems(planId: Long) {
        viewModelScope.launch {
            planRepository.getPlannedItems(planId).collect { map ->
                _state.update { it.copy(planedItemsMap = map) }
            }
        }
    }

    fun OnEvent(event: PlanEvent) {
        when (event) {
            PlanEvent.SavePlan -> {
                viewModelScope.launch {
                    val currentPlan = _state.value.currentPlan
                    if (currentPlan != null) {
                        planRepository.createPlan(currentPlan)
                    }
                }
            }

            is PlanEvent.AddSourceToPlan -> {
                val currentPlan = _state.value.currentPlan
                Log.d("PlanViewModel", "Current Plan [$currentPlan]")
                if (currentPlan != null) {
                    viewModelScope.launch {
                        planRepository.addSourceToPlan(currentPlan.id, event.sourceId)
                        _state.update { it.copy(sourceId = event.sourceId) }
                    }
                } else {
                    Log.d("PlanViewModel", "No active plan found!")
                }
            }

            is PlanEvent.SetTransactionId -> {
                _state.update {
                    it.copy(transactionId = event.transactionId)
                }
            }

            is PlanEvent.UpdatePlan -> TODO()

            PlanEvent.AddPlan -> {
                viewModelScope.launch {
                    createNewPlan()
                }
            }
        }
    }

    private suspend fun createNewPlan() {
        val currentPlans = state.value.planList
        val activePlans = currentPlans.filter { it.status == ItemStatus.INPROCESS }

        val calendar = Calendar.getInstance()
        val currentMonth =
            calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: "Unknown"
        val currentYear = calendar.get(Calendar.YEAR)

        if (currentPlans.isEmpty()) {
            val (startDate, endDate) = getMonthRange(calendar)

            val newPlan = PlanEntity(
                description = "Plan for $currentMonth $currentYear",
                status = ItemStatus.INPROCESS,
                creationDate = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).format(Date()),
                startDate = startDate,
                endDate = endDate
            )
            planRepository.createPlan(newPlan)
            _state.update { it.copy(currentPlan = newPlan) }
            Log.d("PlanViewModel", "Current Plan [$newPlan]")
        } else if (activePlans.size == 1) {
            calendar.add(Calendar.MONTH, 1)
            val nextMonth =
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                    ?: "Unknown"
            val nextYear = calendar.get(Calendar.YEAR)
            //maybe count check is preferred here not to rely on String check -> localization will be introduced in the future
            val existingNextMonthPlan = currentPlans.any { plan ->
                plan.description == "Plan for $nextMonth $nextYear"
            }
            if (!existingNextMonthPlan) {
                val (startDate, endDate) = getMonthRange(calendar)
                val newPlan = PlanEntity(
                    description = "Plan for $nextMonth $nextYear",
                    status = ItemStatus.PLANNED,
                    creationDate = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(Date()),
                    startDate = startDate,
                    endDate = endDate
                )
                planRepository.createPlan(newPlan)
                _state.update { it.copy(currentPlan = newPlan) }
                Log.d("PlanViewModel", "Current Plan [$newPlan]")
            } else {
                _state.update {
                    it.copy(
                        errorMessage = "Plan for next month already exists"
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    errorMessage = "Critical issue creating new Plan. It should not be so ((("
                )
            }
        }
    }

    private fun getMonthRange(calendar: Calendar): Pair<String, String> {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        return Pair(startDate, endDate)
    }
}
