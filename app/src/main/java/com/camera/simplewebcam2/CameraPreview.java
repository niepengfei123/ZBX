package com.camera.simplewebcam2;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.camera.simplewebcam2.CaffeMobile;
import com.jy.jz.zbx.R;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    static final String tag = "CameraPreview";
    private static final boolean DEBUG = true;
    protected Context context;
    private SurfaceHolder holder;
    Thread mainLoop = null;
    private Bitmap bmp = null;

    private boolean cameraExists = false;
    private boolean shouldStop = false;

    /// /dev/videox (x=cameraId+cameraBase) is used.
    // In some omap devices, system uses /dev/video[0-3],
    // so users must use /dev/video[4-].
    // In such a case, try cameraId=0 and cameraBase=4
    private int cameraId = 0;//4;
    private int cameraBase = 0;
    //

    // This definition also exists in ImageProc.h.
    // Webcam must support the resolution 640x480 with YUYV format.
    static final int IMG_WIDTH = 1280;
    static final int IMG_HEIGHT = 960;

    // The following variables are used to draw camera images.
    private int winWidth = 0;
    private int winHeight = 0;
    private Rect rect;
    private int dw, dh;
    private float rate;

    //serial port
    EditText mReception;
    FileOutputStream mOutputStream;
    FileInputStream mInputStream;
    //SerialPort sp;
    //ReadThread  mReadThread;
    private int[] buffer_command = new int[128];
    private int size_command;
    private boolean command_flag = false;
    private boolean stop_command_flag = false;
    private boolean begin_command_flag = false;
    int length = 0;

    byte[] stop_command = new byte[4];
    byte[] begin_command = new byte[5];

    byte[] ok_command = new byte[3];

    String modelDir = "/data/test_dnn";
    //    String modelProto = modelDir + "/deploy_ssd.prototxt";//"/deploy_caffe.prototxt";
    String modelProto = modelDir + CameraPreview.class.getClassLoader().getResourceAsStream("deploy_ssd.prototxt");//"/deploy_caffe.prototxt";
    //    String modelBinary = modelDir + "/ResNet_joy_SSD_300x300_50_iter_60000.caffemodel";//"/ResNet_joy_SSD_300x300_50_iter_60000.caffemodel";//"/snapshot_iter_2k.caffemodel";
    String modelBinary = modelDir + CameraPreview.class.getClassLoader().getResourceAsStream("ResNet_joy_SSD_300x300_50_iter_60000.caffemodel");//"/ResNet_joy_SSD_300x300_50_iter_60000.caffemodel";//"/snapshot_iter_2k.caffemodel";

    String[] strArrayResult = {
            "r01red_bean_and_lilyx",
            "r02soy_and_blackx",
            "r03five_color_beanx",
            "r04five_beanx",
            "r05three_blackx",
            "r06rice_and_soyx",
            "r07walnut_sesame_soyx",
            "r08osmanthus_soyx",
            "r09walnut_oat_soyx",
            "r10oat_soyx",
            "r11millet_soyx",
            "r12Buckwheat_soyx",
            "r13ricex",
            "r14black_beanx",
            "r15soybeanx",
            "r16green_beanx",
            "r17milletx",
            "r18nothingx",
            "r19wet_soybeanx"};

    private Button SEND;


    // JNI functions
    public native int prepareCamera(int videoid);

    public native int prepareCameraWithBase(int videoid, int camerabase);

    public native void processCamera();

    public native void stopCamera();

    public native byte[] pixeltobmpDJJ(Bitmap bitmap, int flag);

    public native byte[] pixeltobmpJZLP(Bitmap bitmap, int flag);

    public native void CreateAlg();

    public native void DestoryAlg();

    private CaffeMobile caffeMobile;

    static {
        System.loadLibrary("object_classfication_soy_dnn");
        System.loadLibrary("caffe");
        System.loadLibrary("caffe_jni");
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setKeepScreenOn(true);
        this.setFocusable(true);

        this.context = context;
        if (DEBUG) Log.d("WebCam", "CameraPreview constructed");
        setFocusable(true);

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);


    }

    CameraPreview(Context context) {
        super(context);
        this.context = context;
        if (DEBUG) Log.d("WebCam", "CameraPreview constructed");
        setFocusable(true);

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    }

    int count = 0;


    @Override
    public void run() {
        while (true && cameraExists) {
            Log.i(tag, "loop");
            //obtaining display area to draw a large image
            if (winWidth == 0) {
                winWidth = this.getWidth();
                winHeight = this.getHeight();
                Log.v("sjb", "winWidth=" + winWidth);
                Log.v("sjb", "winHeight=" + winHeight);

                if (winWidth * 3 / 4 <= winHeight) {
                    dw = 0;
                    dh = 0;//(winHeight-winWidth*3/4)/2;
                    rate = ((float) winWidth) / IMG_WIDTH;
                    rect = new Rect(dw, dh, dw + winWidth - 1, dh + winWidth * 3 / 4 - 1);
                } else {
                    dw = 0;//(winWidth-winHeight*4/3)/2;
                    dh = 0;
                    rate = ((float) winHeight) / IMG_HEIGHT;
                    rect = new Rect(dw, dh, dw + winHeight * 4 / 3 - 1, dh + winHeight - 1);
                }
            }


            //algo
            if (command_flag == true) {
                command_flag = false;

                int sum = 0;
                for (int i = 0; i < size_command - 1; i++) {
                    sum += buffer_command[i];
                }
                sum = sum & 0x000000ff;
                if (sum == buffer_command[size_command - 1]) {
                    if (buffer_command[0] == 0x55 && buffer_command[1] == 0xcc) {
                        stop_command_flag = false;
                        begin_command_flag = true;
                    }
                }
                /*
        		if(size_command == 4)//stop
        		{
        			int i;
        			for(i = 0; i < size_command; i++)
            		{
            			if(buffer_command[i] != stop_command[i])
            			{
            				break;
            			}
            		}
        			
        			if(i == size_command)
        			{
        				stop_command_flag  = true;
        				begin_command_flag = false;
        			}
        		}
        		
        		if(size_command == 5)//begin
        		{
        			int i;
        			for(i = 0; i < size_command; i++)
            		{
            			if(buffer_command[i] != begin_command[i])
            			{
            				break;
            			}
            		}
        			
        			if(i == size_command)
        			{
        				stop_command_flag  = false;
        				begin_command_flag = true;
        			}
        		}*/

            }

            // obtaining a camera image (pixel data are stored in an array in JNI).
            //processCamera();
            // camera image to bmp
            //byte[] buffer = new byte[1280 * 960 * 4];
            //pixeltobmp(bmp, buffer);
            int flag = 1;
            //begin_command_flag = true;
            //if(begin_command_flag == true)
            //{
            //	flag = 1;
            //}

            byte[] resutl = pixeltobmpJZLP(bmp, flag);

//        	try {
//        		byte[] send_info = new byte[3];
//        		send_info[0] = 0x55;
//        		send_info[1] = 0x66;
//        		send_info[2] = 0x78;
//				mOutputStream=(FileOutputStream) sp.getOutputStream();
//				mOutputStream.write(send_info);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

            //
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                // draw camera bmp on canvas
                canvas.drawBitmap(bmp, null, rect, null);

                getHolder().unlockCanvasAndPost(canvas);
            }


            //if(begin_command_flag == true && flag == 1)
            {
//        		execShell("echo none >/sys/class/leds/firefly:yellow:user/trigger");

                //byte[] buffer;
                int[] result_caffe = new int[19];

                //classify
                //long startTime=System.currentTimeMillis();   //获取开始时间
//            	result_caffe = caffeMobile.predictImage(resutl, 256, 256, 19);
                //long endTime=System.currentTimeMillis(); //获取结束时间
                //System.out.println("程序运行时间： "+(endTime-startTime)+"ms"+"  类别："+result_caffe[0]);

                //detection
                //long startTime=System.currentTimeMillis();   //获取开始时间
                int leibie_num = 20;
                float[] result_det = new float[leibie_num * 7];
                int fujia_size = 64;
                int img_size = 256 * 256 * 3 / 2;
                int src_img_base_addr = img_size + fujia_size;
                int src_img_size = 300 * 300 * 3 / 2;
                //byte[] img_src_detect = new byte[src_img_size];
                //for(int i = 0; i < src_img_size; i++)
                //{
                //	img_src_detect[i] = resutl[src_img_base_addr + i];
                //}
                result_det = caffeMobile.Detect(resutl, 300, 300);
                //long endTime=System.currentTimeMillis(); //获取结束时间
                //System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

//            	int length = result_det.length;
//            	int num = length / 7;
//            	int[] box = new int[num];
//            	for(int i = 0; i < num; i++)
//            	{
//            		box[i] = (int)result_det[i * 7 + 1];
//            	}
//
//            	byte[] send_info = new byte[5];
//            	//
//            	byte[] front={(byte)0xAA};
//            	//"nothing", "Cooking cpu", "Juice cpu", "broken cpu"
//            	byte[] cup={(byte)0xB0, (byte)0xB1, (byte)0xB2, (byte)0xB3};
//            	//'background',"carrot(whole)", "potato(whole)", "potato(half)", "apple(whole)", "soybean","carrot(piece)","beaf(piece)"
//            	byte[] food={(byte)0xC0, (byte)0xC1, (byte)0xC2, (byte)0xC3, (byte)0xC4, (byte)0xC5, (byte)0xC6, (byte)0xC7};
//            	int numobj = food.length;
//            	//('__background__',
//            	//"carrot(whole)", "potato(whole)", "potato(half)", "apple(whole)", "soybean",
//            	//"carrot(piece)", "beaf(piece)", "Cooking cpu", "Juice cpu", "broken cpu")
//            	byte cup_o  = cup[0];
//            	byte food_o = food[0];
//            	int cls_ind;
//            	for(int i = 0; i < num; i++)
//            	{
//            		cls_ind = box[i];
//            		if(cls_ind < numobj)
//            		{
//            			food_o = food[cls_ind];
//            		}
//            		else
//            		{
//            			cup_o = cup[cls_ind-numobj+1];
//            		}
//            	}
//
//            	int j = 0;
//            	send_info[j++] = (byte)0xaa;
//            	send_info[j++] = cup_o;
//            	send_info[j++] = food_o;
//            	int sum = 0;
//            	for(int i = 0; i < j; i++)
//            	{
//            		sum += send_info[i];
//            	}
//            	send_info[j++] = (byte)(sum & 0xff);
//            	send_info[j++] = 0x78;
//            	int len = send_info.length;
//
//            	//
//          	    int label_length = 256 * 256 * 3 / 2;
//        		int label = resutl[label_length];
//        		label = 17;//result_caffe[0];
//        		if(label > 18 || label < 0)
//        		{
//        			label = 17;
//        		}

//        		try {
//    				mOutputStream=(FileOutputStream) sp.getOutputStream();
//
//    				//mOutputStream.write(new String(strArrayResult[label]).getBytes());
//    				mOutputStream.write(send_info);
//    				//mOutputStream.write('\n');
//    			} catch (IOException e) {
//    				e.printStackTrace();
//    			}

//        		begin_command_flag = false;

//        		execShell("echo default-on >/sys/class/leds/firefly:yellow:user/trigger");
            }


            if (shouldStop) {
                shouldStop = false;
                Log.i(tag, "break");
                break;
            }
        }
        Log.i(tag, "线程退出12");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (DEBUG) Log.d("WebCam", "surfaceCreated");
        if (bmp == null) {
            bmp = Bitmap.createBitmap(IMG_WIDTH, IMG_HEIGHT, Bitmap.Config.ARGB_8888);
        }

        // /dev/videox (x=cameraId + cameraBase) is used
        int ret = prepareCameraWithBase(cameraId, cameraBase);

        if (ret != -1) cameraExists = true;

        CreateAlg();

        caffeMobile = new CaffeMobile();
        caffeMobile.setNumThreads(4);
        caffeMobile.loadModel(modelProto, modelBinary);

        float[] meanValues = {109.96191406250000f, 134.07785034179687f, 136.15139770507812f};
        caffeMobile.setMean(meanValues);

