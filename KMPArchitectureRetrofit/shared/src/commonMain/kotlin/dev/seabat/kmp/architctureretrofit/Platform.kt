package dev.seabat.kmp.architctureretrofit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform