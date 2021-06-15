package com.rmart.inventory.filter.repository;

public class ProductBrandRepository {
/*
    public static MutableLiveData<ProductBrandReponse> getProductBrand(String vc_number, String operator) {

        DthService dthRechargeService = RetrofitClientInstance.getRetrofitInstanceForAddP().create(ProductBr.class);
        final MutableLiveData<ProductBrandReponse> resultMutableLiveData = new MutableLiveData<>();
        Call<DthResponse> call = dthRechargeService.getCustomerIdInfo(vc_number,operator);
        final DthResponse result = new DthResponse();

        call.enqueue(new Callback<DthResponse>() {
            @Override
            public void onResponse(Call<DthResponse> call, Response<DthResponse> response) {

                if( response.isSuccessful()) {

                    DthResponse data = response.body();
                    resultMutableLiveData.setValue(data);

                } else {

                    String a= response.message();
                    result.setMsg(a);
                    result.setStatus(response.code());
                    resultMutableLiveData.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<DthResponse> call, Throwable t) {
                result.setMsg("Please Check Internet Connection");
                result.setStatus(400);
                resultMutableLiveData.setValue(result);
            }
        });
        return resultMutableLiveData;
    }
*/
}
