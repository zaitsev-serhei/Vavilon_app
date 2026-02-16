package com.vavilon.model.dataHandlers

import com.vavilon.model.ItemStatus

class PlanItemWrapper(
    private var source: Source,
    private var transaction: Transaction,
    private var planId: Long
) {
    fun getPlanId(): Long {
        return this.planId
    }

    fun getTransaction(): Transaction {
        return this.transaction
    }

    fun getSource(): Source {
        return this.source
    }

    fun updateItemStatus(status: ItemStatus) {
        this.transaction.setStatus(status)
    }

}