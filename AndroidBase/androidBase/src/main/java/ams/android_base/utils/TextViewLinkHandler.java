package ams.android_base.utils;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Ahmad Mahmoud on 11-May-17.
 */

@SuppressWarnings({"DefaultFileTemplate", "unused"})
public abstract class TextViewLinkHandler extends LinkMovementMethod {

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP)
            return super.onTouchEvent(widget, buffer, event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
        if (link.length != 0) {
            onLinkClick(link[0].getURL());
        }
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    abstract public void onLinkClick(String url);

    // This is how to make clickable links in list or recycler view
    /*
    holder.tvMessage.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                Toast.makeText(holder.tvMessage.getContext(), url, Toast.LENGTH_SHORT);
            }
     });
    */
}