package common.hoangdz.lib.utils.user

import android.content.Context
import common.hoangdz.lib.utils.PreferenceHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiumHolder @Inject constructor(@ApplicationContext context: Context) :
    PreferenceHelper(context) {

    companion object {
        private const val IS_LIFE_TIME = "is_life_time"

        private const val PREMIUM_DAY = "premium_day"
    }

    override fun getPrefName(): String {
        return "user_info"
    }

    private val isLifetime
        get() = pref.getBoolean(IS_LIFE_TIME, false)

    private val premiumDay
        get() = pref.getLong(PREMIUM_DAY, 0L)

    fun setLifeTimePremium(isLifeTime: Boolean) {
        putBoolean(IS_LIFE_TIME, isLifeTime)
        _premiumState.value = isPremium
    }

    fun setPremiumDay(premiumDay: Long) {
        putLong(PREMIUM_DAY, premiumDay)
        _premiumState.value = isPremium
    }

    val isPremium
        get() = true//isLifetime || premiumDay > System.currentTimeMillis()

    private val _premiumState by lazy { MutableStateFlow(isPremium) }

    val premiumState by lazy { _premiumState.asStateFlow() }
}