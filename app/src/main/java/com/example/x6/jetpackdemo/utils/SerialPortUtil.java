package com.example.x6.jetpackdemo.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android_serialport_api.Device;
import android_serialport_api.SerialPortManager;

public class SerialPortUtil {

    public static String TAG = "SerialPortUtil";

    /**
     * 标记当前串口状态(true:打开,false:关闭)
     **/
//    public static boolean isFlagSerial = false;

    private static SerialPortManager serialPortManager;
    private static String selectPort = "/dev/ttyS2";
    private static int selectSpeed = 115200;

    /**
     * rk3288工业开发屏的接受串口线程,串口数据输入输出流的设置跟串口设置
     */
//    public static SerialPort serialPort = null;
//    public static InputStream inputStream = null;
//    public static OutputStream outputStream = null;
//    public static Thread receiveThread = null;
    public static String strData = "";
    private static Handler mHandler;

    /**
     * 打开串口
     */
    public static void open() {
//        try {

        Device device = new Device();
        device.path = selectPort;
        device.speed = selectSpeed;
        serialPortManager = new SerialPortManager(device);
//        isFlagSerial = true;
        /**
         * 设置数据接收回调 （在线程中运行）
         **/
        serialPortManager.setOnDataReceiveListener(new SerialPortManager.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] bytes, int i) {
                if (bytes != null && bytes.length > 0) {
//                    while (isFlagSerial) { //判断串口是否开启
                        //判断数据头跟校验位是否正确
                        if (bytes[0] == (byte) 0x5A && bytes[1] == (byte) 0xA5 && bytes[16] == (byte) 0x03) {
                            //累加和校验跟尾部校验位做对比
                            if (bytes[i - 1] == ByteUtil.checksum(bytes, i)) {
                                strData = ByteUtil.byteToStr(bytes, i);
                                Message msgs = mHandler.obtainMessage();
                                msgs.obj = strData;
                                //用页面数据将收到数据发到不同fragment的串口数据接受Handler中并将数据进行解析
                                switch (strData.substring(6, 8)) {
                                    case "00":
                                        msgs.what = 0x00;
                                        break;
                                    case "01":
                                        msgs.what = 0x01;
                                        break;
                                    case "02":
                                        msgs.what = 0x02;
                                        break;
                                    case "03":
                                        msgs.what = 0x03;
                                        break;
                                    case "04":
                                        msgs.what = 0x04;
                                        break;
                                    case "05":
                                        msgs.what = 0x05;
                                        break;
                                    case "06":
                                        msgs.what = 0x06;
                                        break;
                                    case "07":
                                        msgs.what = 0x07;
                                        break;
                                    case "08":
                                        msgs.what = 0x08;
                                        break;
                                    case "09":
                                        msgs.what = 0x09;
                                        break;
                                }
                                mHandler.sendMessage(msgs);
                                Log.i(TAG, "readSerialData:" + strData);
                            }
                        }

                    }
                }
//            }
        });

//            serialPort = new SerialPort(new File("/dev/ttyS1"), 115200, 0);
//            inputStream = serialPort.getInputStream();
//            outputStream = serialPort.getOutputStream();
//            receive();
//            isFlagSerial = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 关闭串口
     */
    public static void close() {
        Log.i(TAG, "关闭串口");
        serialPortManager.closeSerialPort();
//        try {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            if (outputStream != null) {
//                outputStream.close();
//            }
//            isFlagSerial = false;//关闭串口时，连接状态标记为false
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 发送串口指令
     */
    public static void sendString(String data) {

        serialPortManager.sendPacket(ByteUtil.hex2byte(data));

//        if (!isFlagSerial) {
//            Log.i(TAG, "串口未打开,发送失败" + data);
//            return;
//        }
//        try {
//            outputStream.write(ByteUtil.hex2byte(data));
//            outputStream.flush();
//            Log.i(TAG, "sendSerialData:" + data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i(TAG, "发送指令出现异常");
//        }
    }

    /**
     * 发送串口指令
     */
    public static void sendString(String data, Handler handler) {
        mHandler = handler;
        serialPortManager.sendPacket(ByteUtil.hex2byte(data));

//        if (!isFlagSerial) {
//            Log.i(TAG, "串口未打开,发送失败" + data);
//            return;
//        }
//        try {
//            outputStream.write(ByteUtil.hex2byte(data));
//            outputStream.flush();
//            Log.i(TAG, "sendSerialData:" + data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i(TAG, "发送指令出现异常");
//        }
    }

    /**
     * 原rk3288工业屏，接收串口数据的方法（自己制作开启一个用于接受串口数据的线程）
     */
//    public static void receive() {
//        if (receiveThread != null && !isFlagSerial) {
//            return;
//        }
//        //创建接受线程
//        receiveThread = new Thread() {
//            @Override
//            public void run() {
//                while (isFlagSerial) {
//                    try {
//                        byte[] readData = new byte[32];
//                        if (inputStream == null) {
//                            return;
//                        }
//                        int size = inputStream.read(readData);
//                        //判断串口是否开启
//                        if (size > 0 && isFlagSerial) {
//                            //判断数据头跟校验位是否正确
//                            if (readData[0] == (byte) 0x5A && readData[1] == (byte) 0xA5 && readData[16] == (byte) 0x03) {
//                                //累加和校验跟尾部校验位做对比
//                                if (readData[size - 1] == ByteUtil.checksum(readData, 18)) {
//                                    strData = ByteUtil.byteToStr(readData, size);
//                                    Message msgs = mHandler.obtainMessage();
//                                    msgs.obj = strData;
//                                    //用页面数据将收到数据发到不同fragment的串口数据接受Handler中并将数据进行解析
//                                    switch (strData.substring(6, 8)) {
//                                        case "00":
//                                            msgs.what = 0x00;
//                                            break;
//                                        case "01":
//                                            msgs.what = 0x01;
//                                            break;
//                                        case "02":
//                                            msgs.what = 0x02;
//                                            break;
//                                        case "03":
//                                            msgs.what = 0x03;
//                                            break;
//                                        case "04":
//                                            msgs.what = 0x04;
//                                            break;
//                                        case "05":
//                                            msgs.what = 0x05;
//                                            break;
//                                        case "06":
//                                            msgs.what = 0x06;
//                                            break;
//                                        case "07":
//                                            msgs.what = 0x07;
//                                            break;
//                                        case "08":
//                                            msgs.what = 0x08;
//                                            break;
//                                        case "09":
//                                            msgs.what = 0x09;
//                                            break;
//                                    }
//                                    mHandler.sendMessage(msgs);
//                                    Log.i(TAG, "readSerialData:" + strData);
//                                }
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        receiveThread.start();
//    }
}
