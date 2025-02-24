package com.vavilon.model.dataHandlers

import com.vavilon.model.ItemStatus
import java.text.SimpleDateFormat
import java.util.Date

data class Plan(
    private var id: Long = 0,
    private var description: String,
    private var status: ItemStatus,
    private var startDate: Date,
    private var endDate: Date,
    private var creationDate: Date,
    private var plannedItems: List<PlanItemWrapper> = emptyList(),
    private var isUpdated: Boolean = true
) {
    fun getId(): Long = id
    fun setId(id: Long) {
        this.id = id
    }

    fun getDescription(): String = description
    fun setDescription(description: String) {
        this.description = description
    }

    fun getStatus(): ItemStatus = status
    fun setStatus(status: ItemStatus) {
        this.status = status
    }

    fun getStartDate(): Date = startDate
    fun setStartDate(startDate: Date) {
        this.startDate = startDate
    }

    fun getEndDate(): Date = endDate
    fun setEndDate(endDate: Date) {
        this.endDate = endDate
    }
    fun getCreationDate():Date = creationDate
    fun setCreationDate(date:Date) {
        this.creationDate = date
    }
    fun getPlannedItems(): List<PlanItemWrapper> = plannedItems
    fun setPlannedItems(plannedItems: List<PlanItemWrapper>) {
        this.plannedItems = plannedItems
    }

    fun isUpdated(): Boolean = isUpdated
    fun setUpdated(isUpdated: Boolean) {
        this.isUpdated = isUpdated
    }
}