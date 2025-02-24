package com.vavilon.model.dataHandlers

import com.vavilon.model.ItemStatus
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.TransactionEntity

class PlanItemWrapper(
    private var source: SourceEntity,
    private var transaction: TransactionEntity,
    private var planId: Long
) {
    fun getPlanId(): Long {
        return this.planId
    }

    fun getTransaction(): TransactionEntity {
        return this.transaction
    }

    fun getSource(): SourceEntity {
        return this.source
    }

    fun updateItemStatus(status: ItemStatus) {
        this.transaction.status = status
    }


}