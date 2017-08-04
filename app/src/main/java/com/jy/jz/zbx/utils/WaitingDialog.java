package com.jy.jz.zbx.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.jy.jz.zbx.R;

/**
 * 等待框
 */
public class WaitingDialog {
	private Dialog waitingDialog;
	private Context context;

	public WaitingDialog(Context context) {
		super();
		this.context = context;
	}

	public WaitingDialog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 显示等待框
	 */
	public void showWaitingDialog() {
		if (waitingDialog == null) {
			DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					ImageView img = (ImageView) waitingDialog
							.findViewById(R.id.waiting);
					// 滚动条滚动
					((AnimationDrawable) img.getDrawable()).start();
				}
			};
			DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					dismissWaitingDialog();
				}
			};
			waitingDialog = new Dialog(context, R.style.myDialogTheme);
			waitingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			waitingDialog.setContentView(R.layout.waiting_dialog);
			waitingDialog.setOnShowListener(showListener);
			waitingDialog.setCanceledOnTouchOutside(false);
			waitingDialog.setOnCancelListener(cancelListener);
			waitingDialog.show();
		}
	}

	/**
	 * 显示等待框,不可中断
	 */
	public void showWaitingDialogForNotCancel() {
		if (waitingDialog == null) {
			DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					ImageView img = (ImageView) waitingDialog
							.findViewById(R.id.waiting);
					// 滚动条滚动
					((AnimationDrawable) img.getDrawable()).start();
				}
			};
			DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					dismissWaitingDialog();
				}
			};
//			waitingDialog = new Dialog(context, R.style.TransparentDialog);
			waitingDialog = new Dialog(context);
			waitingDialog.setContentView(R.layout.waiting_dialog);
			waitingDialog.setOnShowListener(showListener);
			waitingDialog.setCanceledOnTouchOutside(false);
			waitingDialog.setOnCancelListener(cancelListener);
			waitingDialog.setCancelable(false);
			waitingDialog.show();
		}
	}

	/**
	 * 销毁等待框
	 */
	public void dismissWaitingDialog() {
		if (waitingDialog != null&&waitingDialog.isShowing()) {
			ImageView img = (ImageView) waitingDialog
					.findViewById(R.id.waiting);
			((AnimationDrawable) img.getDrawable()).stop();
			waitingDialog.dismiss();
			waitingDialog = null;
		}
	}

}
