package com.jy.jz.zbx.dms;

import com.jy.jz.zbx.utils.APPUtils;

import java.io.Serializable;

public class ModuleInfo implements Serializable {

  /**
   * 设备数据存储类
   */
  private static final long serialVersionUID = -6069535815673134956L;

  private final static int HashCode = 60;

  //
  private byte[] cmd;// 从index=14开始,是业务数据
  String hexString = "";
  //
  private String id;
  private String sn;// u8*2
  private String productType = "0";// u16
  private String productVersion;// u16
  private String encrypt;// u8
  private String token;// u8*2
  private String ip;// u8
  private String mac;// u8
  private String name;// u8
  private String descript;//u8
  private String imgUrl;
  private int status;// 设备是否同步过
  private String devstatus;// 设备状态
  private String uid = "joyoung";

  private String isonline;// 0表示不在线，1表示在线
  private String isActivate;// 0表示未绑定，1表示绑定

  /* =========以下是V1版本兼容的字段========== */
  private String produce_code;// sn码
  private String dname;// name
  private String ismain;//是否是主设备
  private String mdcode;// 型号硬件编码 0002  0004  8193 10240
  private String mcode;// 型号编码
  private String isdeleted;// 是否删除(0 未删除 1 已删除)
  private String fromType;// 设备来自哪里(0 表示本地 1 服务器)
  private long addtime;//添加时间
  private String mname;//型号名称
  private String mid;//型号ID
  private String fcode;//固件版本
  private String chost;//设备连接cms服务器的ip
  private String cport;//设备连接cms服务器的端口
  private String dcode;//硬件编码
  private String pid;//产品线ID
  private String appkey;//新架构解析命令字段
  private String createdate;//添加时间


  public String getCreatedate() {
    return createdate;
  }

  public void setCreatedate(String createdate) {
    this.createdate = createdate;
  }

  public String getAppkey() {
    return appkey;
  }

  public void setAppkey(String appkey) {
    this.appkey = appkey;
  }


  public String getIsmain() {
    return ismain;
  }

  public void setIsmain(String ismain) {
    this.ismain = ismain;
  }

  /**
   * 2016-10-27 by lxc
   */
  private String did; //设备id

  private boolean isChoose;//用于设备管理页面，不存数据库


  public long getAddtime() {
    return addtime;
  }

  public void setAddtime(long addtime) {
    this.addtime = addtime;
  }

  public String getMname() {
    return mname;
  }

  public void setMname(String mname) {
    this.mname = mname;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public String getFcode() {
    return fcode;
  }

  public void setFcode(String fcode) {
    this.fcode = fcode;
  }

  public String getChost() {
    return chost;
  }

  public void setChost(String chost) {
    this.chost = chost;
  }

  public String getCport() {
    return cport;
  }

  public void setCport(String cport) {
    this.cport = cport;
  }

  public String getDcode() {
    return dcode;
  }

  public void setDcode(String dcode) {
    this.dcode = dcode;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public boolean isChoose() {
    return isChoose;
  }

  public void setChoose(boolean isChoose) {
    this.isChoose = isChoose;
  }

  public void setCmd(byte[] cmd) {
    this.cmd = cmd;
  }

  /**
   * @param id2 not use now beacuse id2=sn2
   */
  public ModuleInfo(String id2, String sn2, String type2, String ip2, String mac2, String version2,
      String name2,
      String descript2) {
    // this.id = id2;
    this.sn = sn2;
    this.mac = mac2;
    this.ip = ip2;
    this.productType = type2;
    this.productVersion = version2;
    this.name = name2;
    this.descript = descript2;

  }

  public ModuleInfo(byte[] cmd) {
    this.cmd = cmd;
    hexString = APPUtils.bytesToHexString(cmd, cmd.length);
  }

  public byte[] getCmd() {
    return cmd;
  }

  public String getSn() {
    if (hexString.length() > 0) {
      sn = hexString.substring(14 * 2, 30 * 2);
    }
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getProductType() {
    if (hexString.length() > 0) {
      productType = hexString.substring(30 * 2, 32 * 2);
      int pt = Integer.parseInt(productType, 16);

      String value = productType;
      value = APPUtils.hexToInt(value) + "";
      productType = value;
    }
    if (productType == null) {
      return "1";
    }
//		int pt = Integer.parseInt(productType);
//		if (pt < 10) {
//			return "000" + pt;
//		} else if (pt < 100) {
//			return "00" + pt;
//		} else if (pt < 1000) {
//			return "0" + pt;
//		}
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProductVersion() {
    return productVersion;
  }

  public void setProductVersion(String productVersion) {
    this.productVersion = productVersion;
  }

  public String getEncrypt() {
    return encrypt;
  }

  public void setEncrypt(String encrypt) {
    this.encrypt = encrypt;
  }

  public String getToken() {

    if (hexString.length() > 0) {
      token = hexString.substring(34 * 2, 50 * 2);
      ;
    }
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getDid() {
    return did;
  }

  public void setDid(String did) {
    this.did = did;
  }

  public String getIp() {
    if (hexString.length() > 0) {
      ip = Integer.toString(Integer.parseInt((hexString.substring(51 * 2, 52 * 2)), 16));
      ip = ip + "." + Integer.toString(Integer.parseInt((hexString.substring(52 * 2, 53 * 2)), 16));
      ip = ip + "." + Integer.toString(Integer.parseInt((hexString.substring(53 * 2, 54 * 2)), 16));
      ip = ip + "." + Integer.toString(Integer.parseInt((hexString.substring(54 * 2, 55 * 2)), 16));
    }
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * MAC先保存的16位 如果需要再转换
   */
  public String getMac() {
    if (hexString.length() > 0) {
      mac = hexString.substring(55 * 2, 61 * 2);
    }
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public ModuleInfo() {

  }

  public String getName() {
    if (name == null) {
      return "没有设备";
    }
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescript() {
    return descript;
  }

  public void setDescript(String descript) {
    this.descript = descript;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDevstatus() {
    return devstatus;
  }

  public void setDevstatus(String devstatus) {
    this.devstatus = devstatus;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getIsActivate() {
    return isActivate;
  }

  public void setIsActivate(String isActivate) {
    this.isActivate = isActivate;
  }

  public String getIsonline() {
    return isonline;
  }

  public void setIsonline(String isonline) {
    this.isonline = isonline;
  }

  public String getProduce_code() {
    if (produce_code == null) {
      return " ";
    }
    return produce_code;
  }

  public void setProduce_code(String produce_code) {
    this.produce_code = produce_code;
  }

  public String getDname() {
    return dname;
  }

  public void setDname(String dname) {
    this.dname = dname;
  }

  public String getMdcode() {
    if (mdcode == null) {
      return "1";
    }
    return mdcode;
  }

  public void setMdcode(String mdcode) {
    this.mdcode = mdcode;
  }

  public String getIsdeleted() {
    return isdeleted;
  }

  public void setIsdeleted(String isdeleted) {
    this.isdeleted = isdeleted;
  }

  public String getFromType() {
    return fromType;
  }

  public void setFromType(String fromType) {
    this.fromType = fromType;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  public String getMcode() {
    return mcode;
  }

  public void setMcode(String mcode) {
    this.mcode = mcode;
  }

  @Override
  public int hashCode() {
    return HashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ModuleInfo)) {
      return false;
    }
    //对传入对象上转型为ModuleInfo对象
    ModuleInfo mDev = (ModuleInfo) obj;
    //判断两个对象设备id一致认为是一个设备
    return this.did.equals(mDev.getDid());
  }

}
