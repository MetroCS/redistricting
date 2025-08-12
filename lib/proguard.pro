-dontobfuscate
-dontoptimize
-ignorewarnings
-dontwarn **
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature
-keep class ** { *; }
-keep interface ** { *; }
-keep enum ** { *; }
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}
