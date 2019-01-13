package ams.androidbase.androidbase;

import android.content.Context;
import android.content.Intent;

import ams.androidbase.androidbase.scenes.mainScreenScene.MainActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigator {

    public static void opeHomeFragment (FragmentManager fragmentManager) {
        Fragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_view, fragment);
        fragmentTransaction.commit();
    }

    public static void openMainActivity (Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
