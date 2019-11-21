package com.example.mygarden;

import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.PTZCommand;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;


public class FourFragment extends Fragment {
    private Button m_oLoginBtn = null;
    private Button m_oPreviewBtn = null;
    private Button m_oPTZBtn = null;
    private SurfaceView m_osurfaceView = null;

    private Button m_oLoginBtn2 = null;
    private Button m_oLoginBtn3 = null;
    private String m_oIPAddr = "192.168.1.65";
    private String m_oPort = "8001";
    private String m_oUser = "admin";
    private String m_oPsd = "chuangxin508";

    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime

    private int m_iPort = -1; // play port
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number

    private final String TAG = "DemoActivity";

    private boolean m_bTalkOn = false;
    private boolean m_bPTZL = false;
    private boolean m_bMultiPlay = false;

    private boolean m_bNeedDecode = true;
    private boolean m_bSaveRealData = false;
    private boolean m_bStopPlayback = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_four,container,false);
        return view;
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!initeSdk()) {

        }

        m_oLoginBtn = (Button) view.findViewById(R.id.btn_Login);
        m_oPreviewBtn = (Button)view. findViewById(R.id.btn_Preview);
        m_oPTZBtn = (Button) view.findViewById(R.id.btn_PTZ);
        m_osurfaceView = (SurfaceView) view.findViewById(R.id.Sur_Player);

        // m_oLoginBtn2 = (Button) findViewById(R.id.btn_Login2);
        //  m_oLoginBtn3 = (Button) findViewById(R.id.btn_Login3);

        m_oLoginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showSingDialog();

            }
        });

        m_oPreviewBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    if (m_iLogID < 0) {
                        Log.e(TAG, "please login on device first");
                        return;
                    }
                    if (m_bNeedDecode) {
                        if (m_iChanNum > 1)// preview more than a channel
                        {
                            if (!m_bMultiPlay) {
                                m_bMultiPlay = true;
                                m_oPreviewBtn.setText("停止");
                            } else {
                                stopMultiPreview();
                                m_bMultiPlay = false;
                                m_oPreviewBtn.setText("播放");
                            }
                        } else // preivew a channel
                        {
                            if (m_iPlayID < 0) {
                                startSinglePreview();
                            } else {
                                stopSinglePreview();
                                m_oPreviewBtn.setText("播放");
                            }
                        }
                    } else {

                    }
                } catch (Exception err) {
                    Log.e(TAG, "error: " + err.toString());
                }
            }
        });
        //这是让旋转的button
        m_oPTZBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    if (m_iLogID < 0) {
                        Log.e(TAG, "please login on a device first");
                        return false;
                    }
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (m_bPTZL == false) {
                            if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(
                                    m_iLogID, m_iStartChan, PTZCommand.PAN_LEFT, 0)) {
                                Log.e(TAG,
                                        "start PAN_LEFT failed with error code: "
                                                + HCNetSDK.getInstance()
                                                .NET_DVR_GetLastError());
                            } else {
                                Log.i(TAG, "start PAN_LEFT succ");
                            }
                        } else {
                            if (!HCNetSDK.getInstance()
                                    .NET_DVR_PTZControl_Other(m_iLogID,
                                            m_iStartChan, PTZCommand.PAN_RIGHT, 0)) {
                                Log.e(TAG,
                                        "start PAN_RIGHT failed with error code: "
                                                + HCNetSDK.getInstance()
                                                .NET_DVR_GetLastError());
                            } else {
                                Log.i(TAG, "start PAN_RIGHT succ");
                            }
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (m_bPTZL == false) {
                            if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(
                                    m_iLogID, m_iStartChan, PTZCommand.PAN_LEFT, 1)) {
                                Log.e(TAG, "stop PAN_LEFT failed with error code: "
                                        + HCNetSDK.getInstance()
                                        .NET_DVR_GetLastError());
                            } else {
                                Log.i(TAG, "stop PAN_LEFT succ");
                            }
                            m_bPTZL = true;
                            m_oPTZBtn.setText("右转");
                        } else {
                            if (!HCNetSDK.getInstance()
                                    .NET_DVR_PTZControl_Other(m_iLogID,
                                            m_iStartChan, PTZCommand.PAN_RIGHT, 1)) {
                                Log.e(TAG,
                                        "stop PAN_RIGHT failed with error code: "
                                                + HCNetSDK.getInstance()
                                                .NET_DVR_GetLastError());
                            } else {
                                Log.i(TAG, "stop PAN_RIGHT succ");
                            }
                            m_bPTZL = false;
                            m_oPTZBtn.setText("左转");
                        }
                    }
                    return true;
                } catch (Exception err) {
                    Log.e(TAG, "error: " + err.toString());
                    return false;
                }
            }
        });

    }
    // @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_osurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.i(TAG, "surface is created" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        Surface surface = holder.getSurface();
        if (true == surface.isValid()) {
            if (false == Player.getInstance()
                    .setVideoWindow(m_iPort, 0, holder)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }

    // @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    // @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        if (true == holder.getSurface().isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }


    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }

    private void startSinglePreview() {
        if (m_iPlaybackID >= 0) {
            Log.i(TAG, "Please stop palyback first");
            return;
        }
        RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
        if (fRealDataCallBack == null) {
            Log.e(TAG, "fRealDataCallBack object is failed!");
            return;
        }
        Log.i(TAG, "m_iStartChan:" + m_iStartChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = 0; // substream
        previewInfo.bBlocked = 1;

        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID,
                previewInfo, fRealDataCallBack);
        if (m_iPlayID < 0) {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }

        Log.i(TAG,
                "NetSdk Play sucess ***********************3***************************");
        m_oPreviewBtn.setText("Stop");
    }


    private void stopMultiPreview() {
        int i = 0;
        for (i = 0; i < 4; i++) {
            //playView[i].stopPreview();
        }
        m_iPlayID = -1;
    }

    private void stopSinglePreview() {
        if (m_iPlayID < 0) {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }

        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
            Log.e(TAG, "StopRealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }

        m_iPlayID = -1;
        stopSinglePlayer();
    }

    private void stopSinglePlayer() {
        Player.getInstance().stopSound();
        // player stop play
        if (!Player.getInstance().stop(m_iPort)) {
            Log.e(TAG, "stop is failed!");
            return;
        }

        if (!Player.getInstance().closeStream(m_iPort)) {
            Log.e(TAG, "closeStream is failed!");
            return;
        }
        if (!Player.getInstance().freePort(m_iPort)) {
            Log.e(TAG, "freePort is failed!" + m_iPort);
            return;
        }
        m_iPort = -1;
    }

    /**
     * @fn loginNormalDevice
     * @author zhuzhenlei
     * @brief login on device
     *            [out]
     * @return login ID
     */
    private int loginNormalDevice() {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        String strIP = m_oIPAddr;
        int nPort = Integer.parseInt(m_oPort);
        String strUser = m_oUser;
        String strPsd = m_oPsd;
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort,
                strUser, strPsd, m_oNetDvrDeviceInfoV30);
        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
        }
        Log.i(TAG, "NET_DVR_Login is Successful!");

        return iLogID;
    }



    private int loginDevice() {
        int iLogID = -1;

        iLogID = loginNormalDevice();

        return iLogID;
    }

    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    private RealPlayCallBack getRealPlayerCbf() {
        RealPlayCallBack cbf = new RealPlayCallBack() {
            public void fRealDataCallBack(int iRealHandle, int iDataType,
                                          byte[] pDataBuffer, int iDataSize) {
                // player channel 1
                processRealData(1, iDataType, pDataBuffer,
                        iDataSize, Player.STREAM_REALTIME);
            }
        };
        return cbf;
    }


    public void processRealData(int iPlayViewNo, int iDataType,
                                byte[] pDataBuffer, int iDataSize, int iStreamMode) {
        if (!m_bNeedDecode) {
            // Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + ",iDataType:" +
            // iDataType + ",iDataSize:" + iDataSize);
        } else {
            if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
                if (m_iPort >= 0) {
                    return;
                }
                m_iPort = Player.getInstance().getPort();
                if (m_iPort == -1) {
                    Log.e(TAG, "getPort is failed with: "
                            + Player.getInstance().getLastError(m_iPort));
                    return;
                }
                Log.i(TAG, "getPort succ with: " + m_iPort);
                if (iDataSize > 0) {
                    if (!Player.getInstance().setStreamOpenMode(m_iPort,
                            iStreamMode)) // set stream mode
                    {
                        Log.e(TAG, "setStreamOpenMode failed");
                        return;
                    }
                    if (!Player.getInstance().openStream(m_iPort, pDataBuffer,
                            iDataSize, 2 * 1024 * 1024)) // open stream
                    {
                        Log.e(TAG, "openStream failed");
                        return;
                    }
                    if (!Player.getInstance().play(m_iPort,
                            m_osurfaceView.getHolder())) {
                        Log.e(TAG, "play failed");
                        return;
                    }
                    if (!Player.getInstance().playSound(m_iPort)) {
                        Log.e(TAG, "playSound failed with error code:"
                                + Player.getInstance().getLastError(m_iPort));
                        return;
                    }
                }
            } else {
                if (!Player.getInstance().inputData(m_iPort, pDataBuffer,
                        iDataSize)) {
                    // Log.e(TAG, "inputData failed with: " +
                    // Player.getInstance().getLastError(m_iPort));
                    for (int i = 0; i < 4000 && m_iPlaybackID >= 0
                            && !m_bStopPlayback; i++) {
                        if (Player.getInstance().inputData(m_iPort,
                                pDataBuffer, iDataSize)) {
                            break;

                        }

                        if (i % 100 == 0) {
                            Log.e(TAG, "inputData failed with: "
                                    + Player.getInstance()
                                    .getLastError(m_iPort) + ", i:" + i);
                        }

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                    }
                }

            }
        }

    }
    int choice;
    private void showSingDialog(){
        final String[] items = {"西南监控","西北监控","东北监控","东南监控"};
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(getContext());
        singleChoiceDialog.setIcon(R.drawable.jiankong2);
        singleChoiceDialog.setTitle("监控选择");
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice= which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice!=-1){
                   if(items[choice].equals("西南监控")){
                       m_oIPAddr = "192.168.1.65";
                       m_oPort = "8001";
                       try {
                           if (m_iLogID < 0) {
                               // login on the device
                               m_iLogID = loginDevice();
                               if (m_iLogID < 0) {
                                   Log.e(TAG, "This device logins failed!");
                                   return;
                               } else {
                                   System.out.println("m_iLogID=" + m_iLogID);
                               }
                               // get instance of exception callback and set
                               ExceptionCallBack oexceptionCbf = getExceptiongCbf();
                               if (oexceptionCbf == null) {
                                   Log.e(TAG, "ExceptionCallBack object is failed!");
                                   return;
                               }

                               if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
                                       oexceptionCbf)) {
                                   Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                                   return;
                               }

                               m_oLoginBtn.setText("已连接");
                               Log.i(TAG,
                                       "Login sucess ****************************1***************************");
                           } else {
                               // whether we have logout
                               if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
                                   Log.e(TAG, " NET_DVR_Logout is failed!");
                                   return;
                               }
                               m_oLoginBtn.setText("摄像机1");
                               m_iLogID = -1;
                           }
                       } catch (Exception err) {
                           Log.e(TAG, "error: " + err.toString());
                       }
                   }
                   else if(items[choice].equals("西北监控")){}
                   else if(items[choice].equals("东南南监控")){}
                   else if(items[choice].equals("东北监控")){}
                   Toast.makeText(getContext(),
                            "你选择了" + items[choice],
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        singleChoiceDialog.show();
    }

}


