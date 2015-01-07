package com.example.listdemo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class GradientListView extends ListView {


    private int mStartColor = Color.WHITE;
    private int mEndColor = Color.RED;
    private static final int MIN_ALAPHA = 0;
    private static final int MAX_ALAPHA = 50;
    private int mAlphaInterval;

    public GradientListView(Context context) {
        super(context);
    }

    public GradientListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int childCount = getChildCount();
        mAlphaInterval = (MAX_ALAPHA - MIN_ALAPHA) / childCount;
        int listViewHeight = getHeight();
        for (int i = childCount - 1; i >= 0; i--) {
            if(getChildAt(i) instanceof View){
                final View view = (MessageListItem)getChildAt(i);
                MessageItem msgItem = view.mMessageItem;
                if (view != null) {
                    int y = (int) view.getY();
                    int fromBottom = listViewHeight - y;
                    int alpha = MAX_ALAPHA - (int) (1.0f * fromBottom / listViewHeight * MAX_ALAPHA);
                    if (alpha < 0) {
                        alpha = 0;
                    }
                    alpha = MAX_ALAPHA - alpha;
                    int endAlpha = alpha - mAlphaInterval;
                    if (endAlpha < 0) {
                        endAlpha = 0;
                    }
                    int startColor = Color.argb(alpha, 255, 255, 255);
                    int endColor = Color.argb(endAlpha, 255, 255, 255);
                    final LinearGradient gradient = new LinearGradient(0, 0, 0, view.getHeight(), startColor, endColor,
                            Shader.TileMode.REPEAT);
                    Shape shape = new Shape() {
                        @Override
                        public void draw(Canvas canvas, Paint paint) {
                            paint.setShader(gradient);
                            canvas.drawRect(0, 0, view.getWidth(), view.getHeight(), paint);
                        }
                    };
                    ShapeDrawable drawable = new ShapeDrawable(shape);
                    Drawable[] layers = new Drawable[2];
                    int boxType = msgItem.mMsgType;
                    boolean isBottom = msgItem.isBottem;
                    boolean isLastItem = msgItem.mIsLastItem;
                    View v = view.findViewById(R.id.mms_sms_text_area);
                    View texView = v.findViewById(R.id.text_view);
//                Log.d("li","------isBottom---->"+isBottom+"------isLastItem---->"+isLastItem);
//                Log.d("li","------boxType----------------->"+boxType);
                    if (boxType == MessageListAdapter.TIMING_ITEM_TYPE_SMS) {

                    } else {
                        if((boxType == MessageListAdapter.INCOMING_ITEM_TYPE_SMS ||
                                boxType == MessageListAdapter.INCOMING_ITEM_TYPE_MMS)){
                            if(!isBottom){
                                texView.setPadding(35,20,35,20);
                                layers[0] = getResources().getDrawable(R.drawable.message_list_recv_nobottom_selector);
                                if(isLastItem){
                                    texView.setPadding(35,45,35,20);
                                    layers[0] = getResources().getDrawable(R.drawable.message_list_recv_selector);
                                }
                            }else{
                                texView.setPadding(35,45,35,20);
                                layers[0] = getResources().getDrawable(R.drawable.message_list_recv_selector);
                            }

                        }else{
                            if(!isBottom){
                                texView.setPadding(55,20,35,20);
                                layers[0] = getResources().getDrawable(R.drawable.message_list_send_nobottom_selector);
                                if(isLastItem){
                                    texView.setPadding(55,20,35,55);
                                    layers[0] = getResources().getDrawable(R.drawable.message_list_send_selector);
                                }
                            }else{
                                texView.setPadding(55,20,35,55);
                                layers[0] = getResources().getDrawable(R.drawable.message_list_send_selector);

                            }
                        }
                    }

                    layers[1] = drawable;
                    LayerDrawable layerDrawable = new LayerDrawable(layers);
                    v.setBackground(layerDrawable);
                }
            }

        }
    }
}
