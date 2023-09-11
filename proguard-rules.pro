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