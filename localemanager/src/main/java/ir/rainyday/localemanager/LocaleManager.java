package ir.rainyday.localemanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.core.os.ConfigurationCompat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public  class LocaleManager {

    public static final String APP_LOCALE_CHANGED_BROADCAST_ACTION = "ir.rainyday.localmanager.APP_LOCALE_CHANGED_BROADCAST_ACTION" ;
    public static final String APP_LOCALE_NEW_LOCALE_KEY = "NEW_LOCALE_KEY" ;

    //region Singleton
    private static volatile LocaleManager instance;
    private static final Object mutex = new Object();


    public static LocaleManager getInstance() {
        LocaleManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new LocaleManager();
            }
        }
        return result;
    }
    //endregion

    private String mCurrentLocale = getOperatingSystemLocale().getLanguage();

    //region public methods
    public Locale getCurrentLocale() {
        return new Locale(mCurrentLocale);
    }

    public void setCurrentLocale(Context context, Locale locale) {
        if (locale != null && !locale.getLanguage().equals(this.mCurrentLocale)) {
            this.mCurrentLocale = locale.getLanguage();

            //broadcast new language
            Intent intent = new Intent(APP_LOCALE_CHANGED_BROADCAST_ACTION);
            intent.putExtra(APP_LOCALE_NEW_LOCALE_KEY,locale.getLanguage());
            context.sendBroadcast(intent);
        }
    }

    public Context wrapContext(Context context) {
        return setLanguage(context, mCurrentLocale);
    }

    public void resetLocale(Context context){
        setCurrentLocale(context, getOperatingSystemLocale());
    }
    //endregion

    //region privates
    private Context setLanguage(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }
    //endregion

    //region useful methods
    private static final Set<String> RTL;

    static {
        Set<String> lang = new HashSet<String>();
        lang.add("ar"); // Arabic
        lang.add("dv"); // Divehi
        lang.add("fa"); // Persian (Farsi)
        lang.add("ha"); // Hausa
        lang.add("he"); // Hebrew
        lang.add("iw"); // Hebrew (old code)
        lang.add("ji"); // Yiddish (old code)
        lang.add("ps"); // Pashto, Pushto
        lang.add("ur"); // Urdu
        lang.add("yi"); // Yiddish
        RTL = Collections.unmodifiableSet(lang);
    }

    public static boolean isRTL(String language) {
        return RTL.contains(language);
    }

    public static boolean isRTL(Locale locale) {
        return isRTL(locale.getLanguage());
    }

    public boolean isRTL() {
        return isRTL(mCurrentLocale);
    }

    public static boolean isPersian(Locale locale) {
        return locale.getLanguage().toLowerCase().contains("fa");
    }

    public boolean isPersian() {
        return isPersian(getCurrentLocale());
    }

    public static Locale getOperatingSystemLocale(){
        return ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    }
    //endregion
}
