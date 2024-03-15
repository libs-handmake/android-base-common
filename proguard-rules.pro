-keepclassmembers class common.hoangdz.lib.widget.recyclerview.MyCustomRecyclerView{
    <init>(android.content.Context);
    <init>(android.content.Context, android.util.AttributeSet);
}
#-keep class common.hoangdz.lib.databinding.**{*;}
#-keep public class common.hoangdz.lib.widget.recyclerview.MyCustomRecyclerView{*;}
#-keep class common.hoangdz.lib.extensions.** { *; }
-keep class com.intuit.sdp.R.** { *; }
-keepclassmembers class **.R$* {
       public static <fields>;
}
-keepnames @common.hoangdz.lib.utils.anotations.KeepNameR8 class *
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

#For AppLovin integration
-keepclassmembers class com.applovin.sdk.AppLovinSdk {
    static *;
}
-keep public interface com.applovin.sdk** {*; }
-keep public interface com.applovin.adview** {*; }
-keep public interface com.applovin.mediation** {*; }
-keep public interface com.applovin.communicator** {*; }