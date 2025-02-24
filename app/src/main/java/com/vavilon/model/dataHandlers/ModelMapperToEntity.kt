package com.vavilon.model.dataHandlers

import com.vavilon.model.SourceCategories
import com.vavilon.model.TransactionCategories
import com.vavilon.storage.local.entities.PlanEntity
import com.vavilon.storage.local.entities.SourceEntity
import com.vavilon.storage.local.entities.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Locale

private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
fun Plan.toEntity(): PlanEntity {
    return PlanEntity(
        description = this.getDescription(),
        status = this.getStatus(),
        creationDate = this.getCreationDate().toString(),
        startDate = this.getStartDate().toString(),
        endDate = this.getEndDate().toString()
    ).apply { id = this@toEntity.getId() }

}

fun PlanEntity.toModel(): Plan {
    return Plan(
        id = this.id,
        description = this.description,
        status = this.status,
        startDate = dateFormat.parse(this.startDate) ?: SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).parse("2025-01-01"),
        endDate = dateFormat.parse(this.endDate) ?: SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(
            "2025-01-01"
        ),
        creationDate = dateFormat.parse(this.creationDate) ?: SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).parse("2025-01-01"),
        isUpdated = true
    )
}

fun Source.toEntity(): SourceEntity {
    return SourceEntity(
        sourceType = this.getType().sourceCategory,
        sourceTitle = this.getName(),
        sourceDescription = this.getDescription(),
        balance = this.getCurrentBalance()
    ).apply { sourceId = this@toEntity.getId() }
}

fun SourceEntity.toModel(): Source {
    return Source(
        id = this.sourceId,
        name = this.sourceTitle,
        description = this.sourceDescription,
        type = SourceCategories.entries.firstOrNull { category ->
            category.getSrcCategory() == this.sourceType
        } ?: SourceCategories.INCOME,
        currentBalance = this.currentBalance,
        totalBalance = this.totalBalance
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        amount = this.getValue(),
        category = this.getCategory().getTransactionCategory(),
        description = this.getDescription(),
        sourceId = this.getSourceId(),
        status = this.getStatus(),
        creationDate = this.getTransactionDate().toString()
    ).apply { transactionId = this@toEntity.getId() }
}

fun TransactionEntity.toModel(): Transaction {
    return Transaction(id =this.transactionId ,
        sourceId = this.sourceId,
        amount = this.amount,
        description = this.description,
        status = this.status,
        creationDate = dateFormat.parse(this.transactionDate) ?: SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).parse("2025-01-01"),
        transactionDate = dateFormat.parse(this.transactionDate) ?: SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).parse("2025-01-01"),
        plannedOccurrenceDate = dateFormat.parse(this.transactionDate) ?: SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).parse("2025-01-01"),
        category = TransactionCategories.entries.firstOrNull { category ->
            category.getTransactionCategory() == this.category
        } ?: TransactionCategories.CUSTOM,
        )
}
