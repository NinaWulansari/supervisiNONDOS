/**
 * Created by Nina
 */

package com.wahanaartha.supervisionline.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    public static final String ARGS_INSTANCE = "com.f22labs.instalikefragmenttransaction";


    FragmentNavigation mFragmentNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public abstract int getCount();

    public abstract Object getItem(int i);

    public abstract long getItemId(int i);

    public abstract View getView(int i, View convertView, ViewGroup parent);

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }

}
