package com.example.tripon.model.flight

data class PriceBreakdown(
    val baseFare: BaseFare,
    val discount: Discount,
    val fee: Fee,
    val moreTaxesAndFees: MoreTaxesAndFees,
    val tax: Tax,
    val total: Total,
    val totalWithoutDiscount: TotalWithoutDiscount
)