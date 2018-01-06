package com.kartum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kartum.R;
import com.kartum.objects.Spinner;
import com.kartum.utils.Debug;
import com.kartum.utils.Utils;

import java.util.ArrayList;


public class SpinnerAdapter extends BaseAdapter implements Filterable {

	private Context mContext;
	private LayoutInflater infalter;
	private ArrayList<Spinner> data = new ArrayList<Spinner>();
	private ArrayList<Spinner> dataSource = new ArrayList<Spinner>();

	public SpinnerAdapter(Context c) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Spinner getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addAll(ArrayList<Spinner> files) {

		try {

			this.data.clear();
			this.data.addAll(files);

			if (isFilterable) {
				this.dataSource.clear();
				this.dataSource.addAll(files);
			}

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		notifyDataSetChanged();
	}

	public void add(Spinner files) {

		try {
			this.data.add(files);

			if (isFilterable) {
				this.dataSource.add(files);
			}

		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = infalter.inflate(R.layout.spinner_item, null);
			holder.tvMenuTitle = (TextView) convertView;

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.tvMenuTitle.setText(data.get(position).title);
		} catch (Exception e) {
			Utils.sendExceptionReport(e);
		}

		return convertView;
	}

	public class ViewHolder {
		TextView tvMenuTitle;
	}

	boolean isEnable = true;

	public void setParentCategEnabled(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	boolean isFilterable = false;

	public void setFilterable(boolean isFilterable) {
		this.isFilterable = isFilterable;
	}

	@Override
	public Filter getFilter() {

		if (isFilterable) {
			return new PTypeFilter();
		}

		return null;
	}

	private class PTypeFilter extends Filter {

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence prefix, FilterResults results) {
			// NOTE: this function is *always* called from the UI thread.

			data.clear();
			data.addAll((ArrayList<Spinner>) results.values);
			if (data != null && !data.isEmpty()) {
				notifyDataSetChanged();
			} else {
//                data.clear();
//                data.addAll(dataSource);
				notifyDataSetChanged();
			}
		}

		protected FilterResults performFiltering(CharSequence prefix) {
			// NOTE: this function is *always* called from a background thread,
			// and
			// not the UI thread.

			FilterResults results = new FilterResults();
			ArrayList<Spinner> new_res = new ArrayList<Spinner>();
			if (prefix != null && prefix.toString().length() > 0) {
				for (int index = 0; index < dataSource.size(); index++) {

					try {
						Spinner si = dataSource.get(index);

						if (si.title.toLowerCase().contains(
								prefix.toString().toLowerCase())) {
							new_res.add(si);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				results.values = new_res;
				results.count = new_res.size();

			} else {
				Debug.e("", "Called synchronized view");

				results.values = dataSource;
				results.count = dataSource.size();

			}

			return results;
		}
	}
}
