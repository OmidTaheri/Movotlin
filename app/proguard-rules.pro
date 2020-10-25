# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontoptimize
-keep class * extends androidx.fragment.app.Fragment{}
-keepclasseswithmembers class ir.omidtaheri.local.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.search.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.mainpage.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.genrelist.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.favorite.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.remote.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.domain.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.data.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.mainpage.entity.**.*{ *; }
-keepclasseswithmembers class ir.omidtaheri.daggercore.**.*{ *; }
-keepclasseswithmembers interface ir.omidtaheri.daggercore.**.*{ *; }
-keepattributes *Annotation*, Signature, Exception