//package com.kartum.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.Toast;
//
//import com.loopj.android.http.AsyncHttpResponseHandler;
//
//import cz.msebera.android.httpclient.Header;
//
//
//public abstract class AsyncResponseHandler extends AsyncHttpResponseHandler {
//
//    private Activity context;
//
//    public AsyncResponseHandler(Activity context) {
//        this.context = context;
//    }
//
//    public AsyncResponseHandler(Context context) {
//    }
//
//    abstract public void onSuccess(String content);
//
//    abstract public void onFailure(Throwable e, String content);
//
//    @Override
//    public void onFailure(int ResponseCode, Header[] arg1, byte[] bytes, Throwable e) {
//        try {
//            String response = new String(bytes, "UTF-8");
//            Debug.e("onFailure", "" + ResponseCode + " " + e.getMessage() + " " + response);
//            e.printStackTrace();
//            onFailure(e, "" + response);
//
//            if (ResponseCode == 500) {
//                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onSuccess(int ResponseCode, Header[] arg1, byte[] bytes) {
//        try {
//
////            Debug.e("onSuccess", "" + ResponseCode);
//            String response = new String(bytes, "UTF-8");
////            try {
////                if (ResponseCode == 200) {
////
////                    Response login = new Gson().fromJson(
////                            response, new TypeToken<Response>() {
////                            }.getType());
////
////                    if (login.status.equals("4")) {
////                        Utils.clearLoginCredetials(context);
////
////                        LocalBroadcastManager.getInstance(context)
////                                .sendBroadcast(
////                                        new Intent(Constant.FINISH_ACTIVITY));
////
////                        Intent intent = new Intent(context,
////                                LoginActivity.class);
////                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
////                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                        context.startActivity(intent);
////                        return;
////                    }
////
////                }
////
////            } catch (Exception e) {
////                Utils.sendExceptionReport(e);
////                onFailure(e, "" + e.getMessage());
////            }
//
//            onSuccess(response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            onFailure(e, "" + e.getMessage());
//        }
//    }
//
//}
