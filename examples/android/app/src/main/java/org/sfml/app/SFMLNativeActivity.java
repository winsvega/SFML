package org.sfml.app;

import android.app.NativeActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;

public class SFMLNativeActivity extends NativeActivity {
    private EditText bridgeEditText;
    private boolean suppressChange;

    private static native void nativeCommitText(String text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bridgeEditText = new EditText(this);
        bridgeEditText.setSingleLine(true);
        bridgeEditText.setAlpha(0f);
        bridgeEditText.setFocusable(true);
        bridgeEditText.setFocusableInTouchMode(true);

        bridgeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (suppressChange) {
                    return;
                }

                if (editable.length() == 0) {
                    return;
                }

                final String committed = editable.toString();
                nativeCommitText(committed);

                suppressChange = true;
                editable.clear();
                suppressChange = false;
            }
        });

        addContentView(bridgeEditText, new FrameLayout.LayoutParams(1, 1));
        bridgeEditText.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bridgeEditText != null) {
            bridgeEditText.requestFocus();
        }
    }
}
