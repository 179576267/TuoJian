package com.wzf.tuojian.ui.views.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wzf.tuojian.R;
import com.wzf.tuojian.function.http.dto.response.MainBannerResDto;
import com.wzf.tuojian.function.imageloader.ImageLoader;
import com.wzf.tuojian.ui.activity.WebViewActivity;

import java.util.List;

/**
 * zhenfei
 * 看点首页banner适配器
 */
public class AdImagePagerAdapter extends RecyclingPagerAdapter {
	private Context context;
	private List<MainBannerResDto.CarouselListBean> imageIdList;
	ViewGroup father;
	public AdImagePagerAdapter(Context context, List<MainBannerResDto.CarouselListBean> imageIdList) {
		this.context = context;
		this.imageIdList = imageIdList;
	}


	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container) {
		int realPosition = position % imageIdList.size();
		father = container;
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.layout_banner_image, null);
			holder.imageView = (ImageView) view.findViewById(R.id.im);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final MainBannerResDto.CarouselListBean vItem = imageIdList.get(realPosition);
		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(vItem.getBannerType() == 1){
//					PostDetailActivity.startMethod(context, vItem.getPageUrl());
				}else if(vItem.getBannerType() == 2){
					WebViewActivity.startMethod(context, vItem.getPageUrl());
				}
			}
		});
		ImageLoader.getInstance().displayOnlineImage(vItem.getImgUrl(), holder.imageView, 0, 6);
		return view;
	}

	private static class ViewHolder {
		ImageView imageView;
	}

}
