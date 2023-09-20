package common.hoangdz.lib.di

import android.content.Context
import common.hoangdz.lib.media.audio.AudioService
import common.hoangdz.lib.media.audio.SimpleAudioService
import common.hoangdz.lib.media.video.torch.TorchHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class GlobalModule {

    @Provides
    @Singleton
    fun provideAudioService(
        @ApplicationContext context: Context,
        torchHelper: TorchHelper
    ): AudioService = SimpleAudioService(context, torchHelper)
}