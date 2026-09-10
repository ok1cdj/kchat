# kotlinx.serialization — keep generated serializers and @Serializable classes.
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class **$$serializer { *; }
-keepclasseswithmembers class * { @kotlinx.serialization.Serializable *; }
-keepclassmembers @kotlinx.serialization.Serializable class * {
    static <fields>;
    *** Companion;
    static ** INSTANCE;
}

# Ktor + OkHttp engine — R8 otherwise strips the SSE client / reflectively used bits.
-keep class io.ktor.** { *; }
-keepclassmembers class io.ktor.** { volatile <fields>; }
-dontwarn io.ktor.**
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.slf4j.**

# Coroutines internals referenced via reflection.
-keepclassmembers class kotlinx.coroutines.** { volatile <fields>; }
-dontwarn kotlinx.coroutines.**
