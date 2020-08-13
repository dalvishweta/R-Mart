package com.rmart.orders.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rmart.R;
import com.rmart.orders.adapters.OrdersListAdapter;
import com.rmart.orders.models.OrderListObject;
import com.rmart.orders.models.OrderObject;
import com.rmart.orders.models.ProductObject;

import java.util.ArrayList;

public class OrderListFragment extends BaseOrderFragment implements View.OnClickListener {

    private static final String ARG_ORDER_OBJECT = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(OrderObject orderObject, String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER_OBJECT, orderObject);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_ORDER_OBJECT);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView orderList = view.findViewById(R.id.order_list);
        ArrayList<OrderListObject> orderListObjects = new ArrayList<>();

        ArrayList<ProductObject> productObjects = new ArrayList<>();
        productObjects.add(new ProductObject("Aashirvad Multigrain Atta", "1KG", "1", "100"));
        productObjects.add(new ProductObject("Aashirvad Multigrain Atta", "2KG", "1", "200"));
        productObjects.add(new ProductObject("Aashirvad Multigrain Atta", "3KG", "1", "300"));
        orderListObjects.add(new OrderListObject("10 Aug 2020","13", "12321", productObjects));
        orderListObjects.add(new OrderListObject("10 Aug 2020","14", "12322", productObjects));
        orderListObjects.add(new OrderListObject("10 Aug 2020","15", "22323", productObjects));
        orderListObjects.add(new OrderListObject("10 Aug 2020","16", "22324", productObjects));
        orderListObjects.add(new OrderListObject("10 Aug 2020","17", "12325", productObjects));
        orderListObjects.add(new OrderListObject("10 Aug 2020","18", "12326", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","19", "12327", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","20", "12328", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","21", "12329", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","22", "12330", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","23", "12331", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","24", "12332", productObjects));
        orderListObjects.add(new OrderListObject("11 Aug 2020","25", "12333", productObjects));
        orderListObjects.add(new OrderListObject("12 Aug 2020","26", "12334", productObjects));
        orderListObjects.add(new OrderListObject("12 Aug 2020","27", "12335", productObjects));
        orderListObjects.add(new OrderListObject("12 Aug 2020","28", "12336", productObjects));
        orderListObjects.add(new OrderListObject("12 Aug 2020","29", "12337", productObjects));
        orderListObjects.add(new OrderListObject("12 Aug 2020","30", "12338", productObjects));
        orderListObjects.add(new OrderListObject("12 Aug 2020","31", "12339", productObjects));
        OrdersListAdapter ordersListAdapter = new OrdersListAdapter(orderListObjects, this);
        orderList.setAdapter(ordersListAdapter);
    }

    @Override
    public void onClick(View view) {
        OrderListObject orderListObject = (OrderListObject) view.getTag();
        mListener.goToViewFullOrder(orderListObject);
    }
}