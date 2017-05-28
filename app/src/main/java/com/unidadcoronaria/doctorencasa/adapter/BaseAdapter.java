package com.unidadcoronaria.doctorencasa.adapter;

import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by AGUSTIN.BALA on 5/28/2017.
 */

public class BaseAdapter {

   /*  public void setProductList(final List<? extends Product> productList) {
       if (mProductList == null) {
            mProductList = productList;
            notifyItemRangeInserted(0, productList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProductList.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mProductList.get(oldItemPosition).getId() ==
                            productList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product product = productList.get(newItemPosition);
                    Product old = productList.get(oldItemPosition);
                    return product.getId() == old.getId()
                            && Objects.equals(product.getDescription(), old.getDescription())
                            && Objects.equals(product.getName(), old.getName())
                            && product.getPrice() == old.getPrice();
                }
            });
            mProductList = productList;
            result.dispatchUpdatesTo(this);
        }
    } */
}
