package projects.shahabgt.com.myuniversity;

import android.os.Bundle;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class HelpActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.h1)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.mainimg)
                        .title("title 1")
                        .description("Description 1")
                        .build());
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.h2)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.frown)
                .title("title 2")
                .description("Description 2")
                .build());
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.h3)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.pen)
                .title("title 3")
                .description("Description 3")
                .build());
    }
}
