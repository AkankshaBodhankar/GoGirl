package gogirl.apptite.com.apptite;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class MultivaluedFragment extends Fragment {
    WebView webView;
    String URL;

    public MultivaluedFragment() {}

    public static MultivaluedFragment newInstance(String URL) {
        MultivaluedFragment myFragment = new MultivaluedFragment();

        Bundle args = new Bundle();
        args.putString("defacto_url" , URL);
        myFragment.setArguments(args);

        return myFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_multivalued, container, false);
        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                .setProgressBarColor(Color.WHITE)
                .setText("Loading")
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_DEEP_PURPLE))
                .setAnimations(Style.ANIMATIONS_POP).show();



        Bundle b = this.getArguments();
        if(b!=null)
        {
            this.URL = b.getString(getResources().getString(R.string.url));
        }

        webView = (WebView)rootView.findViewById(R.id.multivalued_wv);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.loadUrl(this.URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        return rootView;
    }

}
