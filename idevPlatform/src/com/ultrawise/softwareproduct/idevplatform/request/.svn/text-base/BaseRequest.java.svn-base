package com.ultrawise.softwareproduct.idevplatform.request;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.ultrawise.softwareproduct.idevplatform.R;
import com.ultrawise.softwareproduct.idevplatform.view.ProgressWheel;

/**
 * Created by matti on 2014/4/9.
 */
public abstract class BaseRequest<T> implements IRequest {
	private int what;
	protected BaseCallBack<T> callBack;
    protected  Context  mContext;
    private Dialog  mMesgDialog;
    protected  RequestDialog mRequestDialog;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public BaseRequest() {

	}
    public BaseRequest(Context  context,RequestDialog  requestDialog){
        this.mContext=context;
        this.mRequestDialog=requestDialog;
    }
	public BaseRequest(int what){
        this.what=what;
    }

	public BaseRequest(BaseCallBack<T> callBack) {
		this.callBack = callBack;
	}

	@Override
	public void processing() {
        if(mRequestDialog!=null&&mRequestDialog.isShowDialog()){
            mMesgDialog=creatMesgDialog();
        }
		new BaseAsyncTask<T>(what, this, callBack,mMesgDialog).execute();
	}
    public  Dialog  creatMesgDialog(){
        Dialog  mDialog=new Dialog(mContext, R.style.popupDialog);
        mDialog.setContentView(R.layout.dialog_layout_call);
        ProgressWheel mProgressWheel= (ProgressWheel) mDialog.findViewById(R.id.progressBarTwo);
        if(mRequestDialog.getBarColor()!=0){
        mProgressWheel.setBarColor(mContext.getResources().getColor(mRequestDialog.getBarColor()));
        }
        mDialog.setCanceledOnTouchOutside(mRequestDialog.isTouchCancel());
        mDialog.setCancelable(mRequestDialog.isBackCancel());
        return  mDialog;
    }

	public IRequestCallBack<T> getCallBack() {
		return callBack;
	}

	public void setCallBack(BaseCallBack<T> callBack) {
		this.callBack = callBack;
	}

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

}
