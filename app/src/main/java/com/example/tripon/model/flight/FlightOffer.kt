package com.example.tripon.model.flight

data class FlightOffer(
    val ancillaries: Ancillaries,
    val appliedDiscounts: List<Any>,
    val badges: List<Badge>,
    val brandedFareInfo: BrandedFareInfo,
    val extraProductDisplayRequirements: ExtraProductDisplayRequirements,
    val extraProducts: List<ExtraProduct>,
    val includedProducts: IncludedProducts,
    val includedProductsBySegment: List<List<IncludedProductsBySegment>>,
    val offerExtras: OfferExtras,
    val offerKeyToHighlight: String,
    val pointOfSale: String,
    val posMismatch: PosMismatch,
    val priceBreakdown: PriceBreakdownXXX,
    val priceDisplayRequirements: List<Any>,
    val requestableBrandedFares: Boolean,
    val seatAvailability: SeatAvailability,
    val segments: List<SegmentX>,
    val token: String,
    val travellerPrices: List<TravellerPriceX>,
    val tripType: String
)