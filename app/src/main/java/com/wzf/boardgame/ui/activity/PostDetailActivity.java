package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.autolink.AutoLinkUtils;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.response.PostDetailResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.function.imageloader.ImageLoaderToBitmapListener;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.dialog.ShareDialog;
import com.wzf.boardgame.ui.views.ScaleImageView;
import com.wzf.boardgame.utils.ScreenUtils;
import com.wzf.boardgame.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-07-18 10:52
 */

public class PostDetailActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.im_right2)
    ImageView imRight2;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.im_avatar)
    ImageView imAvatar;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.im_v)
    ImageView imV;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_total_reply)
    TextView tvTotalReply;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.tv_view_num)
    TextView tvViewNum;
    private String postId;

    private PostDetailResDto responseDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        postId = getIntent().getStringExtra("postId");
    }

    private boolean first = true;
    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("帖子详情");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.forum_btn_comment_nor);
        imRight1.setVisibility(View.VISIBLE);
        imRight2.setImageResource(R.mipmap.forum_btn_collect_nor);
        imRight2.setVisibility(View.VISIBLE);
    }

    private void initData() {

        UrlService.SERVICE.getPostInformation(new PostReqDto(postId).toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<PostDetailResDto>(this, first) {
                    @Override
                    public void onSuccess(PostDetailResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        first = false;
                        setView(responseDto);
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private void setView(final PostDetailResDto responseDto) {
        llContent.removeAllViews();
        this.responseDto = responseDto;
        tvNickname.setText(responseDto.getNickname());
        imV.setVisibility(responseDto.getAuthLevel() == 0 ? View.GONE : View.VISIBLE );
        tvTitle.setText(responseDto.getPostTitle());
        tvTime.setText(responseDto.getPostTime());
        tvTotalReply.setText("共" + responseDto.getReplyCount() + "条回复");
        tvCommentNum.setText(responseDto.getReplyCount() +"");
        tvViewNum.setText(responseDto.getReadCount() +"");
        setCollectStatus();
        ImageLoader.getInstance().displayOnlineRoundImage(responseDto.getAvatarUrl(), imAvatar);

        String content = responseDto.getPostContent();
        String[] cs = content.split("\\{\\$img\\$\\}");
        TextView tv;

        for (int i = 0; i < cs.length; i++) {
            tv = new TextView(this);
            tv.setTextSize(16);
//            tv.setText(cs[i].replace("\n", ""));
            tv.setText(cs[i]);
            tv.setAutoLinkMask(Linkify.WEB_URLS);
            AutoLinkUtils.interceptHyperLink(tv);
            llContent.addView(tv);
            if (responseDto.getPostImgsUrls().size() > i) {
                final ScaleImageView im = new ScaleImageView(this);
                im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                int w = ScreenUtils.getScreenWidth(MyApplication.getAppInstance());
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, w * 2 / 3);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                im.setLayoutParams(params);
                ImageLoader.getInstance().urlToBitmap(responseDto.getPostImgsUrls().get(i), new ImageLoaderToBitmapListener() {
                    @Override
                    public void onLoadFinish(Bitmap bitmap) {
                        im.setInitSize(bitmap.getWidth(), bitmap.getHeight());
                        im.setImageBitmap(bitmap);
                }});
//                ImageLoader.getInstance().displayOnlineImage(responseDto.getPostImgsUrls().get(i), im, 0, 0);
                llContent.addView(im);
                final int position = i;
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewUtils.previewPicture(PostDetailActivity.this, position, responseDto.getPostImgsUrls());
                    }
                });
            }
        }


    }

    private void setCollectStatus() {
        imRight2.setImageResource(responseDto.getIsCollect() == 0 ? R.mipmap.forum_btn_collect_nor : R.mipmap.forum_btn_collect_sel);
    }

    @OnClick({R.id.im_left, R.id.im_right1, R.id.im_right2, R.id.ll_comment, R.id.im_avatar, R.id.im_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                if(responseDto != null){
                    EditCommentActivity.startMethod(this, postId);
                }
                break;
            case R.id.im_right2:
                if(responseDto != null){
                    changeCollectStatus();
                }
                break;
            case R.id.ll_comment:
                if(responseDto != null) {
                    CommentListActivity.startMethod(this, postId);
                }
                break;
            case R.id.im_avatar:
                if(responseDto != null){
                    UserInfoActivity.startMethod(this, responseDto.getUserId());
                }
                break;
            case R.id.im_more:
                if(responseDto != null){
                    new ShareDialog(this,
                            UrlService.LOGO_URL,
                            "吾声桌游",
                            "吾声 | " + responseDto.getPostTitle() + "——" + responseDto.getNickname(),
                            UrlService.DEFAULT_SHARE_URL).show();
                }

                break;
        }
    }

    private void changeCollectStatus() {
        if(responseDto.getIsCollect() == 0){
            UrlService.SERVICE.collectPost(new PostReqDto(postId).toEncodeString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ResponseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object dto) throws Exception {
                            super.onSuccess(dto);
                            responseDto.setIsCollect(1);
                            setCollectStatus();
                        }

                        @Override
                        public void onFailure(int code, String message) throws Exception {
                            super.onFailure(code, message);
                            showToast(message);
                        }
                    });
        }else {
            UrlService.SERVICE.cancelCollectPost(new PostReqDto(postId).toEncodeString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ResponseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object dto) throws Exception {
                            super.onSuccess(dto);
                            responseDto.setIsCollect(0);
                            setCollectStatus();
                        }

                        @Override
                        public void onFailure(int code, String message) throws Exception {
                            super.onFailure(code, message);
                            showToast(message);
                        }
                    });
        }

    }

    public static void startMethod(Context context, String postId) {
        context.startActivity(new Intent(context, PostDetailActivity.class).
                putExtra("postId", postId));
    }
}
