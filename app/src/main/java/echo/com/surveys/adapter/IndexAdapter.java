package echo.com.surveys.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import echo.com.surveys.R;

import java.util.ArrayList;

public class IndexAdapter extends RecyclerView.Adapter {

    ArrayList<Boolean> indexList;

    public IndexAdapter(ArrayList<Boolean> indexList) {
        this.indexList = indexList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((IndexViewHolder) holder).bindData(indexList.get(position));
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.layout_item_indexer;
    }


    @Override
    public int getItemCount() {
        return indexList.size();
    }

    public class IndexViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public IndexViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.indexer);
        }
        public void bindData(final Boolean isSelected) {
            imageView.setImageResource(isSelected? R.drawable.shape_circle_filled: R.drawable.shape_circle_empty);
        }
    }



    }

