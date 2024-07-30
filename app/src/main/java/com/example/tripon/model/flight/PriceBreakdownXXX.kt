package com.example.tripon.model.flight

data class PriceBreakdownXXX(
    val baseFare: BaseFare,
    val carrierTaxBreakdown: List<CarrierTaxBreakdown>,
    val discount: Discount,
    val fee: Fee,
    val moreTaxesAndFees: MoreTaxesAndFees,
    val tax: Tax,
    val total: Total,
    val totalRounded: TotalRounded,
    val totalWithoutDiscount: TotalWithoutDiscount,
    val totalWithoutDiscountRounded: TotalWithoutDiscountRounded
)