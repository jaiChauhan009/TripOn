package com.example.tripon.model.flight

data class TravellerPriceBreakdownX(
    val baseFare: BaseFare,
    val discount: Discount,
    val fee: Fee,
    val moreTaxesAndFees: MoreTaxesAndFees,
    val tax: Tax,
    val total: Total,
    val totalRounded: TotalRounded,
    val totalWithoutDiscount: TotalWithoutDiscount,
    val totalWithoutDiscountRounded: TotalWithoutDiscountRounded
)