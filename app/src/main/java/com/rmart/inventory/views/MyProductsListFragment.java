package com.rmart.inventory.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rmart.R;

import com.rmart.inventory.adapters.ProductAdapter;
import com.rmart.profile.model.MyProfile;
import com.rmart.retiler.inventory.product.activities.ProductList;
import com.rmart.utilits.LoggerInfo;
import com.rmart.utilits.RetrofitClientInstance;
import com.rmart.utilits.Utils;
import com.rmart.utilits.pojos.ProductListResponse;
import com.rmart.utilits.pojos.ProductResponse;
import com.rmart.utilits.services.VendorInventoryService;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductsListFragment extends BaseInventoryFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProductAdapter productAdapter;
    private AppCompatTextView tvTotalCount;
    private PopupMenu popup;
    private SearchView searchView;
    private VendorInventoryService vendorInventoryService;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<ProductResponse> productsList = new ArrayList<>();
    private String filterType;

    public MyProductsListFragment() {
        // Required empty public constructor
    }

    public static MyProductsListFragment newInstance(String param1, String param2) {
        MyProductsListFragment fragment = new MyProductsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle(getString(R.string.my_product_list));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoggerInfo.printLog("Fragment", "MyProductsListFragment");
        vendorInventoryService = RetrofitClientInstance.getRetrofitInstance().create(VendorInventoryService.class);
        return inflater.inflate(R.layout.fragment_inventory_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView productRecycleView = view.findViewById(R.id.product_list);
        LinearLayout addProduct = view.findViewById(R.id.add_product);
        tvTotalCount = view.findViewById(R.id.category_count);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_items);

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false);
        productRecycleView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(requireActivity(), productsList, onClickListener, 2);
        productRecycleView.setAdapter(productAdapter);

        filterType = "";
        getProductList("1,2,3,4,5,6,7");

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            getProductList("1,2,3,4,5,6,7");
        });
        addProduct.setOnClickListener(this);
        popup = new PopupMenu(requireActivity(), view.findViewById(R.id.sort));
        popup.getMenu().add(Menu.NONE, 1, 1, "All Products");
        popup.getMenu().add(Menu.NONE, 2, 2, "Available");
        popup.getMenu().add(Menu.NONE, 3, 3, "Out Of Stock");
        /*popup.getMenu().add(Menu.NONE, 6, 5, "Unavailable");*/
        popup.setOnMenuItemClickListener(item -> {
            int i = item.getItemId();
            item.setChecked(true);
            filterType = item.getTitle().toString();
            switch (i) {
                case 3:
                    getProductList(String.valueOf(item.getItemId()));
                    break;
                case 2:
                    getProductList("5");
                    break;
                case 6:
                    getProductList("6");
                    break;
                case 1:
                    filterType = "";
                    getProductList("1,2,3,4,5,6,7");
                    break;
                default:
                    break;
            }
            return true;
        });

        // Inflating the Popup using xml file
        // popup.getMenuInflater().inflate(R.menu.inventory_view_products, popup.getMenu());
        view.findViewById(R.id.sort).setOnClickListener(param -> {
            popup.show();
        });

        setSearchView(view);
    }

    private final View.OnClickListener onClickListener = view -> {
        searchView.setQuery("", false);
        searchView.clearFocus();
        ProductResponse product = (ProductResponse) view.getTag();
        mListener.showProductPreview(product, true);
    };

    public void getProductList(String stockType) {
        if(!Utils.isNetworkConnected(requireActivity())) {
            showDialog(getString(R.string.error_internet), getString(R.string.error_internet_text));
            return;
        }
        resetProductsList();
        progressDialog.show();
        vendorInventoryService.getProductList("0", MyProfile.getInstance().getMobileNumber(), stockType).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProductListResponse> call, @NotNull Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    ProductListResponse productsListResponse = response.body();
                    if(productsListResponse != null) {

                        if (productsListResponse.getStatus().equalsIgnoreCase(Utils.SUCCESS)) {
                            if (productsListResponse.getProductResponses().size() <= 0) {
                                showDialog(getString(R.string.sorry), getString(R.string.no_products_error));
                            } else {
                                productsList = productsListResponse.getProductResponses();
                                updateList();
                            }
                        } else {
                            showDialog("", productsListResponse.getMsg());
                        }
                    } else {
                        showDialog(getString(R.string.no_information_available));
                    }
                } else {
                    showDialog("", response.message());
                }
                mSwipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ProductListResponse> call, @NotNull Throwable t) {
                if(t instanceof SocketTimeoutException){
                    showDialog("", getString(R.string.network_slow));
                } else {
                    showDialog("", t.getMessage());
                }
                progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void resetProductsList() {
        productsList.clear();
        productAdapter.updateItems(productsList);
        productAdapter.notifyDataSetChanged();
        tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), productsList.size(), filterType));
    }

    protected void setSearchView(@NonNull View view) {
        if (null != productAdapter) {
            searchView = view.findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.length() == 0) {
                        searchView.clearFocus();
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(!TextUtils.isEmpty(newText)) {
                        productAdapter.getFilter().filter(newText, count -> tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), count, filterType)));
                    } else {
                        updateList();
                    }
                    return false;
                }
            });
        }
    }

    private void updateList() {
        try {
            // resetProductsList();
            tvTotalCount.setText(String.format(getResources().getString(R.string.total_products), productsList.size(), filterType));
            productAdapter.updateItems(productsList);
            productAdapter.notifyDataSetChanged();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_product) {

                //mListener.addProductToInventory(Utils.PRODUCT, "");
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.base_container, ProductList.newInstance(), ProductList.class.getName());
            fragmentTransaction.addToBackStack( ProductList.class.getName());
            fragmentTransaction.commit();
        }
    }





}