//        SEND = (Button)getRootView().findViewById(R.id.ButtonSent);
//		SEND.setOnClickListener(new manager());
//
//		//
//        mainLoop = new Thread(this);
//        mainLoop.start();
//
//		//serial port
//	    try {
//	    	//sp=new SerialPort(new File("/dev/ttySAC3"),115200);
//	    	sp=new SerialPort(new File("/dev/ttyS3"),9600);
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	    mReadThread = new ReadThread();
//	    mReadThread.start();
//
//	    mInputStream=(FileInputStream) sp.getInputStream();
//
//
//	    stop_command[0] = 0x73;
//	    stop_command[1] = 0x74;
//	    stop_command[2] = 0x6F;
//	    stop_command[3] = 0x70;
//
//	    begin_command[0] = 0x62;
//	    begin_command[1] = 0x65;
//	    begin_command[2] = 0x67;
//	    begin_command[3] = 0x69;
//	    begin_command[4] = 0x6E;
//
//	    ok_command[0] = 0x55;
//	    ok_command[1] = 0x66;
//	    ok_command[2] = 0x78;
//
//	    execShell("echo default-on >/sys/class/leds/firefly:yellow:user/trigger");
    }

    //
//	public void execShell(String cmd){
//        try{
//            //权限设置
//            Process p = Runtime.getRuntime().exec("sh");  //su为root用户,sh普通用户
//            //获取输出流
//            OutputStream outputStream = p.getOutputStream();
//            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
//            //将命令写入
//            dataOutputStream.writeBytes(cmd);
//            //提交命令
//            dataOutputStream.flush();
//            //关闭流操作
//            dataOutputStream.close();
//            outputStream.close();
//       }
//       catch(Throwable t)
//        {
//             t.printStackTrace();
//            }
//    }
//
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (DEBUG) Log.d("WebCam", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (DEBUG) Log.d("WebCam", "surfaceDestroyed");
        if (cameraExists) {
            shouldStop = true;
            while (shouldStop) {
                try {
                    Thread.sleep(100); // wait for thread stopping
                } catch (Exception e) {
                }
            }
        }
        DestoryAlg();
        stopCamera();
//		sp.close();
    }
//
//	private class ReadThread extends Thread {
//
//		@Override
//		public void run() {
//			super.run();
//			while(!isInterrupted()) {
//				int size;
//				try {
//					byte[] buffer = new byte[512];
//					if (mInputStream == null) return;
//					size = mInputStream.read(buffer);
//					if (size > 0) {
//
//						for(int i = 0; i < size; i++)
//						{
//							buffer_command[length] = buffer[i] & 0x0ff;
//							length++;
//						}
//						if(buffer_command[length - 1] == 0x78)
//						{
//							size_command = length - 1;
//							command_flag = true;
//							length = 0;
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//					return;
//				}
//				//mReception.append(new String("recive"));
//			}
//		}
//	}
//
//	class manager implements OnClickListener{
// 		public void onClick(View v) {
// 			switch (v.getId()) {
// 			//send
// 			case R.id.ButtonSent:
// 				Log.d(tag,"algo run start ...");
//
// 				begin_command_flag = true;
// 			}
// 		}
// 	}
}
