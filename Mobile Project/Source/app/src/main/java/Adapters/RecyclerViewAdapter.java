package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chakradhar.weatherapp.R;

import java.util.List;

//shos weekly weather data

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<Weather> dailyWeather;

    protected Context context;

    public RecyclerViewAdapter(Context context, List<Weather> dailyWeather) {
        this.dailyWeather = dailyWeather;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_page, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        holder.dayOfWeek.setText(dailyWeather.get(position).getDayOfWeek());


        String mTemp = Integer.toString(dailyWeather.get(position).getTemperature());
        holder.weatherResult.setText(mTemp+" C");

        holder.weatherResultSmall.setText(mTemp);
        holder.weatherResultSmall.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }


}
