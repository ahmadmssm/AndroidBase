package ams.androidbase.androidbase.managers;

import android.content.Context;
import android.content.Intent;

import ams.androidbase.androidbase.common.Application;
import ams.androidbase.androidbase.scenes.fragments.homeScene.HomeFragment;
import ams.androidbase.androidbase.R;
import ams.androidbase.androidbase.scenes.activites.mainScreenScene.MainActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigator {

    private static Context getActivityContext() { return Application.getInstance().getBaseContext(); }

    public static void openHomeFragment (FragmentManager fragmentManager) {
        Fragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_view, fragment);
        fragmentTransaction.commit();
    }

    public static void openMainActivity () {
        Intent intent = new Intent(getActivityContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivityContext().startActivity(intent);
    }

    public static void openFragmentWithFlag(FragmentManager fragmentManager, String flag) {
        // APHomeFragment apHomeFragment = new APHomeFragment();
        // bundle.putBoolean("flag", isLoggedIn);
        // apHomeFragment.setArguments(bundle);
        // transaction.replace(R.id.grouplayout, apHomeFragment, APStaticKeys.home);
        // transaction.addToBackStack(null);
        // transaction.commit();
    }

}
