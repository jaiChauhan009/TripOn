package com.example.tripon.model.hotel

data class PriceBreakdown(
    val benefitBadges: List<BenefitBadge>,
    val grossPrice: GrossPrice,
    val strikethroughPrice: StrikethroughPrice,
    val taxExceptions: List<Any>
)