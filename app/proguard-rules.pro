# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
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
-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontshrink
-ignorewarnings
-repackageclasses ''
-keepparameternames
-allowaccessmodification
-keeppackagenames doNotKeepAThing

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod


-keep class android.** { *; }
-dontwarn android.**
-keep class androidx.** { *; }
-dontwarn androidx.**
-keep class com.google.** { *; }
-dontwarn com.google.**
-keep class com.facebook.ads.** { *; }
-dontwarn com.facebook.ads.**

-keep public class de.mateware.snacky.* {
    public protected *;
}
-keepclassmembernames class de.mateware.snacky.* {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}
-keepclasseswithmembernames class de.mateware.snacky.* {
    native <methods>;
}
-keepclassmembers class de.mateware.snacky.* extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.picasso.Transformation

-keep class com.gun0912.tedpermission.** { *; }
-keep class com.squareup.picasso3.** { *; }
-dontwarn com.yalantis.ucrop.**
-keep class com.yalantis.ucrop.** { *; }
-keep interface com.yalantis.ucrop.** { *; }
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }


#jp.wasabeef.recyclerview
#flipagram.assetcopylib
#me.zhanghai.android.materialratingbar
#com.akexorcist.roundcornerprogressbar
#com.fangxu.allangleexpandablebutton
#com.github.chrisbanes.photoview
#com.github.florent37.shapeofview
#com.like.view
#com.luseen.spacenavigation
#com.makeramen.roundedimageview
#com.yarolegovich.slidingrootnav

