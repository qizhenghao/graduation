package com.ultrawise.softwareproduct.idevplatform.request;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import com.ultrawise.softwareproduct.idevplatform.R;
import com.ultrawise.softwareproduct.idevplatform.view.ProgressWheel;

/**
 * 请求线程
 * 
 * @author
 * @param <T>
 * 
 */
public  class BaseAsyncTask<T> extends
		AsyncTask<Void, Integer, Response<T>> {

	private BaseCallBack<T> callBack;
	private BaseRequest<T> mRequest;
	private int what = 0;
    private Dialog dialog;

	public  BaseAsyncTask(int what,  BaseRequest mSQLRequest,
			BaseCallBack<T> callBack ,Dialog dialog) {
		this.mRequest = mSQLRequest;
		this.callBack = callBack;
		this.what = what;
        this.dialog=dialog;
	}
	@Override
	protected Response<T> doInBackground(Void... params) {
		Response<?> dbResponse = null;
		if (!isCancelled()) {
			dbResponse = new DBResponse<T>();
			try {
				Response<?> request = mRequest.request();
				dbResponse = request;
			} catch (Exception e) {
				dbResponse.setError(dbResponse.getError()!=null?dbResponse.getError():new RequestError("error", e.getMessage()));
				e.printStackTrace();
			} finally{
				dbResponse.setWhat(what);
			}
		}
		return (Response<T>) dbResponse;

	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		if (isCancelled()) {
			return;
		}
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog!=null&&mRequest.mRequestDialog!=null&&mRequest.mRequestDialog.isShowDialog()) {
            dialog.show();
            ((ProgressWheel) dialog.getWindow().findViewById(R.id.progressBarTwo)).spin();
        }
    }
    @Override
	protected void onPostExecute(Response<T> response) {
		super.onPostExecute(response);
		if (callBack != null && response != null) {
             if(dialog!=null &&mRequest.mRequestDialog!=null&& mRequest.mRequestDialog.isSystemCancel()){
                dialog.dismiss();
             }
			callBack.onFinished(response);
            callBack.onFinished(response,dialog);
			if (response.getError() != null) {
				callBack.onError(response);
                callBack.onError(response,dialog);
			} else {
				callBack.onSuccess(response);
                callBack.onSuccess(response,dialog);
			}
		}
	}

}
