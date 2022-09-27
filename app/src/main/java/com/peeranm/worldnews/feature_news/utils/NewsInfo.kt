package com.peeranm.worldnews.feature_news.utils

enum class NewsCategory(val categoryName: String) {
    General("general"),
    Technology("technology"),
    Science("science"),
    Business("business"),
    Sports("sports"),
    Health("health"),
    Entertainment("entertainment")
}

enum class CountryCode(val code: String, val country: String) {
    INDIA("in", "India"),
    CHINA("cn", "China"),
    RUSSIA("ru", "Russia"),
    UNITED_STATES("us", "United States"),
    UNITED_ARAB_EMIRATES("ae", "United Arab Emirates"),
    UNITED_KINGDOM("gb", "United Kingdom"),
    JAPAN("jp", "Japan"),
    GERMANY("de", "Germany"),
    AUSTRALIA("au", "Australia"),
    SOUTH_AFRICA("za", "South Africa")
}