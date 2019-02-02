package ir.rainyday.localemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import java.util.Locale;

public class AppLocaleChangeReceiver extends BroadcastReceiver {

    private AppLocaleChangeListener mListener = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(LocaleManager.APP_LOCALE_CHANGED_BROADCAST_ACTION)) {

            if (intent.hasExtra(LocaleManager.APP_LOCALE_NEW_LOCALE_KEY) && mListener != null) {
                String lang = intent.getStringExtra(LocaleManager.APP_LOCALE_NEW_LOCALE_KEY);
                mListener.onAppLocaleChanged(new Locale(lang));
            }

        }
    }

    public void setListener(AppLocaleChangeListener mListener) {
        this.mListener = mListener;
    }


    public interface AppLocaleChangeListener {
        void onAppLocaleChanged(@NonNull Locale  newLocale);
    }
}
