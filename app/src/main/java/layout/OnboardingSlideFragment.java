package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charliechao.fishintel.R;

public class OnboardingSlideFragment extends Fragment {

    public OnboardingSlideFragment() {}

    public static OnboardingSlideFragment newInstance(int iconId, String text, String subtext) {
        OnboardingSlideFragment fragment = new OnboardingSlideFragment();
        Bundle args = new Bundle();
        args.putInt("iconId", iconId);
        args.putString("text", text);
        args.putString("subtext", subtext);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_slide, container, false);
        AppCompatImageView icon= (AppCompatImageView) view.findViewById(R.id.onboarding_slide_icon);
        icon.setImageResource(getArguments().getInt("iconId"));
        TextView text = (TextView) view.findViewById(R.id.onboarding_slide_text);
        text.setText(getArguments().getString("text"));
        TextView subtext = (TextView) view.findViewById(R.id.onboarding_slide_subtext);
        subtext.setText(getArguments().getString("subtext"));
        return view;
    }

}
