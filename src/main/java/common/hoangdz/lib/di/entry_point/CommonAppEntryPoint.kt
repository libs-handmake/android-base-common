package common.hoangdz.lib.di.entry_point

import common.hoangdz.lib.utils.user.PremiumHolder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CommonAppEntryPoint {
    fun premiumHolder(): PremiumHolder
}