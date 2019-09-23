package jpuente.afinal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Deslizador extends AppCompatActivity implements Wiki.OnFragmentInteractionListener, Videos.OnFragmentInteractionListener {

    private ViewPager mViewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.deslizador);

            contextOfApplication = getApplicationContext(); // Context para utilizarlo después en el Wiki

            SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            mViewPager = findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }

        @Override
        public void onFragmentInteraction(Uri uri) {
        }

        public static class PlaceholderFragment extends Fragment {

            private static final String ARG_SECTION_NUMBER = "section_number";

            public PlaceholderFragment() {
            }

            public static Fragment newInstance(int sectionNumber) {

                Fragment fragment = null;
                switch(sectionNumber){
                    case 1:
                        fragment = new Wiki();
                        break;
                    case 2:
                        fragment = new Videos();
                        break;
                }
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);

                return fragment;
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.deslizador, container, false);
                //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }

        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Wiki";
                    case 1:
                        return "Videos";
                }
                return null;
            }
        }

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}